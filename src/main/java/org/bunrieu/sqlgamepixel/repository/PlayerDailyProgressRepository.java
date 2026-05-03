package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.PlayerDailyProgress;
import org.bunrieu.sqlgamepixel.entity.PlayerDailyProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerDailyProgressRepository extends JpaRepository<PlayerDailyProgress, PlayerDailyProgressId> {
    List<PlayerDailyProgress> findByPlayerIdAndLastResetDate(Integer playerId, LocalDate date);

    @Query("SELECT p FROM PlayerDailyProgress p WHERE p.playerId = :playerId AND p.lastResetDate < :date")
    List<PlayerDailyProgress> findStaleProgress(Integer playerId, LocalDate date);
}
