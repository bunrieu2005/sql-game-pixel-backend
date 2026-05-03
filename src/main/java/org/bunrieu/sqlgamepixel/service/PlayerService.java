package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.dto.GoldRewardDto;
import org.bunrieu.sqlgamepixel.dto.PlayerProfileDto;
import org.bunrieu.sqlgamepixel.entity.GoldTransaction;
import org.bunrieu.sqlgamepixel.entity.Player;
import org.bunrieu.sqlgamepixel.repository.GoldTransactionRepository;
import org.bunrieu.sqlgamepixel.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GoldTransactionRepository goldTransactionRepository;
    private final CharacterService characterService;

    public PlayerService(
            PlayerRepository playerRepository,
            GoldTransactionRepository goldTransactionRepository,
            CharacterService characterService) {
        this.playerRepository = playerRepository;
        this.goldTransactionRepository = goldTransactionRepository;
        this.characterService = characterService;
    }

    public PlayerProfileDto getProfile(String username) {
        Player player = playerRepository.findByUsername(username)
            .orElseGet(() -> {
                Player newPlayer = new Player();
                newPlayer.setUsername(username);
                newPlayer.setGold(0);
                newPlayer.setTotalGoldEarned(0);
                newPlayer.setLevelsCompleted(0);
                return playerRepository.save(newPlayer);
            });

        return toProfileDto(player);
    }

    @Transactional
    public PlayerProfileDto selectCharacter(String username, String characterId) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found: " + username));

        player.setSelectedCharacter(characterId);
        playerRepository.save(player);
        return toProfileDto(player);
    }

    @Transactional
    public GoldRewardDto addGold(String username, int amount, String reason) {
        Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Player not found: " + username));

        player.setGold(player.getGold() + amount);
        player.setTotalGoldEarned(player.getTotalGoldEarned() + amount);
        playerRepository.save(player);

        GoldTransaction tx = new GoldTransaction();
        tx.setPlayerId(player.getId());
        tx.setAmount(amount);
        tx.setReason(reason);
        goldTransactionRepository.save(tx);

        GoldRewardDto reward = new GoldRewardDto();
        reward.setGoldEarned(amount);
        reward.setTotalGold(player.getGold());
        reward.setReason(reason);
        reward.setMessage("+" + amount + " Gold! Reason: " + reason);
        return reward;
    }

    private PlayerProfileDto toProfileDto(Player player) {
        PlayerProfileDto dto = new PlayerProfileDto();
        dto.setId(player.getId());
        dto.setUsername(player.getUsername());
        dto.setGold(player.getGold());
        dto.setTotalGoldEarned(player.getTotalGoldEarned());
        dto.setLevelsCompleted(player.getLevelsCompleted());

        String charId = player.getSelectedCharacter();
        dto.setSelectedCharacterId(charId);
        dto.setCharacterSelected(charId != null && !charId.isEmpty());

        if (charId != null && !charId.isEmpty()) {
            try {
                dto.setSelectedCharacter(characterService.getCharacterById(charId));
            } catch (Exception ignored) {}
        }
        return dto;
    }
}
