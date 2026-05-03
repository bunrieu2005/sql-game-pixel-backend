package org.bunrieu.sqlgamepixel.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "player_daily_progress")
@IdClass(PlayerDailyProgressId.class)
public class PlayerDailyProgress {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "quest_id", length = 20)
    private String questId;

    @Column(nullable = false)
    private Integer progress = 0;

    @Column
    private Boolean claimed = false;

    @Column(name = "last_reset_date")
    private LocalDate lastResetDate;

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getQuestId() { return questId; }
    public void setQuestId(String questId) { this.questId = questId; }

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }

    public Boolean getClaimed() { return claimed; }
    public void setClaimed(Boolean claimed) { this.claimed = claimed; }

    public LocalDate getLastResetDate() { return lastResetDate; }
    public void setLastResetDate(LocalDate lastResetDate) { this.lastResetDate = lastResetDate; }
}
