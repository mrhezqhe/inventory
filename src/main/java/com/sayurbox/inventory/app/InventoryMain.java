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

            new CartManager(manager, manda);
            manager.selecting();

            new CartManager(manager, susan);
            manager.selecting();

            new OrderManager(manager, manda);
            manager.ordering();

            new OrderManager(manager, susan);
            manager.ordering();

        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


}
