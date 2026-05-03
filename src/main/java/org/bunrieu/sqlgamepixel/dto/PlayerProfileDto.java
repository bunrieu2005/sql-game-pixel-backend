package org.bunrieu.sqlgamepixel.dto;

public class PlayerProfileDto {
    private int id;
    private String username;
    private int gold;
    private int totalGoldEarned;
    private int levelsCompleted;
    private String selectedCharacterId;
    private CharacterDto selectedCharacter;
    private boolean characterSelected;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getTotalGoldEarned() { return totalGoldEarned; }
    public void setTotalGoldEarned(int totalGoldEarned) { this.totalGoldEarned = totalGoldEarned; }

    public int getLevelsCompleted() { return levelsCompleted; }
    public void setLevelsCompleted(int levelsCompleted) { this.levelsCompleted = levelsCompleted; }

    public String getSelectedCharacterId() { return selectedCharacterId; }
    public void setSelectedCharacterId(String selectedCharacterId) { this.selectedCharacterId = selectedCharacterId; }

    public CharacterDto getSelectedCharacter() { return selectedCharacter; }
    public void setSelectedCharacter(CharacterDto selectedCharacter) { this.selectedCharacter = selectedCharacter; }

    public boolean isCharacterSelected() { return characterSelected; }
    public void setCharacterSelected(boolean characterSelected) { this.characterSelected = characterSelected; }
}
