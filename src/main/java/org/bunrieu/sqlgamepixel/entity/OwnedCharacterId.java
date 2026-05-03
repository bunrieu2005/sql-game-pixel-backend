package org.bunrieu.sqlgamepixel.entity;

import java.io.Serializable;

public class OwnedCharacterId implements Serializable {
    private Integer playerId;
    private String characterId;

    public OwnedCharacterId() {}
    public OwnedCharacterId(Integer playerId, String characterId) {
        this.playerId = playerId;
        this.characterId = characterId;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getCharacterId() { return characterId; }
    public void setCharacterId(String characterId) { this.characterId = characterId; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof OwnedCharacterId that)) return false;
        return playerId.equals(that.playerId) && characterId.equals(that.characterId);
    }
    @Override public int hashCode() {
        return 31 * playerId.hashCode() + characterId.hashCode();
    }
}
