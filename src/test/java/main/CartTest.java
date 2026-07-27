package main;

import models.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Part testPart;

    @BeforeEach
    void setUp() {
        InventoryManager inventoryManager = new InventoryManager();
        cart = new Cart(inventoryManager);
        testPart = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
    }

    @Test
    @DisplayName("addItem should increase cart size")
    void addItem() {
        cart.addItem(testPart, 2);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(2, cart.getCartItems().get(0).getQuantity());
    }

    @Test
    @DisplayName("removeItem should remove item by partCode")
    void removeItem() {
        cart.addItem(testPart, 2);
        cart.removeItem("P101");

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    @DisplayName("calculateTotal should apply 5% discount when quantity is 3 or more")
    void calculateTotal() {
        cart.addItem(testPart, 3);

        assertEquals(142.50, cart.calculateTotal(), 0.001);
    }

    @Test
    @DisplayName("checkOut should return confirmation message")
    void checkOut() {
        cart.addItem(testPart, 1);
        String result = cart.checkOut();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("clearCart should remove all items")
    void clearCart() {
        cart.addItem(testPart, 2);
        cart.clearCart();

        assertAll("cleared cart",
                () -> assertTrue(cart.getCartItems().isEmpty()),
                () -> assertEquals(0.0, cart.calculateTotal(), 0.001)
        );
    }
}