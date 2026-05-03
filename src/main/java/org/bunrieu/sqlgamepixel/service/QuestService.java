package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.DailyQuestDto;
import org.bunrieu.sqlgamepixel.entity.DailyQuest;
import org.bunrieu.sqlgamepixel.entity.GoldTransaction;
import org.bunrieu.sqlgamepixel.entity.Player;
import org.bunrieu.sqlgamepixel.entity.PlayerDailyProgress;
import org.bunrieu.sqlgamepixel.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class QuestService {

    private final PlayerRepository playerRepository;
    private final DailyQuestRepository dailyQuestRepository;
    private final PlayerDailyProgressRepository progressRepository;
    private final GoldTransactionRepository goldTransactionRepository;

    public QuestService(
            PlayerRepository playerRepository,
            DailyQuestRepository dailyQuestRepository,
            PlayerDailyProgressRepository progressRepository,
            GoldTransactionRepository goldTransactionRepository) {
        this.playerRepository = playerRepository;
        this.dailyQuestRepository = dailyQuestRepository;
        this.progressRepository = progressRepository;
        this.goldTransactionRepository = goldTransactionRepository;
    }

    public List<DailyQuestDto> getDailyQuests(String username) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found"));
        LocalDate today = LocalDate.now();
        ensureDailyProgress(player.getId(), today);

        List<DailyQuest> quests = dailyQuestRepository.findAllByOrderBySortOrderAsc();
        List<PlayerDailyProgress> progressList = progressRepository.findByPlayerIdAndLastResetDate(player.getId(), today);

        Map<String, PlayerDailyProgress> progressMap = new HashMap<>();
        for (PlayerDailyProgress p : progressList) {
            progressMap.put(p.getQuestId(), p);
        }

        return quests.stream().map(q -> toDto(q, progressMap.get(q.getId()))).toList();
    }

    @Transactional
    public String claimQuest(String username, String questId) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found"));
        LocalDate today = LocalDate.now();

        DailyQuest quest = dailyQuestRepository.findById(questId)
            .orElseThrow(() -> new RuntimeException("Quest not found"));

        PlayerDailyProgress progress = progressRepository.findByPlayerIdAndLastResetDate(player.getId(), today)
            .stream().filter(p -> p.getQuestId().equals(questId)).findFirst()
            .orElse(null);

        if (progress != null && Boolean.TRUE.equals(progress.getClaimed())) {
            return "Bạn đã nhận thưởng quest này rồi!";
        }

        int currentProgress = progress != null ? progress.getProgress() : 0;
        if (currentProgress < quest.getTarget()) {
            return "Chưa hoàn thành quest! Tiến độ: " + currentProgress + "/" + quest.getTarget();
        }

        if (progress != null) {
            progress.setClaimed(true);
            progressRepository.save(progress);
        }

        int reward = quest.getGoldReward();
        player.setGold(player.getGold() + reward);
        playerRepository.save(player);

        GoldTransaction tx = new GoldTransaction();
        tx.setPlayerId(player.getId());
        tx.setAmount(reward);
        tx.setReason("Daily quest reward: " + questId);
        goldTransactionRepository.save(tx);

        return "Nhận thưởng thành công! +" + reward + " Gold!";
    }

    @Transactional
    public void incrementDailyProgress(String username, int levelsCompleted) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);
        if (playerId < 0) return;

        LocalDate today = LocalDate.now();
        List<PlayerDailyProgress> active = progressRepository.findByPlayerIdAndLastResetDate(playerId, today);

        for (PlayerDailyProgress p : active) {
            if (p.getProgress() < getQuestTarget(p.getQuestId())) {
                p.setProgress(p.getProgress() + levelsCompleted);
                progressRepository.save(p);
            }
        }
    }

    private void ensureDailyProgress(int playerId, LocalDate today) {
        // Reset stale progress
        List<PlayerDailyProgress> stale = progressRepository.findStaleProgress(playerId, today);
        for (PlayerDailyProgress p : stale) {
            p.setProgress(0);
            p.setClaimed(false);
            p.setLastResetDate(today);
            progressRepository.save(p);
        }

        // Ensure rows exist for all quests
        List<DailyQuest> quests = dailyQuestRepository.findAll();
        List<String> existingQuestIds = progressRepository.findByPlayerIdAndLastResetDate(playerId, today)
            .stream().map(PlayerDailyProgress::getQuestId).toList();

        for (DailyQuest quest : quests) {
            if (!existingQuestIds.contains(quest.getId())) {
                PlayerDailyProgress p = new PlayerDailyProgress();
                p.setPlayerId(playerId);
                p.setQuestId(quest.getId());
                p.setProgress(0);
                p.setClaimed(false);
                p.setLastResetDate(today);
                progressRepository.save(p);
            }
        }
    }

    private int getQuestTarget(String questId) {
        return dailyQuestRepository.findById(questId)
            .map(DailyQuest::getTarget).orElse(0);
    }

    private DailyQuestDto toDto(DailyQuest quest, PlayerDailyProgress progress) {
        DailyQuestDto dto = new DailyQuestDto();
        dto.setId(quest.getId());
        dto.setTitle(quest.getTitle());
        dto.setDescription(quest.getDescription());
        dto.setTarget(quest.getTarget());
        dto.setGoldReward(quest.getGoldReward());

        int prog = progress != null ? progress.getProgress() : 0;
        boolean claimed = progress != null && Boolean.TRUE.equals(progress.getClaimed());
        dto.setProgress(prog);
        dto.setClaimed(claimed);
        dto.setCompleted(prog >= quest.getTarget());
        return dto;
    }
}
