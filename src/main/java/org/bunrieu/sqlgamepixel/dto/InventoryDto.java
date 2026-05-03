package org.bunrieu.sqlgamepixel.dto;

import java.util.List;

public class InventoryDto {
    private List<String> ownedCharacterIds;
    private List<String> ownedSkinIds;
    private java.util.Map<String, Integer> items; // itemId -> quantity

    public List<String> getOwnedCharacterIds() { return ownedCharacterIds; }
    public void setOwnedCharacterIds(List<String> ownedCharacterIds) { this.ownedCharacterIds = ownedCharacterIds; }

    public List<String> getOwnedSkinIds() { return ownedSkinIds; }
    public void setOwnedSkinIds(List<String> ownedSkinIds) { this.ownedSkinIds = ownedSkinIds; }

    public java.util.Map<String, Integer> getItems() { return items; }
    public void setItems(java.util.Map<String, Integer> items) { this.items = items; }
}
