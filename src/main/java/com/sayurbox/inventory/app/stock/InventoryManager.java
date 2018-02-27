package com.sayurbox.inventory.app.stock;

import com.google.gson.Gson;
import com.sayurbox.inventory.app.common.XConstants;
import com.sayurbox.inventory.app.model.*;
import com.sayurbox.inventory.app.util.FunctionUtil;
import com.sayurbox.inventory.app.util.Init;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author mrhezqhez@gmail.com
 */
public class InventoryManager {

    final static org.apache.log4j.Logger log = Logger.getLogger(InventoryManager.class);

    private Gson gson = new Gson();
    private List<Observer> observers = new ArrayList<>();
    private Init init;
    private User user;

    public InventoryManager(Init init){
        this.init = init;
    }

    public void add(Observer o) {
        observers.add(o);
    }

    public synchronized void selecting() {
        executeCart();
        printingCart();
    }

    public synchronized void ordering() {
        executeOrder();
        printingOrder();
    }

    private void executeCart() {
        try {
            log.info("executeCart");

            Cart mandaSelectedCart = gson.fromJson(init.getMandaCart(), Cart.class);
            Cart susanSelectedCart = gson.fromJson(init.getSusanCart(), Cart.class);

            if(user.getName().equalsIgnoreCase(mandaSelectedCart.getCustomerId().getName())){

                if(mandaSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_CART)){
                    Stock progressStock = new Stock();
                    List<Item> progressItems = new ArrayList<>();
                    List<Item> progressIdleItems = new ArrayList<>();
                    List<Item> selectedItems = new ArrayList<>();
                    Iterator<Stock> iterStock = null;
                    for(Order c: mandaSelectedCart.getCart()){
                        //iterate over stock here
                        iterStock = FunctionUtil.checkStock(init.getAvailableStocks(), progressItems, selectedItems, progressIdleItems, c);
                    }
                    if(progressIdleItems.size() > 0){
                        for(Item idleItem : progressIdleItems){
                            progressItems.add(idleItem);
                        }
                    }
                    init.getSelectedMandaCart().add(selectedItems);
                    progressStock.setItems(progressItems);
                    progressStock.setWarehouse(init.getInitalStock().getWarehouse());
                    iterStock.remove();
                    init.getAvailableStocks().add(progressStock); //update last stock
                }
            } else if(user.getName().equalsIgnoreCase(susanSelectedCart.getCustomerId().getName())){

                if(susanSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_CART)){
                    Stock progressStock = new Stock();
                    List<Item> progressItems = new ArrayList<>();
                    List<Item> progressIdleItems = new ArrayList<>();
                    List<Item> selectedItems = new ArrayList<>();
                    Iterator<Stock> iterStock = null;
                    for(Order c: susanSelectedCart.getCart()){
                        //iterate over stock here
                        iterStock = FunctionUtil.checkStock(init.getAvailableStocks(), progressItems, selectedItems, progressIdleItems, c);
                    }
                    if(progressIdleItems.size() > 0){
                        for(Item idleItem : progressIdleItems){
                            progressItems.add(idleItem);
                        }
                    }
                    init.getSelectedSusanCart().add(selectedItems);
                    progressStock.setItems(progressItems);
                    progressStock.setWarehouse(init.getInitalStock().getWarehouse());

                    iterStock.remove();
                    init.getAvailableStocks().add(progressStock); //update last stock
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void executeOrder() {
        try {
            log.info("executeOrder");

            Cart mandaSelectedCart = gson.fromJson(init.getMandaOrder(), Cart.class);
            Cart susanSelectedCart = gson.fromJson(init.getSusanOrder(), Cart.class);

            if(user.getName().equalsIgnoreCase(mandaSelectedCart.getCustomerId().getName())){
                if(mandaSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_ORDER)){
                    Stock progressStock = new Stock();
                    List<Item> selectedOrderItems = new ArrayList<>();
                    for(Order c: mandaSelectedCart.getCart()){ //order data
                        //iterate over selected cart here
                        Iterator<List<Item>> it = init.getSelectedMandaCart().iterator();
                        while (it.hasNext()) {
                            List<Item> listItem = (List<Item>) it.next();
                            Iterator<Item> itItem = listItem.iterator();
                            while (itItem.hasNext()) {
                                Item theItem = (Item) itItem.next();
                                String category = theItem.getCategory();
                                String name = theItem.getFruit().getName();
                                int total = theItem.getTotal();
                                if(category.equalsIgnoreCase(c.getCategory()) && name.equalsIgnoreCase(c.getName())){
                                    //add to order table
                                    int currentOrderTotal = 0;
                                    if(total > c.getTotal()){
                                        //customer ordering less than their available cart, insert back to stock
                                        currentOrderTotal = total - c.getTotal();

                                        //add into stock
                                        Item refundItem = new Item();
                                        refundItem.setCategory(category);
                                        refundItem.setFruit(theItem.getFruit());
                                        refundItem.setTotal(currentOrderTotal);
                                        //add to stock
                                        init.getBackToStockItems().add(refundItem);

                                        //add to order, ready to make purchase
                                        Item selectedItem = new Item();
                                        selectedItem.setCategory(category);
                                        selectedItem.setFruit(theItem.getFruit());
                                        selectedItem.setTotal(c.getTotal());
                                        selectedOrderItems.add(selectedItem);
                                    } else {
                                        selectedOrderItems.add(theItem);
                                    }

                                    //always remove from cart
                                    itItem.remove();
                                    break;
                                } else {
                                    //check if previous item already there
                                    Iterator<Item> iter = selectedOrderItems.iterator();
                                    while (iter.hasNext()) {
                                        Item itemSelected = (Item) iter.next();
                                        if(!name.equalsIgnoreCase(itemSelected.getFruit().getName())){
                                            //insert back to stock
                                            init.getBackToStockItems().add(theItem);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    init.getSelectedMandaOrder().add(selectedOrderItems);
                }
            } else if(user.getName().equalsIgnoreCase(susanSelectedCart.getCustomerId().getName())){
                if(susanSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_ORDER)){
                    Stock progressStock = new Stock();
                    List<Item> selectedOrderItems = new ArrayList<>();
                    for(Order c: susanSelectedCart.getCart()){ //order data
                        //iterate over selected cart here
                        Iterator<List<Item>> it = init.getSelectedSusanCart().iterator();
                        while (it.hasNext()) {
                            List<Item> listItem = (List<Item>) it.next();
                            Iterator<Item> itItem = listItem.iterator();
                            while (itItem.hasNext()) {
                                Item theItem = (Item) itItem.next();
                                String category = theItem.getCategory();
                                String name = theItem.getFruit().getName();
                                int total = theItem.getTotal();
                                if(category.equalsIgnoreCase(c.getCategory()) && name.equalsIgnoreCase(c.getName())){
                                    //add to order table
                                    int currentOrderTotal = 0;
                                    if(total > c.getTotal()){
                                        //customer ordering less than their available cart, insert back to stock
                                        currentOrderTotal = total - c.getTotal();

                                        //add into stock
                                        Item refundItem = new Item();
                                        refundItem.setCategory(category);
                                        refundItem.setFruit(theItem.getFruit());
                                        refundItem.setTotal(currentOrderTotal);
                                        //add to stock
                                        init.getBackToStockItems().add(refundItem);

                                        //add to order, ready to make purchase
                                        Item selectedItem = new Item();
                                        selectedItem.setCategory(category);
                                        selectedItem.setFruit(theItem.getFruit());
                                        selectedItem.setTotal(c.getTotal());
                                        selectedOrderItems.add(selectedItem);
                                    } else {
                                        selectedOrderItems.add(theItem);
                                    }

                                    //always remove from cart
                                    itItem.remove();
                                    break;
                                } else {
                                    //check if previous item already there
                                    Iterator<Item> iter = selectedOrderItems.iterator();
                                    while (iter.hasNext()) {
                                        Item itemSelected = (Item) iter.next();
                                        if(!name.equalsIgnoreCase(itemSelected.getFruit().getName())){
                                            //insert back to stock
                                            init.getBackToStockItems().add(theItem);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    init.getSelectedSusanOrder().add(selectedOrderItems);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printingCart() {
        FunctionUtil.printCartStatus(init.getSelectedMandaCart(), init.getSelectedSusanCart());
        FunctionUtil.printStockStatus(init.getAvailableStocks());
    }

    public void printingOrder() {
        FunctionUtil.printOrderStatus(init.getSelectedMandaOrder(), init.getSelectedSusanOrder());
        FunctionUtil.printStockStatus(init.getAvailableStocks());
    }

    public Init getInit() {
        return init;
    }

    public void setInit(Init init) {
        this.init = init;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
