package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, String> {
    List<Achievement> findAllByOrderByGoldRewardDesc();
}
