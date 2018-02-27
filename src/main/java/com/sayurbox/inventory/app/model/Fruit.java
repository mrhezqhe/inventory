package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class Fruit {

    private String name;
    private String color;
    private String farmLocation;

    public Fruit(String name, String farmLocation){
        this.name = name;
        this.farmLocation = farmLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        this.farmLocation = farmLocation;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", farmLocation='" + farmLocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fruit fruit = (Fruit) o;

        return name.equals(fruit.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
