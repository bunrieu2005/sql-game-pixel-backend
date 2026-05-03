package org.bunrieu.sqlgamepixel.dto;

public class LeaderboardEntryDto {
    private int rank;
    private String username;
    private int gold;
    private int levelsCompleted;
    private String selectedCharacterId;

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getLevelsCompleted() { return levelsCompleted; }
    public void setLevelsCompleted(int levelsCompleted) { this.levelsCompleted = levelsCompleted; }

    public String getSelectedCharacterId() { return selectedCharacterId; }
    public void setSelectedCharacterId(String selectedCharacterId) { this.selectedCharacterId = selectedCharacterId; }
}
