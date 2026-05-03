package org.bunrieu.sqlgamepixel.dto;

public class SkinDto {
    private String id;
    private String characterId;
    private String name;
    private String color;
    private int price;
    private String description;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCharacterId() { return characterId; }
    public void setCharacterId(String characterId) { this.characterId = characterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
