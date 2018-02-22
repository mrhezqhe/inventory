package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class Item {

    private Fruit fruit;
    private String category;
    private int total;

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
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
