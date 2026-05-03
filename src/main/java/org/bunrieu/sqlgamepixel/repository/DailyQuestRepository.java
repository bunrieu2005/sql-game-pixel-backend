package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.DailyQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DailyQuestRepository extends JpaRepository<DailyQuest, String> {
    List<DailyQuest> findAllByOrderBySortOrderAsc();
}
