package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findByUsername(String username);

    List<Player> findTopByOrderByTotalGoldEarnedDesc(Pageable pageable);
    List<Player> findTopByOrderByLevelsCompletedDesc(Pageable pageable);
}
