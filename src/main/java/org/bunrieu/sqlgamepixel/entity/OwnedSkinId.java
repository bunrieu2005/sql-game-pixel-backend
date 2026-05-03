package org.bunrieu.sqlgamepixel.entity;

import java.io.Serializable;

public class OwnedSkinId implements Serializable {
    private Integer playerId;
    private String skinId;

    public OwnedSkinId() {}
    public OwnedSkinId(Integer playerId, String skinId) {
        this.playerId = playerId;
        this.skinId = skinId;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getSkinId() { return skinId; }
    public void setSkinId(String skinId) { this.skinId = skinId; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof OwnedSkinId that)) return false;
        return playerId.equals(that.playerId) && skinId.equals(that.skinId);
    }
    @Override public int hashCode() {
        return 31 * playerId.hashCode() + skinId.hashCode();
    }
}
