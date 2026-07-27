package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.DealerManager;
import models.Dealer;
import java.io.IOException;

public class DealerController {

    @FXML private TableView<Dealer> dealersTable;
    @FXML private TableColumn<Dealer, String> colCode;
    @FXML private TableColumn<Dealer, String> colName;
    @FXML private TableColumn<Dealer, String> colPhone;
    @FXML private TableColumn<Dealer, String> colLocation;

    private DealerManager dealerManager = new DealerManager();

    public void initialize() {
        dealerManager.loadDealers();
        setupColumns();
    }

    private void setupColumns() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("dealerCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    @FXML
    private void handleSelectDealers() {
        dealerManager.selectRandomDealers();
        dealerManager.sortSelectedDealers();
        dealersTable.getItems().clear();
        dealersTable.getItems().addAll(dealerManager.getSelectedDealers());
    }

    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 700);
        Stage stage = (Stage) dealersTable.getScene().getWindow();
        stage.setScene(scene);
    }
}