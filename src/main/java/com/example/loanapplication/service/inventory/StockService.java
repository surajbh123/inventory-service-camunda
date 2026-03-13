package com.example.loanapplication.service.inventory;

import com.example.loanapplication.postgres.Datasource;
import com.example.loanapplication.inventory.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class StockService {

    private final EntityManager inventoryEntityManager;

    public StockService(@Qualifier(Datasource.POSTGRES_ENTITY_MANAGER) EntityManager inventoryEntityManager) {
        this.inventoryEntityManager = inventoryEntityManager;
    }


    public void addQuantityToInventory(int quantity, int productId) {
        EntityTransaction transaction = inventoryEntityManager.getTransaction();
        try {
            transaction.begin();
            // get product from inventory and lock it for update
            Inventory inventory = inventoryEntityManager.find(Inventory.class, productId, LockModeType.PESSIMISTIC_WRITE);
            if (inventory == null) {
                throw new IllegalArgumentException("Inventory product not found: " + productId);
            }
            inventory.setQuantity(inventory.getQuantity() + quantity);
            // entity is managed; no explicit persist required
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }
}
