package com.example.loanapplication.postgres;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryTransactionalService {

    private final InventoryJdbcRepository repository;

    public InventoryTransactionalService(InventoryJdbcRepository repository) {
        this.repository = repository;
    }

    /** Run withdrawal in a new transaction (independent commit/rollback) */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void withdrawInNewTx(Long id, int amount) {
        int updated = repository.updateQuantity(id, -amount);
        if (updated == 0) {
            throw new IllegalStateException("Item not found or insufficient quantity for id=" + id);
        }
    }

    /** Run deposit in a new transaction (independent commit/rollback) */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void depositInNewTx(Long id, int amount) {
        repository.updateQuantity(id, amount);
    }
}
