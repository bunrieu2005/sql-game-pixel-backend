package org.bunrieu.sqlgamepixel.entity;

import java.io.Serializable;

public class PlayerAchievementId implements Serializable {
    private Integer playerId;
    private String achievementId;

    public PlayerAchievementId() {}
    public PlayerAchievementId(Integer playerId, String achievementId) {
        this.playerId = playerId;
        this.achievementId = achievementId;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getAchievementId() { return achievementId; }
    public void setAchievementId(String achievementId) { this.achievementId = achievementId; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof PlayerAchievementId that)) return false;
        return playerId.equals(that.playerId) && achievementId.equals(that.achievementId);
    }
    @Override public int hashCode() {
        return 31 * playerId.hashCode() + achievementId.hashCode();
    }
}
