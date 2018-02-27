package com.sayurbox.inventory.app.model;

import com.sayurbox.inventory.app.stock.Observable;

import java.util.List;

/**
 * @author mrhezqhez@gmail.com
 */
public class Stock implements Observable {

    @Override
    public String toString() {
        return "Stock{" +
                "items=" + items +
                ", warehouse='" + warehouse + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;

        Stock stock = (Stock) o;

        return warehouse.equals(stock.warehouse);

    }

    @Override
    public int hashCode() {
        return warehouse.hashCode();
    }

    private List<Item> items;
    private String warehouse;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void addObserver(Item item) {
        items.add(item);
    }

    @Override
    public void removeObserver(Item item) {
        items.remove(item);
    }

    @Override
    public void notifyObserver() {

    }

}
