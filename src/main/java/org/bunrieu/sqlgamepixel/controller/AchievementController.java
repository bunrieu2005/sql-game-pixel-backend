package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.AchievementDto;
import org.bunrieu.sqlgamepixel.service.AchievementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "http://localhost:4200")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<AchievementDto> getAchievements(
        @RequestParam(defaultValue = "player1") String username
    ) {
        return achievementService.getAchievements(username);
    }

    @PostMapping("/check")
    public Map<String, Object> checkAchievements(
        @RequestParam(defaultValue = "player1") String username
    ) {
        List<String> unlocked = achievementService.checkAndUnlockAchievements(username);
        if (unlocked.isEmpty()) {
            return Map.of("message", "Không có thành tựu mới.");
        }
        return Map.of(
            "message", "Đã mở khóa: " + String.join(", ", unlocked),
            "unlocked", unlocked
        );
    }
}
