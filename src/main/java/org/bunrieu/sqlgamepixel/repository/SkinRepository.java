package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<Skin, String> {
    List<Skin> findByCharacterId(String characterId);
}
