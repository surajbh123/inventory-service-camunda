package com.example.loanapplication.postgres;

import com.example.loanapplication.inventory.Inventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryJdbcRepository repository;
    private final InventoryTransactionalService txService;

    public InventoryService(InventoryJdbcRepository repository, InventoryTransactionalService txService) {
        this.repository = repository;
        this.txService = txService;
    }

    /** Orchestrates a stock transfer. This method runs in a REQUIRED transaction; inner operations use REQUIRES_NEW. */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void transferStock(Long fromId, Long toId, int amount) {
        Inventory from = repository.findById(fromId).orElseThrow(() -> new IllegalStateException("Source not found"));
        Inventory to = repository.findById(toId).orElseThrow(() -> new IllegalStateException("Destination not found"));

        if (from.getQuantity() == null || from.getQuantity() < amount) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        // Each of these runs in its own transaction (REQUIRES_NEW). This demonstrates propagation semantics.
        txService.withdrawInNewTx(fromId, amount);
        txService.depositInNewTx(toId, amount);

        // Note: because inner methods are REQUIRES_NEW, they commit immediately and are independent of this outer transaction.
    }

    /** Example method showing higher isolation level (repeatable read) to work with MVCC snapshots. */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void adjustAndRead(Long id, int delta) {
        repository.updateQuantity(id, delta);
        Inventory i = repository.findById(id).orElseThrow(() -> new IllegalStateException("Inventory missing" + id));
        // work with i...
    }
}
