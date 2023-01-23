import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class ElectronicStoreView extends Pane{
    //Instance Variables
    private Label storeSummary, salesNumber, revenue, moneyPerSale, mostPopItems, storeStock, currCart;
    private TextField numSalesField, revField, monPerSaleField;
    private ListView<String> stockList, cartList, popItemsList;
    private Button resetButton, addButton, removeButton, completeButton;
    private ElectronicStore model;

    //getters
    public ListView<String> getStockList() { return stockList; }
    public ListView<String> getCartList() { return cartList; }
    public Button getResetButton() { return resetButton; }
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getCompleteButton() { return completeButton; }

    public ElectronicStoreView(ElectronicStore m) {
        model = m;
        //Create Labels
        storeSummary = new Label("Store Summary:");
        storeSummary.relocate(40, 10);
        salesNumber = new Label("# Sales:");
        salesNumber.relocate(30, 40);
        revenue = new Label("Revenue:");
        revenue.relocate(20, 70);
        moneyPerSale = new Label("$ / Sale:");
        moneyPerSale.relocate(30, 100);
        mostPopItems = new Label("Most Popular Items:");
        mostPopItems.relocate(30, 130);
        storeStock = new Label("Store Stock:");
        storeStock.relocate(280, 10);
        currCart = new Label("Current Cart($" + model.getCartRevenue() + "):");
        currCart.relocate(600, 10);
        //Create TextFields
        numSalesField = new TextField();
        numSalesField.relocate(85, 30);
        numSalesField.setPrefSize(75, 30);
        numSalesField.setEditable(false);
        numSalesField.setText(Integer.toString(model.getTotalSales()));
        revField = new TextField();
        revField.relocate(85, 65);
        revField.setPrefSize(75, 30);
        revField.setEditable(false);
        revField.setText(Double.toString(model.getRevenue()));
        monPerSaleField = new TextField();
        monPerSaleField.relocate(85, 100);
        monPerSaleField.setPrefSize(75, 30);
        monPerSaleField.setEditable(false);
        monPerSaleField.setText("N/A");
        //Create ListView
        stockList = new ListView<String>();
        stockList.relocate(170, 30);
        stockList.setPrefSize(300, 300);
        cartList = new ListView<String>();
        cartList.relocate(480, 30);
        cartList.setPrefSize(300, 300);
        popItemsList = new ListView<String>();
        popItemsList.relocate(10, 150);
        popItemsList.setPrefSize(150, 180);
        //Create Buttons
        resetButton = new Button("Reset Store");
        resetButton.relocate(30, 335);
        resetButton.setPrefSize(110, 45);
        addButton = new Button("Add to Cart");
        addButton.relocate(260, 335);
        addButton.setPrefSize(130, 45);
        addButton.setDisable(true);
        removeButton = new Button("Remove from Cart");
        removeButton.relocate(480, 335);
        removeButton.setPrefSize(150, 45);
        removeButton.setDisable(true);
        completeButton = new Button("Complete Sale");
        completeButton.relocate(630, 335);
        completeButton.setPrefSize(150, 45);
        completeButton.setDisable(true);
        //addAll the components
        getChildren().addAll(storeSummary, salesNumber, revenue, moneyPerSale, mostPopItems, storeStock,
                currCart, numSalesField, revField, monPerSaleField, stockList, cartList, popItemsList,
                resetButton, addButton, removeButton, completeButton);
        //set view's size
        setPrefSize(800, 400);
    }
    public void update(int selectedStockIndex, int selectedCartIndex, String selectedStockText, boolean didSale) {
        //Get a string arraylist of all the products toString
        ArrayList<String> products = new ArrayList<String>();
        for(int i = 0; i < model.getStock().size(); i++) {
            if(model.getStock().get(i).getStockQuantity() > 0) {
                products.add(model.getStock().get(i).toString());
            }
        }
        //Make an array to store the products
        Product[] sortStock = new Product[model.getStock().size()];
        //Copy the stock into it
        for(int i = 0; i < model.getStock().size(); i++) {
            sortStock[i] = model.getStock().get(i);
        }
        //Sort the array
        for(int i = 0; i < sortStock.length-1; i++) {
            for(int j = i+1; j < sortStock.length; j++) {
                if(sortStock[i].getSoldQuantity() < sortStock[j].getSoldQuantity()) {
                    Product p = sortStock[j];
                    sortStock[j] = sortStock[i];
                    sortStock[i] = p;
                }
            }
        }
        //Turn the array into an arrayList
        ArrayList<String> sortedStock = new ArrayList<String>();
        for(int i = 0; i < 3; i++) {
            sortedStock.add(sortStock[i].toString());
        }
        //Get a string arraylist representation of the cart
        ArrayList<String> cartItems = new ArrayList<String>();
        for(Product p: model.getCart().keySet()) {
            cartItems.add(model.getCart().get(p) + " x " + p.toString());
        }
        //Set items
        stockList.setItems(FXCollections.observableArrayList(products));
        if(didSale) {
            popItemsList.setItems(FXCollections.observableArrayList(sortedStock));
        }
        cartList.setItems(FXCollections.observableArrayList(cartItems));
        //Enable add button if selected index is valid
        if(selectedStockIndex < products.size() && selectedStockIndex >= 0 && products.contains(selectedStockText)) {
            addButton.setDisable(false);
        }else {
            addButton.setDisable(true);
        }
        //Enable remove button if selected index is valid
        if(selectedCartIndex < model.getCart().size() && selectedCartIndex >= 0) {
            removeButton.setDisable(false);
        }else {
            removeButton.setDisable(true);
        }
        //Enables Complete sale button if cart's size is greater than 0
        if(model.getCart().size() > 0) {
            completeButton.setDisable(false);
        }else {
            completeButton.setDisable(true);
        }
        //updates label and textfield text
        currCart.setText("Current Cart($" + model.getCartRevenue() + "):");
        numSalesField.setText(Integer.toString(model.getTotalSales()));
        revField.setText(Double.toString(model.getRevenue()));
        if(model.getTotalSales() == 0) {
            monPerSaleField.setText("N/A");
        }else {
            monPerSaleField.setText(String.format("%.2f", model.getRevenue()/model.getTotalSales()));
        }
    }
}


