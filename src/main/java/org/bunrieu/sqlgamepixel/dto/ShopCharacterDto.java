package org.bunrieu.sqlgamepixel.dto;

public class ShopCharacterDto extends CharacterDto {
    private int price;
    private boolean owned;
    private String equippedSkinId;

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public boolean isOwned() { return owned; }
    public void setOwned(boolean owned) { this.owned = owned; }

    public String getEquippedSkinId() { return equippedSkinId; }
    public void setEquippedSkinId(String equippedSkinId) { this.equippedSkinId = equippedSkinId; }
}
