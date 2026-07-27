package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Cart;
import main.InventoryManager;
import models.CartItem;
import models.Part;
import java.io.IOException;

public class CartController {

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, String> colCode;
    @FXML private TableColumn<Part, String> colName;
    @FXML private TableColumn<Part, Double> colPrice;
    @FXML private TableColumn<Part, Integer> colStock;

    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> colCartName;
    @FXML private TableColumn<CartItem, Integer> colCartQty;
    @FXML private TableColumn<CartItem, Double> colCartPrice;

    @FXML private TextField txtQuantity;
    @FXML private Label totalLabel;
    @FXML private Label discountLabel;

    private InventoryManager inventoryManager = new InventoryManager();
    private Cart cart;

    public void initialize() {
        inventoryManager.loadParts();
        cart = new Cart(inventoryManager);
        setupColumns();
        loadPartsTable();
    }

    private void setupColumns() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        colCartName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        colCartQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCartPrice.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getPart().getPrice() * data.getValue().getQuantity()
                ).asObject()
        );
    }

    private void loadPartsTable() {
        partsTable.getItems().clear();
        partsTable.getItems().addAll(inventoryManager.getParts());
    }

    private void refreshCartTable() {
        cartTable.getItems().clear();
        cartTable.getItems().addAll(cart.getCartItems());
        totalLabel.setText("Rs. " + String.format("%.2f", cart.calculateTotal()));
    }

    @FXML
    private void handleAddToCart() {
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a part!");
            return;
        }
        try {
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            String result = cart.addItem(selected, qty);
            showAlert("Info", result);
            refreshCartTable();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid quantity!");
        }
    }

    @FXML
    private void handleRemove() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select an item to remove!");
            return;
        }
        cart.removeItem(selected.getPart().getPartCode());
        refreshCartTable();
    }

    @FXML
    private void handleCheckout() {
        String result = cart.checkOut();
        showAlert("Checkout", result);
        loadPartsTable();
        refreshCartTable();
    }

    @FXML
    private void handleClearCart() {
        cart.clearCart();
        refreshCartTable();
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) partsTable.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}