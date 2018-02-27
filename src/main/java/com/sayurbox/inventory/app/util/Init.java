package com.sayurbox.inventory.app.util;

import com.google.gson.Gson;
import com.sayurbox.inventory.app.model.Item;
import com.sayurbox.inventory.app.model.Stock;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author mrhezqhez@gmail.com
 *
 */
public class Init {

    private Gson gson = new Gson();
    private Stock initalStock;

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

    public Init(){
        try {

            String stockFile = FunctionUtil.getCwd().getAbsolutePath()+"/sample/stocks.json";

            String mandaCartFile = FunctionUtil.getCwd().getAbsolutePath()+"/sample/manda-cart.json";
            String susanCartFile = FunctionUtil.getCwd().getAbsolutePath()+"/sample/susan-cart.json";

            String mandaOrderFile = FunctionUtil.getCwd().getAbsolutePath()+"/sample/manda-order.json";
            String susanOrderFile = FunctionUtil.getCwd().getAbsolutePath()+"/sample/susan-order.json";

            String contentStock = new String(Files.readAllBytes(Paths.get(stockFile)));
            initalStock = gson.fromJson(contentStock, Stock.class);
            availableStocks.add(initalStock);

            mandaCart = new String(Files.readAllBytes(Paths.get(mandaCartFile)));
            susanCart = new String(Files.readAllBytes(Paths.get(susanCartFile)));

            mandaOrder = new String(Files.readAllBytes(Paths.get(mandaOrderFile)));
            susanOrder = new String(Files.readAllBytes(Paths.get(susanOrderFile)));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Stock getInitalStock() {
        return initalStock;
    }

    public void setInitalStock(Stock initalStock) {
        this.initalStock = initalStock;
    }

    public String getSusanCart() {
        return susanCart;
    }

    public void setSusanCart(String susanCart) {
        this.susanCart = susanCart;
    }

    public String getMandaCart() {
        return mandaCart;
    }

    public void setMandaCart(String mandaCart) {
        this.mandaCart = mandaCart;
    }

    public String getSusanOrder() {
        return susanOrder;
    }

    public void setSusanOrder(String susanOrder) {
        this.susanOrder = susanOrder;
    }

    public String getMandaOrder() {
        return mandaOrder;
    }

    public void setMandaOrder(String mandaOrder) {
        this.mandaOrder = mandaOrder;
    }

    public LinkedHashSet<Stock> getAvailableStocks() {
        return availableStocks;
    }

    public void setAvailableStocks(LinkedHashSet<Stock> availableStocks) {
        this.availableStocks = availableStocks;
    }

    public LinkedHashSet<List<Item>> getSelectedMandaCart() {
        return selectedMandaCart;
    }

    public void setSelectedMandaCart(LinkedHashSet<List<Item>> selectedMandaCart) {
        this.selectedMandaCart = selectedMandaCart;
    }

    public LinkedHashSet<List<Item>> getSelectedSusanCart() {
        return selectedSusanCart;
    }

    public void setSelectedSusanCart(LinkedHashSet<List<Item>> selectedSusanCart) {
        this.selectedSusanCart = selectedSusanCart;
    }

    public LinkedHashSet<List<Item>> getSelectedMandaOrder() {
        return selectedMandaOrder;
    }

    public void setSelectedMandaOrder(LinkedHashSet<List<Item>> selectedMandaOrder) {
        this.selectedMandaOrder = selectedMandaOrder;
    }

    public LinkedHashSet<List<Item>> getSelectedSusanOrder() {
        return selectedSusanOrder;
    }

    public void setSelectedSusanOrder(LinkedHashSet<List<Item>> selectedSusanOrder) {
        this.selectedSusanOrder = selectedSusanOrder;
    }

    public List<Item> getBackToStockItems() {
        return backToStockItems;
    }

    public void setBackToStockItems(List<Item> backToStockItems) {
        this.backToStockItems = backToStockItems;
    }


}
