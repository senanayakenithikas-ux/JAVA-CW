package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    private Part part;

    @BeforeEach
    void setUp() {
        part = new Part("P101", "Brake Pad", "Brembo", 50.00, 10, "Brakes", "2026-01-01", "brake.png", 5);
    }

    @Test
    @DisplayName("Constructor and getters should initialize fields correctly")
    void constructorAndGetters() {
        assertAll("Part constructor",
                () -> assertEquals("P101", part.getPartCode()),
                () -> assertEquals("Brake Pad", part.getName()),
                () -> assertEquals("Brembo", part.getBrand()),
                () -> assertEquals(50.00, part.getPrice(), 0.001),
                () -> assertEquals(10, part.getQuantity()),
                () -> assertEquals("Brakes", part.getCategory()),
                () -> assertEquals("2026-01-01", part.getDateAdded()),
                () -> assertEquals("brake.png", part.getImageFile()),
                () -> assertEquals(5, part.getThreshold())
        );
    }

    @Test
    @DisplayName("setPrice should update price correctly")
    void setPrice() {
        part.setPrice(75.50);
        assertEquals(75.50, part.getPrice(), 0.001);
    }

    @Test
    @DisplayName("setQuantity should update quantity correctly")
    void setQuantity() {
        part.setQuantity(25);
        assertEquals(25, part.getQuantity());
    }
}