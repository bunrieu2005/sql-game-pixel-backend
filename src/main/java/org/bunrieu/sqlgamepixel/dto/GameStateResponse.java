package org.bunrieu.sqlgamepixel.dto;

import java.util.List;
import java.util.Map;

// data returned to the player after SQL command
public class GameStateResponse {
    private String message;
    private PlayerDto player;// info & position
    private List<Map<String, Object>> dataTable;
    private Map<Integer, Boolean> levelProgress; // 16 level status

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

    public Map<Integer, Boolean> getLevelProgress() {
        return levelProgress;
    }

    public void setLevelProgress(Map<Integer, Boolean> levelProgress) {
        this.levelProgress = levelProgress;
    }
}
