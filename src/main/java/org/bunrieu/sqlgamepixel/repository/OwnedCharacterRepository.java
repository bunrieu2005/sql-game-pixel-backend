package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.OwnedCharacter;
import org.bunrieu.sqlgamepixel.entity.OwnedCharacterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OwnedCharacterRepository extends JpaRepository<OwnedCharacter, OwnedCharacterId> {
    List<OwnedCharacter> findByPlayerId(Integer playerId);
    Optional<OwnedCharacter> findByPlayerIdAndCharacterId(Integer playerId, String characterId);
    long countByPlayerId(Integer playerId);
}
