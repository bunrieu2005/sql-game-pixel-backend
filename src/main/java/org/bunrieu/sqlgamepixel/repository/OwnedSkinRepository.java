package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.OwnedSkin;
import org.bunrieu.sqlgamepixel.entity.OwnedSkinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OwnedSkinRepository extends JpaRepository<OwnedSkin, OwnedSkinId> {
    List<OwnedSkin> findByPlayerId(Integer playerId);
    Optional<OwnedSkin> findByPlayerIdAndSkinId(Integer playerId, String skinId);
    long countByPlayerId(Integer playerId);
}
