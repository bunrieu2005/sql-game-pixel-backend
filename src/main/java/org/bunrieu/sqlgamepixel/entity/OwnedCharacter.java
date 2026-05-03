package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "owned_characters")
@IdClass(OwnedCharacterId.class)
public class OwnedCharacter {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "character_id", length = 20)
    private String characterId;

    @Column(name = "equipped_skin_id", length = 20)
    private String equippedSkinId;

    @Column(name = "bought_at")
    private LocalDateTime boughtAt = LocalDateTime.now();

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getCharacterId() { return characterId; }
    public void setCharacterId(String characterId) { this.characterId = characterId; }

    public String getEquippedSkinId() { return equippedSkinId; }
    public void setEquippedSkinId(String equippedSkinId) { this.equippedSkinId = equippedSkinId; }

    public LocalDateTime getBoughtAt() { return boughtAt; }
    public void setBoughtAt(LocalDateTime boughtAt) { this.boughtAt = boughtAt; }
}
