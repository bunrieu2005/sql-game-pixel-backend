package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.GoldRewardDto;
import org.bunrieu.sqlgamepixel.dto.PlayerProfileDto;
import org.bunrieu.sqlgamepixel.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public PlayerProfileDto getProfile(
        @RequestParam(defaultValue = "player1") String username
    ) {
        return playerService.getProfile(username);
    }

    @PostMapping("/select-character")
    public PlayerProfileDto selectCharacter(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody SelectCharacterRequest request
    ) {
        return playerService.selectCharacter(username, request.getCharacterId());
    }

    @PostMapping("/add-gold")
    public GoldRewardDto addGold(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody AddGoldRequest request
    ) {
        return playerService.addGold(username, request.getAmount(), request.getReason());
    }

    @PostMapping("/init-character")
    public PlayerProfileDto initCharacter(
        @RequestParam(defaultValue = "player1") String username,
        @RequestBody InitCharacterRequest request
    ) {
        return playerService.initCharacter(username, request.getCharacterId(), request.getNickname());
    }

    public static class SelectCharacterRequest {
        private String characterId;
        public String getCharacterId() { return characterId; }
        public void setCharacterId(String id) { this.characterId = id; }
    }

    public static class AddGoldRequest {
        private int amount;
        private String reason;
        public int getAmount() { return amount; }
        public void setAmount(int amount) { this.amount = amount; }
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }

    public static class InitCharacterRequest {
        private String characterId;
        private String nickname;
        public String getCharacterId() { return characterId; }
        public void setCharacterId(String id) { this.characterId = id; }
        public String getNickname() { return nickname; }
        public void setNickname(String n) { this.nickname = n; }
    }
}
