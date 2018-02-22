package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class Mangga extends Fruit {

    public Mangga(String name, String farmLocation) {
        super(name, farmLocation);
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    private String flavour;
}
