package com.sayurbox.inventory.app.model;

import java.util.List;

/**
 * @author mrhezqhez@gmail.com
 */
public class Cart {

    private List<Order> cart;
    private User customerId;
    private String actionType;

    public List<Order> getCart() {
        return cart;
    }

    public void setCart(List<Order> cart) {
        this.cart = cart;
    }

    public User getCustomerId() {
        return customerId;
    }

    public void setCustomerId(User customerId) {
        this.customerId = customerId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

}
