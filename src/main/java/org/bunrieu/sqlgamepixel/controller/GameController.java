package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.dto.GameStateResponse;
import org.bunrieu.sqlgamepixel.dto.SqlQueryRequest;
import org.bunrieu.sqlgamepixel.service.GameEngineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    private final GameEngineService gameEngine;
    public GameController(GameEngineService gameEngine) {
        this.gameEngine = gameEngine;
    }
    @PostMapping("/start")
    public String startLevel() {
        gameEngine.startLevel1();
        return "Lv1 ready";
    }
    @PostMapping("/execute")
    public GameStateResponse executeSql(@RequestBody SqlQueryRequest request) {
        return gameEngine.runCommand(request.getQuery());
    }
}