package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    @DisplayName("setQuantity should update item quantity")
    void setQuantity() {
        Part part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
        CartItem item = new CartItem(part, 2);

        item.setQuantity(5);

        assertEquals(5, item.getQuantity());
    }

    @Test
    @DisplayName("getSubTotal should calculate unit price multiplied by quantity")
    void getSubTotal() {
        Part part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
        CartItem item = new CartItem(part, 3);

        assertEquals(150.00, item.getSubTotal(), 0.001);
    }
}