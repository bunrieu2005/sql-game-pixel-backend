package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, String> {

    @Query(value = "SELECT * FROM characters ORDER BY FIELD(id, 'warrior', 'assassin', 'mage', 'marksman')", nativeQuery = true)
    List<Character> findAllOrdered();
}
