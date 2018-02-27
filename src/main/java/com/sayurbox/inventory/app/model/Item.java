package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class Item  {
    private Fruit fruit;
    private String category;
    private int total;
    private boolean inStock = true;

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

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (inStock != item.inStock) return false;
        if (!fruit.equals(item.fruit)) return false;
        return category.equals(item.category);

    }

    @Override
    public int hashCode() {
        int result = fruit.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + (inStock ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "fruit=" + fruit +
                ", category='" + category + '\'' +
                ", total=" + total +
                ", inStock=" + inStock +
                '}';
    }
}
