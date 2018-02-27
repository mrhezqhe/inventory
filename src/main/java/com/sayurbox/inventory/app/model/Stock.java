package com.sayurbox.inventory.app.model;

import java.util.List;

/**
 * @author mrhezqhez@gmail.com
 */
public class Stock {

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

}
