package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.DailyQuestDto;
import org.bunrieu.sqlgamepixel.service.QuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quests")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestController {

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("/daily")
    public List<DailyQuestDto> getDailyQuests(
        @RequestParam(defaultValue = "player1") String username
    ) {
        return questService.getDailyQuests(username);
    }

    @PostMapping("/claim/{questId}")
    public Map<String, String> claimQuest(
        @RequestParam(defaultValue = "player1") String username,
        @PathVariable String questId
    ) {
        String result = questService.claimQuest(username, questId);
        return Map.of("message", result);
    }
}
