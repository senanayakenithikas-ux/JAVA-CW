package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.InventoryManager;
import models.Part;
import java.io.*;
import java.util.ArrayList;

public class InventoryController {

    @FXML private TableView<Part> inventoryTable;
    @FXML private TableColumn<Part, String> colCode;
    @FXML private TableColumn<Part, String> colName;
    @FXML private TableColumn<Part, String> colBrand;
    @FXML private TableColumn<Part, Double> colPrice;
    @FXML private TableColumn<Part, Integer> colQty;
    @FXML private TableColumn<Part, String> colCategory;
    @FXML private TableColumn<Part, String> colDate;
    @FXML private TableColumn<Part, Integer> colThreshold;

    @FXML private ImageView partImage;
    @FXML private Label totalCountLabel;
    @FXML private Label totalValueLabel;

    @FXML private TextField txtPartCode;
    @FXML private TextField txtName;
    @FXML private TextField txtBrand;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtCategory;
    @FXML private TextField txtThreshold;
    @FXML private TextField txtDate;
    @FXML private TextField txtImage;

    private InventoryManager inventoryManager = new InventoryManager();

    public void initialize() {
        inventoryManager.loadParts();
        inventoryManager.sortParts();
        setupColumns();
        loadTable();
        setupSelectionListener();
    }

    private void setupColumns() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        colThreshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void loadTable() {
        inventoryTable.getItems().clear();
        inventoryManager.sortParts();
        inventoryTable.getItems().addAll(inventoryManager.getParts());
        totalCountLabel.setText(String.valueOf(inventoryManager.getTotalCount()));
        totalValueLabel.setText("Rs. " + String.format("%.2f", inventoryManager.getTotalValue()));
    }

    private void setupSelectionListener() {
        inventoryTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        fillForm(newVal);
                        loadImage(newVal.getImageFile());
                    }
                }
        );
    }

    private void fillForm(Part part) {
        txtPartCode.setText(part.getPartCode());
        txtName.setText(part.getName());
        txtBrand.setText(part.getBrand());
        txtPrice.setText(String.valueOf(part.getPrice()));
        txtQuantity.setText(String.valueOf(part.getQuantity()));
        txtCategory.setText(part.getCategory());
        txtThreshold.setText(String.valueOf(part.getThreshold()));
        txtDate.setText(part.getDateAdded());
        txtImage.setText(part.getImageFile());
    }

    private void loadImage(String imageFile) {
        if (imageFile == null || imageFile.trim().isEmpty()
                     || imageFile.equalsIgnoreCase("No Image")
                     || imageFile.equalsIgnoreCase("No image file")){
            loadDefaultImage();
            return;
        }
        try (InputStream stream = getClass().getResourceAsStream("/assets/" + imageFile.trim())) {
            if (stream != null) {
                partImage.setImage(new Image(stream));
            } else {
                loadDefaultImage();
            }
        } catch (Exception e) {
            loadDefaultImage();
        }
    }

    private void loadDefaultImage() {
        try (InputStream defaultStream = getClass().getResourceAsStream("/assets/default.png")) {
            if (defaultStream != null) {
                partImage.setImage(new Image(defaultStream));
            } else {
                partImage.setImage(null);
            }
        } catch (Exception e) {
            partImage.setImage(null);
        }
    }

    @FXML
    private void handleAdd() {
        try {
            String partCode = txtPartCode.getText().trim();
            String name = txtName.getText().trim();
            String brand = txtBrand.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            String category = txtCategory.getText().trim();
            int threshold = Integer.parseInt(txtThreshold.getText().trim());

            if (partCode.isEmpty() || name.isEmpty() || category.isEmpty()) {
                showAlert("Error", "Part Code, Name and Category are required!");
                return;
            }

            String imageFile = txtImage.getText().trim().isEmpty() ? "No Image" : txtImage.getText().trim();
            String dateAdded = txtDate.getText().trim();
            Part part = new Part(partCode, name, brand, price, quantity, category, dateAdded, imageFile, threshold);
            inventoryManager.addPart(part);
            inventoryManager.saveParts();
            boolean added = inventoryManager.addPart(part);
            if (added) {
                inventoryManager.saveParts();
                loadTable();
                handleClear();
                showAlert("Success", "Part added successfully!");}

        } catch (NumberFormatException e) {
            showAlert("Error", "Price, Quantity and Threshold must be valid numbers!");
        }
    }

    @FXML
    private void handleDelete() {
        Part selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a part to delete!");
            return;
        }
        inventoryManager.deletePart(selected.getPartCode());
        inventoryManager.saveParts();
        loadTable();
        showAlert("Success", "Part deleted successfully!");
    }

    @FXML
    private void handleUpdate() {
        try {
            String partCode = txtPartCode.getText().trim();
            String name = txtName.getText().trim();
            String brand = txtBrand.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            String category = txtCategory.getText().trim();
            int threshold = Integer.parseInt(txtThreshold.getText().trim());

            String imageFile = txtImage.getText().trim().isEmpty() ? "No Image" : txtImage.getText().trim();
            String dateAdded = txtDate.getText().trim();
            inventoryManager.updatePart(partCode, name, brand, price, quantity, category, dateAdded,imageFile);
            inventoryManager.saveParts();
            loadTable();
            handleClear();
            showAlert("Success", "Part updated successfully!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Price, Quantity and Threshold must be valid numbers!");
        }
    }

    @FXML
    private void handleClear() {
        txtPartCode.clear();
        txtName.clear();
        txtBrand.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtCategory.clear();
        txtThreshold.clear();
        partImage.setImage(null);
        txtDate.clear();
        txtImage.clear();
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) inventoryTable.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(txtImage.getScene().getWindow());
        if (file != null) {
            txtImage.setText(file.getName());
            loadImage(file.getName());
        }
    }

    @FXML private TextField txtLowStockThreshold;
    @FXML private Label lowStockCountLabel;

    @FXML
    private void handleCheckLowStock() {
        try {
            int threshold = Integer.parseInt(txtLowStockThreshold.getText().trim());
            int count = 0;
            for (int i = 0; i < inventoryManager.getParts().size(); i++) {
                if (inventoryManager.getParts().get(i).getQuantity() < threshold) {
                    count++;
                }
            }
            lowStockCountLabel.setText(String.valueOf(count));
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number!");
        }
    }
}