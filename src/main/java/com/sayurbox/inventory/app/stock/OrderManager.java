package com.sayurbox.inventory.app.stock;

import com.sayurbox.inventory.app.model.User;

/**
 * @author mrhezqhez@gmail.com
 */
public class OrderManager implements Observer {

    private InventoryManager inventoryManager;
    private User user;

    public OrderManager(InventoryManager inventoryManager, User user) {
        this.inventoryManager = inventoryManager;
        this.inventoryManager.add(this);
        this.inventoryManager.setUser(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doSelect() {
        inventoryManager.selecting();
    }

    @Override
    public void doOrder() {
        inventoryManager.ordering();
    }

}
