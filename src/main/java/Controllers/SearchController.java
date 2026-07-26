package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.InventoryManager;
import models.Part;
import utils.InventorySearch;
import java.io.IOException;
import java.util.ArrayList;

public class SearchController {

    @FXML private TextField txtKeyword;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextField txtMinPrice;
    @FXML private TextField txtMaxPrice;
    @FXML private TableView<Part> searchTable;
    @FXML private TableColumn<Part, String> colCode;
    @FXML private TableColumn<Part, String> colName;
    @FXML private TableColumn<Part, String> colBrand;
    @FXML private TableColumn<Part, Double> colPrice;
    @FXML private TableColumn<Part, Integer> colQty;
    @FXML private TableColumn<Part, String> colCategory;

    private InventoryManager inventoryManager = new InventoryManager();
    private InventorySearch inventorySearch;

    public void initialize() {
        inventoryManager.loadParts();
        inventorySearch = new InventorySearch(inventoryManager);

        categoryCombo.getItems().addAll("All", "Engine", "Electrical", "Bodywork", "Brakes");
        categoryCombo.setValue("All");

        setupColumns();
        loadTable(inventoryManager.getParts());
    }

    private void setupColumns() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void loadTable(ArrayList<Part> parts) {
        searchTable.getItems().clear();
        searchTable.getItems().addAll(parts);
    }

    @FXML
    private void handleSearch() {
        String keyword = txtKeyword.getText();
        String category = categoryCombo.getValue();
        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;

        try {
            if (!txtMinPrice.getText().trim().isEmpty()) {
                minPrice = Double.parseDouble(txtMinPrice.getText().trim());
            }
            if (!txtMaxPrice.getText().trim().isEmpty()) {
                maxPrice = Double.parseDouble(txtMaxPrice.getText().trim());
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a valid number!");
            return;
        }

        ArrayList<Part> results = inventorySearch.search(keyword, category, minPrice, maxPrice);
        loadTable(results);
    }

    @FXML
    private void handleClear() {
        txtKeyword.clear();
        categoryCombo.setValue("All");
        txtMinPrice.clear();
        txtMaxPrice.clear();
        loadTable(inventoryManager.getParts());
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) searchTable.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}