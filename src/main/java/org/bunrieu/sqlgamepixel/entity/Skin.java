package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skins")
public class Skin {

    @Id
    @Column(length = 20)
    private String id;

    @Column(name = "character_id", nullable = false, length = 20)
    private String characterId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    private Integer price = 0;

    @Column(length = 255)
    private String description;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCharacterId() { return characterId; }
    public void setCharacterId(String characterId) { this.characterId = characterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
