package com.sayurbox.inventory.app.stock;

import com.sayurbox.inventory.app.model.Item;

/**
 * @author mrhezqhez@gmail.com
 *
 */
public interface Observable {
    public void addObserver(Item item);
    public void removeObserver(Item item);
    public void notifyObserver();
}
