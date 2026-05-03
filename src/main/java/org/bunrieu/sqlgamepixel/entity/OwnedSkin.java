package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "owned_skins")
@IdClass(OwnedSkinId.class)
public class OwnedSkin {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "skin_id", length = 20)
    private String skinId;

    @Column(name = "bought_at")
    private LocalDateTime boughtAt = LocalDateTime.now();

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getSkinId() { return skinId; }
    public void setSkinId(String skinId) { this.skinId = skinId; }

    public LocalDateTime getBoughtAt() { return boughtAt; }
    public void setBoughtAt(LocalDateTime boughtAt) { this.boughtAt = boughtAt; }
}
