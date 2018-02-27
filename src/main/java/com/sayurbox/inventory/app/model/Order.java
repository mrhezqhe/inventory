package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class Order {

    private String name;
    private String category;
    private int total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

