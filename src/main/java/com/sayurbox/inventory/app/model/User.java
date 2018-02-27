package com.sayurbox.inventory.app.model;

/**
 * @author mrhezqhez@gmail.com
 */
public class User {

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

}
