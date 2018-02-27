package com.sayurbox.inventory.app;

import com.google.gson.Gson;
import com.sayurbox.inventory.app.common.XConstants;
import com.sayurbox.inventory.app.model.*;
import org.apache.log4j.Logger;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author mrhezqhez@gmail.com
 *
 *  This is unit test to cover 2 scenario :
 *  selecting and ordering
 */
public class InventoryMainTest {

    final static org.apache.log4j.Logger log = Logger.getLogger(InventoryMainTest.class);

    private Gson gson = new Gson();
    private Stock initalStock;
    private String initalContentStock;

    private String susanCart;
    private String mandaCart;

    private String susanOrder;
    private String mandaOrder;

    private LinkedHashSet<Stock> availableStocks = new LinkedHashSet<Stock>();
    private LinkedHashSet<List<Item>> selectedMandaCart = new LinkedHashSet<List<Item>>();
    private LinkedHashSet<List<Item>> selectedSusanCart = new LinkedHashSet<List<Item>>();
    private LinkedHashSet<List<Item>> selectedMandaOrder = new LinkedHashSet<List<Item>>();
    private LinkedHashSet<List<Item>> selectedSusanOrder = new LinkedHashSet<List<Item>>();
    private List<Item> backToStockItems = new ArrayList<>();

    public InventoryMainTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        log.debug("populate sample data");
        try {
            String stockFile = getCwd().getAbsolutePath()+"/sample/stocks.json";
            String mandaCartFile = getCwd().getAbsolutePath()+"/sample/manda-cart.json";
            String susanCartFile = getCwd().getAbsolutePath()+"/sample/susan-cart.json";
            String mandaOrderFile = getCwd().getAbsolutePath()+"/sample/manda-order.json";
            String susanOrderFile = getCwd().getAbsolutePath()+"/sample/susan-order.json";

            String contentStock = new String(Files.readAllBytes(Paths.get(stockFile)));
            initalStock = gson.fromJson(contentStock, Stock.class);
            availableStocks.add(initalStock);
            System.out.println("sample stock data object : "+initalStock);

            mandaCart = new String(Files.readAllBytes(Paths.get(mandaCartFile)));
            System.out.println("mandaCart = "+mandaCart);
            susanCart = new String(Files.readAllBytes(Paths.get(susanCartFile)));
            System.out.println("susanCart = "+susanCart);

            mandaOrder = new String(Files.readAllBytes(Paths.get(mandaOrderFile)));
            System.out.println("mandaOrder = "+mandaOrder);
            susanOrder = new String(Files.readAllBytes(Paths.get(susanOrderFile)));
            System.out.println("susanOrder = "+susanOrder);

            initalContentStock = gson.toJson(initalStock);
            System.out.println("sample stock data content : "+initalContentStock);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {}

    @Test
    public void selectingSampleScenario() throws Exception {
        try {
            log.debug("selecting scenario");

            Stock newStock = new Stock();
            List<Item> items = new ArrayList<>();

            Item itemApple = new Item();
            itemApple.setFruit(new Apel("Apel", "Malang"));
            itemApple.setCategory(XConstants.CATEGORY_ITEM_FRUIT);
            itemApple.setTotal(5);
            items.add(itemApple);

            Item itemPepaya = new Item();
            itemPepaya.setFruit(new Pepaya("Pepaya", "California"));
            itemPepaya.setCategory(XConstants.CATEGORY_ITEM_FRUIT);
            itemPepaya.setTotal(1);
            items.add(itemPepaya);

            Item itemMangga = new Item();
            itemMangga.setFruit(new Mangga("Mangga", "Indramayu"));
            itemMangga.setCategory(XConstants.CATEGORY_ITEM_FRUIT);
            itemMangga.setTotal(4);
            items.add(itemMangga);

            newStock.setItems(items);
            newStock.setWarehouse("Jakarta");

            String output = gson.toJson(newStock);
            System.out.println("result : "+output);

            Stock responseStock = gson.fromJson(output, Stock.class);

            for(Item item : responseStock.getItems()){
                String name = item.getFruit().getName();
                String farmLocation = item.getFruit().getFarmLocation();
                int total = item.getTotal();
                String category = item.getCategory();
                System.out.println("name : "+name+ " | farmLocation : "+farmLocation+ " | total : "+total+ " | category : "+category);
            }

            String warehouse = responseStock.getWarehouse();
            System.out.println("warehouse : "+warehouse);

            //start selecting
            Cart newCart = new Cart();
            List<Order> mandaItems = new ArrayList<>();
            Order mandaItem1 = new Order();
            mandaItem1.setName("Apple");
            mandaItem1.setCategory(XConstants.CATEGORY_ITEM_FRUIT);
            mandaItem1.setTotal(2);
            mandaItems.add(mandaItem1);

            Order mandaItem2 = new Order();
            mandaItem2.setName("Mangga");
            mandaItem2.setCategory(XConstants.CATEGORY_ITEM_FRUIT);
            mandaItem2.setTotal(4);
            mandaItems.add(mandaItem2);

            newCart.setCart(mandaItems);
            newCart.setCustomerId(new User("manda"));
            newCart.setActionType(XConstants.ACTION_CART);

            String outputnewCart = gson.toJson(newCart);
            System.out.println("result outputnewCart : "+outputnewCart);

            Cart mandaSelectedItems = gson.fromJson(mandaCart, Cart.class);
            Cart susanSelectedItems = gson.fromJson(susanCart, Cart.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Iterator<Stock> checkStockNew(List<Item> progressItems, List<Item> selectedItems, List<Item> progressIdleItems, Order c){
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

    public Iterator<Stock> checkOrder(List<Item> progressItems, List<Item> selectedItems, List<Item> progressIdleItems, Order c){
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

    public void checkStock(List<Item> progressItems, List<Item> progressIdleItems, Order c){
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
                        currentSelectedStock = currentSelectedStock - c.getTotal();
                        //checking into new list
                        Item updatedItem = new Item();
                        updatedItem.setCategory(category);
                        updatedItem.setFruit(item.getFruit());
                        updatedItem.setTotal(currentSelectedStock);
                        if(currentSelectedStock == 0){
                            updatedItem.setInStock(false);
                        }
                        progressItems.add(updatedItem);
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
                            updatedItem.setTotal(currentSelectedStock);
                            progressIdleItems.add(updatedItem);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void mandaCartScenario() throws Exception {
        try {
            log.debug("manda cart scenario");

            Cart mandaSelectedCart = gson.fromJson(mandaCart, Cart.class);

            if(mandaSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_CART)){
                Stock progressStock = new Stock();
                List<Item> progressItems = new ArrayList<>();
                List<Item> progressIdleItems = new ArrayList<>();
                List<Item> selectedItems = new ArrayList<>();
                Iterator<Stock> iterStock = null;
                for(Order c: mandaSelectedCart.getCart()){
                    //iterate over stock here
                    iterStock = checkStockNew(progressItems, selectedItems, progressIdleItems, c);
                }
                if(progressIdleItems.size() > 0){
                    for(Item idleItem : progressIdleItems){
                        progressItems.add(idleItem);
                    }
                }
                selectedMandaCart.add(selectedItems);
                progressStock.setItems(progressItems);
                progressStock.setWarehouse(initalStock.getWarehouse());
                iterStock.remove();
                availableStocks.add(progressStock); //update last stock
            }

            log.debug("manda cart scenario end");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void susanCartScenario() throws Exception {
        try {
            log.debug("susan cart scenario");

            Cart susanSelectedCart = gson.fromJson(susanCart, Cart.class);

            if(susanSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_CART)){
                Stock progressStock = new Stock();
                List<Item> progressItems = new ArrayList<>();
                List<Item> progressIdleItems = new ArrayList<>();
                List<Item> selectedItems = new ArrayList<>();
                Iterator<Stock> iterStock = null;
                for(Order c: susanSelectedCart.getCart()){
                    //iterate over stock here
                    iterStock = checkStockNew(progressItems, selectedItems, progressIdleItems, c);
                }
                if(progressIdleItems.size() > 0){
                    for(Item idleItem : progressIdleItems){
                        progressItems.add(idleItem);
                    }
                }
                selectedSusanCart.add(selectedItems);
                progressStock.setItems(progressItems);
                progressStock.setWarehouse(initalStock.getWarehouse());

                iterStock.remove();
                availableStocks.add(progressStock); //update last stock
            }

            log.debug("susan cart scenario end");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void mandaOrderScenario() throws Exception {
        try {
            log.debug("manda order scenario");

            Cart mandaSelectedCart = gson.fromJson(mandaOrder, Cart.class);

            if(mandaSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_ORDER)){
                Stock progressStock = new Stock();
                List<Item> selectedOrderItems = new ArrayList<>();
                for(Order c: mandaSelectedCart.getCart()){ //order data
                    //iterate over selected cart here
                    Iterator<List<Item>> it = selectedMandaCart.iterator();
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
                                    backToStockItems.add(refundItem);

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
                                        backToStockItems.add(theItem);
                                    }
                                }
                            }
                        }
                    }
                }
                selectedMandaOrder.add(selectedOrderItems);
            }

            log.debug("manda order scenario end");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void susanOrderScenario() throws Exception {
        try {
            log.debug("susan order scenario");

            Cart susanSelectedCart = gson.fromJson(susanOrder, Cart.class);

            if(susanSelectedCart.getActionType().equalsIgnoreCase(XConstants.ACTION_ORDER)){
                Stock progressStock = new Stock();
                List<Item> selectedOrderItems = new ArrayList<>();
                for(Order c: susanSelectedCart.getCart()){ //order data
                    //iterate over selected cart here
                    Iterator<List<Item>> it = selectedSusanCart.iterator();
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
                                    backToStockItems.add(refundItem);

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
                                        backToStockItems.add(theItem);
                                    }
                                }
                            }
                        }
                    }
                }
                selectedSusanOrder.add(selectedOrderItems);
            }

            log.debug("susan order scenario end");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void selectingScenario() throws Exception {
        try {
            log.debug("selecting scenario");
            mandaCartScenario();
            susanCartScenario();
            printCartStatus();
            printStockStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void orderingScenario() throws Exception {
        try {
            log.debug("ordering scenario");
            selectingScenario();

            mandaOrderScenario();
            susanOrderScenario();
            assigningStock();
            printOrderStatus();
            printStockStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assigningStock(){
        if(backToStockItems.size() > 0){
            Stock progressStock = new Stock();
            progressStock.setItems(backToStockItems);
            progressStock.setWarehouse(initalStock.getWarehouse());
            removeStock();
            availableStocks.add(progressStock); //update last stock
        }
    }

    public void removeStock(){
        Iterator<Stock> currentStock = availableStocks.iterator();
        while (currentStock.hasNext()) {
            Stock stockNow = (Stock) currentStock.next();
            System.out.println("updating stock...");
            currentStock.remove();
        }
    }


    public void printCartStatus(){
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

    public void printOrderStatus(){
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

    public void printStockStatus(){
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



    private File getCwd() {
        return new File("").getAbsoluteFile();
    }
}
