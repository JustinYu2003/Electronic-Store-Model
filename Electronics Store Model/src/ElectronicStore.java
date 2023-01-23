//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.ArrayList;
import java.util.HashMap;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private String name;
    private ArrayList<Product> stock;
    private double revenue;
    private HashMap<Product, Integer> cart;
    private double cartRevenue;
    private int totalSales;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new ArrayList<Product>();
        cart = new HashMap<Product, Integer>();
        cartRevenue = 0.0;
        totalSales = 0;
    }

    public String getName() {
        return name;
    }

    //additional getters and setters
    public ArrayList<Product> getStock() { return stock; }
    public HashMap<Product, Integer> getCart() { return cart; }
    public double getCartRevenue() { return cartRevenue; }
    public void setCartRevenue(double cartRevenue) { this.cartRevenue = cartRevenue; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public int getTotalSales() { return totalSales; }
    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }

    public boolean addProduct(Product newProduct){
        //Check if any products are identical to the one being added,
        for(int i = 0; i < stock.size(); i++) {
            // if there is an identical product, return false and don't add
            if(stock.get(i).toString().equalsIgnoreCase(newProduct.toString())){
                return false;
            }
        }
        //if no identical product add product and increase the tracker
        stock.add(newProduct);
        return true;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 