package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.InventoryManager;
import models.Part;
import java.io.IOException;

public class LowStockController {

    @FXML private TableView<Part> lowStockTable;
    @FXML private TableColumn<Part, String> colCode;
    @FXML private TableColumn<Part, String> colName;
    @FXML private TableColumn<Part, Integer> colQty;
    @FXML private TableColumn<Part, Integer> colThreshold;
    @FXML private TextField txtThreshold;
    @FXML private Label lowStockCountLabel;

    private InventoryManager inventoryManager = new InventoryManager();

    public void initialize() {
        inventoryManager.loadParts();
        setupColumns();
        txtThreshold.setText("10");
        loadLowStockTable();
    }

    private void setupColumns() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colThreshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
    }

    private void loadLowStockTable() {
        lowStockTable.getItems().clear();
        lowStockTable.getItems().addAll(inventoryManager.getLowStockParts());
    }

    @FXML
    private void handleRefresh() {
        inventoryManager.loadParts();
        lowStockCountLabel.setText(String.valueOf(inventoryManager.getLowStockParts().size()));
        loadLowStockTable();
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) lowStockTable.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleUpdateThreshold() {
        try {
            int newThreshold = Integer.parseInt(txtThreshold.getText().trim());
            // update threshold for all parts
            for (int i = 0; i < inventoryManager.getParts().size(); i++) {
                inventoryManager.getParts().get(i).setThreshold(newThreshold);
            }
            inventoryManager.saveParts();
            loadLowStockTable();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}