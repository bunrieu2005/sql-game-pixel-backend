package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String element;

    @Column(name = "element_label", nullable = false, length = 30)
    private String elementLabel;

    @Column(nullable = false, length = 30)
    private String weapon;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "class_label", length = 50)
    private String classLabel;

    @Column(name = "base_hp")
    private Integer baseHp = 100;

    @Column(name = "base_mp")
    private Integer baseMp = 50;

    @Column(name = "base_atk")
    private Integer baseAtk = 50;

    @Column(name = "base_def")
    private Integer baseDef = 50;

    @Column(name = "base_speed")
    private Integer baseSpeed = 50;

    @Column(name = "base_crit")
    private Integer baseCrit = 20;

    @Column(name = "avatar_color", length = 7)
    private String avatarColor = "#888888";

    private Integer price = 0;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getElement() { return element; }
    public void setElement(String element) { this.element = element; }

    public String getElementLabel() { return elementLabel; }
    public void setElementLabel(String elementLabel) { this.elementLabel = elementLabel; }

    public String getWeapon() { return weapon; }
    public void setWeapon(String weapon) { this.weapon = weapon; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getClassLabel() { return classLabel; }
    public void setClassLabel(String classLabel) { this.classLabel = classLabel; }

    public Integer getBaseHp() { return baseHp; }
    public void setBaseHp(Integer baseHp) { this.baseHp = baseHp; }

    public Integer getBaseMp() { return baseMp; }
    public void setBaseMp(Integer baseMp) { this.baseMp = baseMp; }

    public Integer getBaseAtk() { return baseAtk; }
    public void setBaseAtk(Integer baseAtk) { this.baseAtk = baseAtk; }

    public Integer getBaseDef() { return baseDef; }
    public void setBaseDef(Integer baseDef) { this.baseDef = baseDef; }

    public Integer getBaseSpeed() { return baseSpeed; }
    public void setBaseSpeed(Integer baseSpeed) { this.baseSpeed = baseSpeed; }

    public Integer getBaseCrit() { return baseCrit; }
    public void setBaseCrit(Integer baseCrit) { this.baseCrit = baseCrit; }

    public String getAvatarColor() { return avatarColor; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}
