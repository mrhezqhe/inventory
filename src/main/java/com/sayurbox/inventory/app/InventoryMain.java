package com.sayurbox.inventory.app;

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

        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


}
