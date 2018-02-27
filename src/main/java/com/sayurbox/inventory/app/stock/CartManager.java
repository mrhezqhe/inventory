package com.sayurbox.inventory.app.stock;

import com.sayurbox.inventory.app.model.User;

/**
 * @author mrhezqhez@gmail.com
 */
public class CartManager implements Observer {

    private InventoryManager inventoryManager;
    private User user;

    public CartManager(InventoryManager inventoryManager, User user) {
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
    public void update() {

    }
}
