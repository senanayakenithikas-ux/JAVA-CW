package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        java.io.File inventoryClean = new java.io.File(".data/inventory_clean.txt");
        java.io.File dealerClean = new java.io.File(".data/dealers_clean.txt");

        if (!inventoryClean.exists() || inventoryClean.length() == 0) {
            utils.InventoryCleaner.main(new String[]{});
        }
        if (!dealerClean.exists() || dealerClean.length() == 0) {
            utils.DealerCleaner.main(new String[]{});
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/models/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Malabe Tuk-Tuk & Three-wheeler Spares Depot");
        stage.setScene(scene);
        stage.show();
    }
}
