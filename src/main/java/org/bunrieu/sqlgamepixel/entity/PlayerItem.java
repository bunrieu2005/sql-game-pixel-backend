package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_items")
@IdClass(PlayerItemId.class)
public class PlayerItem {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "item_id", length = 20)
    private String itemId;

    @Column(nullable = false)
    private Integer quantity = 0;

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
