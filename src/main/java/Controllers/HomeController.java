package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML private Button btnInventory;
    @FXML private Button btnSearch;
    @FXML private Button btnDealers;
    @FXML private Button btnCart;
    @FXML private Button btnLowStock;



    @FXML
    private void handleInventory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/inventory.fxml"));
        Scene scene = new Scene(loader.load(),1200,700);
        Stage stage = (Stage) btnInventory.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleSearch() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/search.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) btnSearch.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleDealers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/dealers.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) btnDealers.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleCart() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/cart.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) btnCart.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleLowStock() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/lowstock.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) btnLowStock.getScene().getWindow();
        stage.setScene(scene);
    }
}
