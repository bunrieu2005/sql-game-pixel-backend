package org.bunrieu.sqlgamepixel.entity;

import java.io.Serializable;

public class PlayerItemId implements Serializable {
    private Integer playerId;
    private String itemId;

    public PlayerItemId() {}
    public PlayerItemId(Integer playerId, String itemId) {
        this.playerId = playerId;
        this.itemId = itemId;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof PlayerItemId that)) return false;
        return playerId.equals(that.playerId) && itemId.equals(that.itemId);
    }
    @Override public int hashCode() {
        return 31 * playerId.hashCode() + itemId.hashCode();
    }
}
