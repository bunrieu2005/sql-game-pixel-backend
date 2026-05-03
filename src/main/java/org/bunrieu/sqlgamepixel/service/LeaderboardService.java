package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.LeaderboardEntryDto;
import org.bunrieu.sqlgamepixel.entity.Player;
import org.bunrieu.sqlgamepixel.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final PlayerRepository playerRepository;

    public LeaderboardService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<LeaderboardEntryDto> getTopPlayersByGold(int limit) {
        List<Player> players = playerRepository.findTopByOrderByTotalGoldEarnedDesc(PageRequest.of(0, limit));
        return players.stream().map(this::toEntry).toList();
    }

    public List<LeaderboardEntryDto> getTopPlayersByLevels(int limit) {
        List<Player> players = playerRepository.findTopByOrderByLevelsCompletedDesc(PageRequest.of(0, limit));
        return players.stream().map(this::toEntry).toList();
    }

    private LeaderboardEntryDto toEntry(Player player) {
        LeaderboardEntryDto dto = new LeaderboardEntryDto();
        dto.setRank(0);
        dto.setUsername(player.getUsername());
        dto.setGold(player.getGold());
        dto.setLevelsCompleted(player.getLevelsCompleted());
        dto.setSelectedCharacterId(player.getSelectedCharacter());
        return dto;
    }
}
