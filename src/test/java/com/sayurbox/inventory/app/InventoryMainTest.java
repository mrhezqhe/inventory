package com.sayurbox.inventory.app;

import com.google.gson.Gson;
import com.sayurbox.inventory.app.model.*;
import org.apache.log4j.Logger;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("sample stock data object : "+initalStock);

            mandaCart = new String(Files.readAllBytes(Paths.get(mandaCartFile)));
            System.out.println("mandaCart : "+mandaCart);
            susanCart = new String(Files.readAllBytes(Paths.get(susanCartFile)));
            System.out.println("susanCart : "+susanCart);

            mandaOrder = new String(Files.readAllBytes(Paths.get(mandaOrderFile)));
            System.out.println("mandaOrder : "+mandaOrder);
            susanOrder = new String(Files.readAllBytes(Paths.get(susanOrderFile)));
            System.out.println("susanOrder : "+susanOrder);

            initalContentStock = gson.toJson(initalStock);
            System.out.println("sample stock data content : "+initalContentStock);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {}

    @Test
    public void selectingScenario() throws Exception {
        try {
            log.debug("selecting scenario");

            Stock newStock = new Stock();
            List<Item> items = new ArrayList<>();

            Item itemApple = new Item();
            itemApple.setFruit(new Apel("Apel", "Malang"));
            itemApple.setCategory("Fruit");
            itemApple.setTotal(5);
            items.add(itemApple);

            Item itemPepaya = new Item();
            itemPepaya.setFruit(new Pepaya("Pepaya", "California"));
            itemPepaya.setCategory("Fruit");
            itemPepaya.setTotal(1);
            items.add(itemPepaya);

            Item itemMangga = new Item();
            itemMangga.setFruit(new Mangga("Mangga", "Indramayu"));
            itemMangga.setCategory("Fruit");
            itemMangga.setTotal(4);
            items.add(itemMangga);

            newStock.setItems(items);
            newStock.setWarehouse("jakarta");

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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void orderingScenario() throws Exception {
        try {
            log.debug("ordering scenario");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private File getCwd() {
        return new File("").getAbsoluteFile();
    }
}
