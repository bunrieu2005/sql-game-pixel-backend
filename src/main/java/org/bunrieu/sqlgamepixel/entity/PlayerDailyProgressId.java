package org.bunrieu.sqlgamepixel.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class PlayerDailyProgressId implements Serializable {
    private Integer playerId;
    private String questId;

    public PlayerDailyProgressId() {}
    public PlayerDailyProgressId(Integer playerId, String questId) {
        this.playerId = playerId;
        this.questId = questId;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getQuestId() { return questId; }
    public void setQuestId(String questId) { this.questId = questId; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof PlayerDailyProgressId that)) return false;
        return playerId.equals(that.playerId) && questId.equals(that.questId);
    }
    @Override public int hashCode() {
        return 31 * playerId.hashCode() + questId.hashCode();
    }
}
