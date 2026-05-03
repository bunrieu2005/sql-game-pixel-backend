package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private Integer gold = 0;

    @Column(name = "total_gold_earned", nullable = false)
    private Integer totalGoldEarned = 0;

    @Column(name = "levels_completed", nullable = false)
    private Integer levelsCompleted = 0;

    @Column(name = "selected_character", length = 20)
    private String selectedCharacter;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getGold() { return gold; }
    public void setGold(Integer gold) { this.gold = gold; }

    public Integer getTotalGoldEarned() { return totalGoldEarned; }
    public void setTotalGoldEarned(Integer totalGoldEarned) { this.totalGoldEarned = totalGoldEarned; }

    public Integer getLevelsCompleted() { return levelsCompleted; }
    public void setLevelsCompleted(Integer levelsCompleted) { this.levelsCompleted = levelsCompleted; }

    public String getSelectedCharacter() { return selectedCharacter; }
    public void setSelectedCharacter(String selectedCharacter) { this.selectedCharacter = selectedCharacter; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
