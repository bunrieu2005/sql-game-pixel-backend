package org.bunrieu.sqlgamepixel.dto;

public class CharacterDto {
    private String id;
    private String name;
    private String element;
    private String elementLabel;
    private String weapon;
    private String description;
    private StatsDto stats;
    private String avatarColor;
    private String classLabel;

    public static class StatsDto {
        private int hp;
        private int mp;
        private int atk;
        private int def;
        private int speed;
        private int crit;

        public int getHp() { return hp; }
        public void setHp(int hp) { this.hp = hp; }

        public int getMp() { return mp; }
        public void setMp(int mp) { this.mp = mp; }

        public int getAtk() { return atk; }
        public void setAtk(int atk) { this.atk = atk; }

        public int getDef() { return def; }
        public void setDef(int def) { this.def = def; }

        public int getSpeed() { return speed; }
        public void setSpeed(int speed) { this.speed = speed; }

        public int getCrit() { return crit; }
        public void setCrit(int crit) { this.crit = crit; }
    }

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

    public StatsDto getStats() { return stats; }
    public void setStats(StatsDto stats) { this.stats = stats; }

    public String getAvatarColor() { return avatarColor; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }

    public String getClassLabel() { return classLabel; }
    public void setClassLabel(String classLabel) { this.classLabel = classLabel; }
}
