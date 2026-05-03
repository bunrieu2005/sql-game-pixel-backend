package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.PlayerItem;
import org.bunrieu.sqlgamepixel.entity.PlayerItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerItemRepository extends JpaRepository<PlayerItem, PlayerItemId> {
    List<PlayerItem> findByPlayerId(Integer playerId);
    Optional<PlayerItem> findByPlayerIdAndItemId(Integer playerId, String itemId);
}
