package org.bunrieu.sqlgamepixel.dto;

import java.util.List;
import java.util.Map;

public class GameStateResponse {
    private String message;
    private PlayerDto player;
    private List<Map<String, Object>> dataTable;
    private List<Map<String, Boolean>> levelProgress;

    public List<Map<String, Object>> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<Map<String, Object>> dataTable) {
        this.dataTable = dataTable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public List<Map<String, Boolean>> getLevelProgress() {
        return levelProgress;
    }

    public void setLevelProgress(List<Map<String, Boolean>> levelProgress) {
        this.levelProgress = levelProgress;
    }
}
