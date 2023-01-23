import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {
    //Instance variables
    private ElectronicStore model;
    private ElectronicStoreView view;

    public void start(Stage primaryStage) {
        //create an instance of electronicstore
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);
        //event handlers
        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                //Enable add button if an item is selected, if not disable addbutton
                view.update(view.getStockList().getSelectionModel().getSelectedIndex(), -1,
                        view.getStockList().getSelectionModel().getSelectedItem(), false);
            }
        });
        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                //Enable remove button if an item is selected, if not disable removeButton
                view.update(-1, view.getCartList().getSelectionModel().getSelectedIndex(), "", false);
            }
        });
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Gets the text of the selected index
                String selectedProduct = view.getStockList().getSelectionModel().getSelectedItem();
                //Looks for the item in stock with the same toString
                for(int i = 0; i < model.getStock().size(); i++) {
                    if(selectedProduct.equals(model.getStock().get(i).toString())) {
                        //Once, found, updates the cart revenue and updates the stock, sold, and cart quantities
                        model.setCartRevenue(model.getCartRevenue() + model.getStock().get(i).sellUnits(1));
                        if(model.getCart().containsKey(model.getStock().get(i))) {
                            model.getCart().put(model.getStock().get(i),
                                    model.getCart().get(model.getStock().get(i))+1);
                        }else {
                            model.getCart().put(model.getStock().get(i), 1);
                        }
                        //Then breaks the loop
                        break;
                    }
                }
                //Update the View
                view.update(view.getStockList().getSelectionModel().getSelectedIndex(), -1, selectedProduct, false);
            }
        });
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Get the text of the selected index
                String[] arrSelectedProduct = view.getCartList().getSelectionModel().getSelectedItem().split(" ", 3);
                String selectedProduct = arrSelectedProduct[2];
                //Look for the product in stock with the same toString
                for(int i = 0; i < model.getStock().size(); i++) {
                    if(selectedProduct.equals(model.getStock().get(i).toString())) {
                        //Add stock quantity back and sold quantity down
                        model.getStock().get(i).setStockQuantity(model.getStock().get(i).getStockQuantity() + 1);
                        model.getStock().get(i).setSoldQuantity(model.getStock().get(i).getSoldQuantity() - 1);
                        //Lower the cart revenue back down
                        model.setCartRevenue(model.getCartRevenue() - model.getStock().get(i).getPrice());
                        //Get rid of one product from the cart
                        if(model.getCart().get(model.getStock().get(i)) > 1) {
                            model.getCart().put(model.getStock().get(i), model.getCart().get(model.getStock().get(i)) - 1);
                        }else {
                            model.getCart().remove(model.getStock().get(i));
                        }
                    }
                }
                //Update the view
                view.update(-1, -1, "", false);
            }
        });
        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //The cart should clear
                model.getCart().clear();
                //Number of sales increases by 1
                model.setTotalSales(model.getTotalSales() + 1);
                //Revenue increases by total value of the cart and reset cart value
                model.setRevenue(model.getRevenue() + model.getCartRevenue());
                model.setCartRevenue(0.0);
                //Update view
                view.update(-1, -1, "", true);
            }
        });
        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Resets everything
                start(primaryStage);
            }
        });
        //Update view
        view.update(-1, -1, "", true);
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
