package com.sayurbox.inventory.app.util;

import com.sayurbox.inventory.app.model.Item;
import com.sayurbox.inventory.app.model.Order;
import com.sayurbox.inventory.app.model.Stock;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author mrhezqhez@gmail.com
 */
public class FunctionUtil {

    public static File getCwd() {
        return new File("").getAbsoluteFile();
    }

    public static Iterator<Stock> checkStock(LinkedHashSet<Stock> availableStocks, List<Item> progressItems, List<Item> selectedItems, List<Item> progressIdleItems, Order c){
        //iterate over stock here
        Iterator<Stock> iterStock = availableStocks.iterator();
        while (iterStock.hasNext()) {
            Stock stock = (Stock) iterStock.next();
            Iterator<Item> iterItem = stock.getItems().iterator();
            while (iterItem.hasNext()) {
                Item item = (Item) iterItem.next();
                int currentSelectedStock = item.getTotal();
                String category = item.getCategory();
                String name = item.getFruit().getName();
                if(category.equalsIgnoreCase(c.getCategory()) && name.equalsIgnoreCase(c.getName())){
                    if(item.isInStock()){
                        //deduct the stock here
                        int totalAvailableCart = 0;
                        if(c.getTotal() > currentSelectedStock){
                            totalAvailableCart = c.getTotal() - currentSelectedStock;
                            currentSelectedStock = 0;
                        } else {
                            currentSelectedStock = currentSelectedStock - c.getTotal();
                        }

                        //checking into new list
                        Item updatedItem = new Item();
                        updatedItem.setCategory(category);
                        updatedItem.setFruit(item.getFruit());
                        updatedItem.setTotal(currentSelectedStock);
                        if(currentSelectedStock <= 0){
                            updatedItem.setInStock(false);
                        }
                        progressItems.add(updatedItem);

                        //add into user
                        updatedItem = new Item();
                        updatedItem.setCategory(category);
                        updatedItem.setFruit(item.getFruit());
                        updatedItem.setTotal(c.getTotal() - totalAvailableCart);
                        selectedItems.add(updatedItem);

                        break;
                    }
                } else {
                    //check if previous item already there
                    Iterator<Item> iter = progressItems.iterator();
                    while (iter.hasNext()) {
                        Item it = (Item) iter.next();
                        if(!name.equalsIgnoreCase(it.getFruit().getName())){
                            //checking into new list
                            Item updatedItem = new Item();
                            updatedItem.setCategory(category);
                            updatedItem.setFruit(item.getFruit());
                            updatedItem.setInStock(item.isInStock());
                            updatedItem.setTotal(currentSelectedStock);
                            progressIdleItems.add(updatedItem);
                        }
                    }
                }
            }
        }
        return iterStock;
    }

    public static Iterator<Stock> checkOrder(LinkedHashSet<Stock> availableStocks, List<Item> progressItems, List<Item> selectedItems, List<Item> progressIdleItems, Order c){
        //iterate over stock here
        Iterator<Stock> iterStock = availableStocks.iterator();
        while (iterStock.hasNext()) {
            Stock stock = (Stock) iterStock.next();
            Iterator<Item> iterItem = stock.getItems().iterator();
            while (iterItem.hasNext()) {
                Item item = (Item) iterItem.next();
                int currentSelectedStock = item.getTotal();
                String category = item.getCategory();
                String name = item.getFruit().getName();
                if(category.equalsIgnoreCase(c.getCategory()) && name.equalsIgnoreCase(c.getName())){
                    if(item.isInStock()){
                        //deduct the stock here
                        int totalAvailableCart = 0;
                        if(c.getTotal() > currentSelectedStock){
                            totalAvailableCart = c.getTotal() - currentSelectedStock;
                            currentSelectedStock = 0;
                        } else {
                            currentSelectedStock = currentSelectedStock - c.getTotal();
                        }

                        //checking into new list
                        Item updatedItem = new Item();
                        updatedItem.setCategory(category);
                        updatedItem.setFruit(item.getFruit());
                        updatedItem.setTotal(currentSelectedStock);
                        if(currentSelectedStock <= 0){
                            updatedItem.setInStock(false);
                        }
                        progressItems.add(updatedItem);

                        //add into user
                        updatedItem = new Item();
                        updatedItem.setCategory(category);
                        updatedItem.setFruit(item.getFruit());
                        updatedItem.setTotal(c.getTotal() - totalAvailableCart);
                        selectedItems.add(updatedItem);

                        break;
                    }
                } else {
                    //check if previous item already there
                    Iterator<Item> iter = progressItems.iterator();
                    while (iter.hasNext()) {
                        Item it = (Item) iter.next();
                        if(!name.equalsIgnoreCase(it.getFruit().getName())){
                            //checking into new list
                            Item updatedItem = new Item();
                            updatedItem.setCategory(category);
                            updatedItem.setFruit(item.getFruit());
                            updatedItem.setInStock(item.isInStock());
                            updatedItem.setTotal(currentSelectedStock);
                            progressIdleItems.add(updatedItem);
                        }
                    }
                }
            }
        }
        return iterStock;
    }

    public static void printCartStatus(LinkedHashSet<List<Item>> selectedMandaCart, LinkedHashSet<List<Item>> selectedSusanCart){
        System.out.println("---------------------");
        System.out.println("CART STATUS");
        System.out.println("---------------------");

        //print manda cart
        System.out.println("manda cart");
        for(List<Item> items :selectedMandaCart){
            for(Item item: items){
                String category = item.getCategory();
                String name = item.getFruit().getName();
                int total = item.getTotal();
                System.out.println("item : "+name+ " | category : "+category+ " | total : "+total);
            }
        }

        //print susan cart
        System.out.println("---------------------");
        System.out.println("susan cart");
        for(List<Item> items :selectedSusanCart){
            for(Item item: items){
                String category = item.getCategory();
                String name = item.getFruit().getName();
                int total = item.getTotal();
                System.out.println("item : "+name+ " | category : "+category+ " | total : "+total);
            }
        }
    }

    public void printOrderStatus(LinkedHashSet<List<Item>> selectedMandaOrder, LinkedHashSet<List<Item>> selectedSusanOrder){
        System.out.println("---------------------");
        System.out.println("ORDER STATUS");
        System.out.println("---------------------");

        //print manda cart
        System.out.println("manda order");
        for(List<Item> items :selectedMandaOrder){
            for(Item item: items){
                String category = item.getCategory();
                String name = item.getFruit().getName();
                int total = item.getTotal();
                System.out.println("item : "+name+ " | category : "+category+ " | total : "+total);
            }
        }

        //print susan cart
        System.out.println("---------------------");
        System.out.println("susan order");
        for(List<Item> items :selectedSusanOrder){
            for(Item item: items){
                String category = item.getCategory();
                String name = item.getFruit().getName();
                int total = item.getTotal();
                System.out.println("item : "+name+ " | category : "+category+ " | total : "+total);
            }
        }
    }

    public static void printStockStatus(LinkedHashSet<Stock> availableStocks){
        //print status stocks
        Iterator<Stock> currentStock = availableStocks.iterator();
        while (currentStock.hasNext()) {
            Stock stock = (Stock) currentStock.next();
            System.out.println("---------------------");
            System.out.println("STOCK STATUS");
            System.out.println("---------------------");
            System.out.println("Inventory Location : "+stock.getWarehouse());
            for(Item item: stock.getItems()){
                String category = item.getCategory();
                String name = item.getFruit().getName();
                int total = item.getTotal();
                System.out.println("Items Available : "+name+ " | category : "+category+ " | total : "+total);
            }
        }
    }
}
