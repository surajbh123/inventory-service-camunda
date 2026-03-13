package com.example.loanapplication.postgres;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Transactional
    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("UPDATE Inventory i SET i.quantity = i.quantity - ?2 WHERE i.id = ?1 AND i.quantity >= ?2")
    int updateQuantity(Long id, int quantity);
}
