package main;

import models.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    @DisplayName("addPart should return true when adding a new part")
    void addPart_Success() {
        Part part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);

        boolean result = inventoryManager.addPart(part);

        assertTrue(result);
        assertEquals(1, inventoryManager.getTotalCount());
    }

    @Test
    @DisplayName("addPart should return false when adding a duplicate part code")
    void addPart_Duplicate() {
        Part part1 = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
        Part part2 = new Part("P101", "Spark Plug", "NGK", 15.00, 20, "Engine", "2026-01-02", "plug.png", 5);

        inventoryManager.addPart(part1);
        boolean result = inventoryManager.addPart(part2);

        assertFalse(result);
        assertEquals(1, inventoryManager.getTotalCount());
    }

    @Test
    @DisplayName("deletePart should remove part from inventory")
    void deletePart() {
        Part part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
        inventoryManager.addPart(part);

        inventoryManager.deletePart("P101");

        assertEquals(0, inventoryManager.getTotalCount());
    }

    @Test
    @DisplayName("updatePart should modify attributes of existing part")
    void updatePart() {
        Part part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
        inventoryManager.addPart(part);

        inventoryManager.updatePart("P101", "New Brake Pad", "Bosch", 65.00, 15, "Brakes", "2026-02-01", "new_brake.png", 3);

        Part updatedPart = inventoryManager.getParts().get(0);
        assertEquals("New Brake Pad", updatedPart.getName());
        assertEquals("Bosch", updatedPart.getBrand());
        assertEquals(65.00, updatedPart.getPrice());
        assertEquals(15, updatedPart.getQuantity());
        assertEquals(3, updatedPart.getThreshold());
    }

    @Test
    @DisplayName("getLowStockParts should return parts with quantity less than or equal to threshold")
    void getLowStockParts() {
        Part lowStockPart = new Part("P101", "Oil Filter", "Bosch", 10.00, 2, "Filters", "2026-01-01", "filter.png", 5);
        Part normalStockPart = new Part("P102", "Air Filter", "Bosch", 20.00, 10, "Filters", "2026-01-01", "air.png", 5);

        inventoryManager.addPart(lowStockPart);
        inventoryManager.addPart(normalStockPart);

        ArrayList<Part> lowStock = inventoryManager.getLowStockParts();

        assertEquals(1, lowStock.size());
        assertEquals("P101", lowStock.get(0).getPartCode());
    }

    @Test
    @DisplayName("getTotalValue should calculate correct sum of price * quantity")
    void getTotalValue() {
        Part part1 = new Part("P101", "Brake Pad", "Brembo", 50.00, 2, "Brakes", "2026-01-01", "brake.png", 5);
        Part part2 = new Part("P102", "Spark Plug", "NGK", 10.00, 5, "Engine", "2026-01-02", "plug.png", 5);

        inventoryManager.addPart(part1);
        inventoryManager.addPart(part2);

        assertEquals(150.00, inventoryManager.getTotalValue(), 0.001);
    }
}