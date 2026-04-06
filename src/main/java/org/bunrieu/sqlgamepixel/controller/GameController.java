package org.bunrieu.sqlgamepixel.controller;


import org.bunrieu.sqlgamepixel.dto.SqlQueryRequest;
import org.bunrieu.sqlgamepixel.service.GameEngineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game") //base url
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
    public String executeSql(@RequestBody SqlQueryRequest request) {
        //json to object
                return gameEngine.executePlayerCommand(request.getQuery());
    }
}
