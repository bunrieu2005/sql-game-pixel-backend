package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.PlayerAchievement;
import org.bunrieu.sqlgamepixel.entity.PlayerAchievementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerAchievementRepository extends JpaRepository<PlayerAchievement, PlayerAchievementId> {
    List<PlayerAchievement> findByPlayerId(Integer playerId);
    Optional<PlayerAchievement> findByPlayerIdAndAchievementId(Integer playerId, String achievementId);
    boolean existsByPlayerIdAndAchievementId(Integer playerId, String achievementId);
}
