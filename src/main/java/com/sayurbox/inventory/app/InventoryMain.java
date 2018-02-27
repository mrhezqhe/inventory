package com.sayurbox.inventory.app;

import com.sayurbox.inventory.app.model.User;
import com.sayurbox.inventory.app.stock.CartManager;
import com.sayurbox.inventory.app.stock.InventoryManager;
import com.sayurbox.inventory.app.stock.OrderManager;
import com.sayurbox.inventory.app.util.Init;
import org.apache.log4j.Logger;

/**
 * @author mrhezqhez@gmail.com
 *
 */
public class InventoryMain {

    final static org.apache.log4j.Logger log = Logger.getLogger(InventoryMain.class);

    public static void main(String[] args) {
        try {
            log.debug("$ Inventory System $");

            InventoryManager manager = new InventoryManager(new Init());

            User manda = new User("manda");
            User susan = new User("susan");

            new CartManager(manager, manda).doSelect();
            new CartManager(manager, susan).doSelect();

            new OrderManager(manager, manda).doOrder();
            new OrderManager(manager, susan).doOrder();

        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


}
