package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}
