package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.AchievementDto;
import org.bunrieu.sqlgamepixel.entity.Achievement;
import org.bunrieu.sqlgamepixel.entity.GoldTransaction;
import org.bunrieu.sqlgamepixel.entity.OwnedCharacter;
import org.bunrieu.sqlgamepixel.entity.OwnedSkin;
import org.bunrieu.sqlgamepixel.entity.Player;
import org.bunrieu.sqlgamepixel.entity.PlayerAchievement;
import org.bunrieu.sqlgamepixel.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AchievementService {

    private final PlayerRepository playerRepository;
    private final AchievementRepository achievementRepository;
    private final PlayerAchievementRepository playerAchievementRepository;
    private final OwnedCharacterRepository ownedCharacterRepository;
    private final OwnedSkinRepository ownedSkinRepository;
    private final GoldTransactionRepository goldTransactionRepository;

    public AchievementService(
            PlayerRepository playerRepository,
            AchievementRepository achievementRepository,
            PlayerAchievementRepository playerAchievementRepository,
            OwnedCharacterRepository ownedCharacterRepository,
            OwnedSkinRepository ownedSkinRepository,
            GoldTransactionRepository goldTransactionRepository) {
        this.playerRepository = playerRepository;
        this.achievementRepository = achievementRepository;
        this.playerAchievementRepository = playerAchievementRepository;
        this.ownedCharacterRepository = ownedCharacterRepository;
        this.ownedSkinRepository = ownedSkinRepository;
        this.goldTransactionRepository = goldTransactionRepository;
    }

    public List<AchievementDto> getAchievements(String username) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);
        if (playerId < 0) return List.of();

        Set<String> unlocked = new HashSet<>();
        Map<String, String> unlockedAt = new HashMap<>();
        for (PlayerAchievement pa : playerAchievementRepository.findByPlayerId(playerId)) {
            unlocked.add(pa.getAchievementId());
            unlockedAt.put(pa.getAchievementId(),
                pa.getUnlockedAt() != null ? pa.getUnlockedAt().toString() : null);
        }

        return achievementRepository.findAllByOrderByGoldRewardDesc().stream()
            .map(a -> toDto(a, unlocked.contains(a.getId()), unlockedAt.get(a.getId())))
            .toList();
    }

    @Transactional
    public List<String> checkAndUnlockAchievements(String username) {
        int playerId = playerRepository.findByUsername(username)
            .map(Player::getId).orElse(-1);
        if (playerId < 0) return List.of();

        List<String> newlyUnlocked = new ArrayList<>();
        Map<String, Integer> stats = getPlayerStats(playerId);

        for (Achievement ach : achievementRepository.findAll()) {
            if (playerAchievementRepository.existsByPlayerIdAndAchievementId(playerId, ach.getId())) {
                continue;
            }

            boolean unlocked = switch (ach.getConditionType()) {
                case "LEVEL_COMPLETE"   -> stats.getOrDefault("levels_completed", 0) >= ach.getConditionValue();
                case "TOTAL_GOLD"       -> stats.getOrDefault("total_gold", 0) >= ach.getConditionValue();
                case "CHARACTERS_OWNED" -> stats.getOrDefault("characters_owned", 0) >= ach.getConditionValue();
                case "SKINS_OWNED"      -> stats.getOrDefault("skins_owned", 0) >= ach.getConditionValue();
                default -> false;
            };

            if (unlocked) {
                PlayerAchievement pa = new PlayerAchievement();
                pa.setPlayerId(playerId);
                pa.setAchievementId(ach.getId());
                playerAchievementRepository.save(pa);

                if (ach.getGoldReward() > 0) {
                    playerRepository.findById(playerId).ifPresent(p -> {
                        p.setGold(p.getGold() + ach.getGoldReward());
                        playerRepository.save(p);
                    });
                    GoldTransaction tx = new GoldTransaction();
                    tx.setPlayerId(playerId);
                    tx.setAmount(ach.getGoldReward());
                    tx.setReason("Achievement reward: " + ach.getName());
                    goldTransactionRepository.save(tx);
                }
                newlyUnlocked.add(ach.getName());
            }
        }
        return newlyUnlocked;
    }

    private Map<String, Integer> getPlayerStats(int playerId) {
        Map<String, Integer> stats = new HashMap<>();

        Player player = playerRepository.findById(playerId).orElse(null);
        if (player != null) {
            stats.put("total_gold", player.getTotalGoldEarned());
            stats.put("levels_completed", player.getLevelsCompleted());
        }

        stats.put("characters_owned", (int) ownedCharacterRepository.countByPlayerId(playerId));
        stats.put("skins_owned", (int) ownedSkinRepository.countByPlayerId(playerId));

        return stats;
    }

    private AchievementDto toDto(Achievement a, boolean unlocked, String unlockedAt) {
        AchievementDto dto = new AchievementDto();
        dto.setId(a.getId());
        dto.setName(a.getName());
        dto.setDescription(a.getDescription());
        dto.setIcon(a.getIcon());
        dto.setGoldReward(a.getGoldReward());
        dto.setUnlocked(unlocked);
        dto.setUnlockedAt(unlockedAt);
        return dto;
    }
}
