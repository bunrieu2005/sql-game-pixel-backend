package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.LeaderboardEntryDto;
import org.bunrieu.sqlgamepixel.service.LeaderboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public List<LeaderboardEntryDto> getLeaderboard(
        @RequestParam(defaultValue = "gold") String sortBy,
        @RequestParam(defaultValue = "10") int limit
    ) {
        if ("levels".equalsIgnoreCase(sortBy)) {
            return leaderboardService.getTopPlayersByLevels(limit);
        }
        return leaderboardService.getTopPlayersByGold(limit);
    }
}
