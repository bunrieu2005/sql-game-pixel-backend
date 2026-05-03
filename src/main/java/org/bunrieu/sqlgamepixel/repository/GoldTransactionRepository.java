package org.bunrieu.sqlgamepixel.repository;

import org.bunrieu.sqlgamepixel.entity.GoldTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldTransactionRepository extends JpaRepository<GoldTransaction, Integer> {
}
