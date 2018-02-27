package com.sayurbox.inventory.app.model;

import com.sayurbox.inventory.app.stock.Observer;

/**
 * @author mrhezqhez@gmail.com
 */
public class User implements Observer {

    public User(String name){
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        
    }
}
