package com.sayurbox.inventory.app.stock;

/**
 * @author mrhezqhez@gmail.com
 *
 */
public interface Inventory {
    void initialize();
    void removeStock(String param);
    void addStock(String param);
    void checkStatus(String param);
    void getStock(String param);
}
