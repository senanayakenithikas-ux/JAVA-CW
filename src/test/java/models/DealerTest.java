package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer("D101", "AutoParts Express", "+1-555-0199", "New York");
    }

    @Test
    @DisplayName("setName should update dealer name")
    void setName() {
        dealer.setName("Speedy Auto Parts");
        assertEquals("Speedy Auto Parts", dealer.getName());
    }

    @Test
    @DisplayName("setPhone should update contact phone number")
    void setPhone() {
        dealer.setPhone("+1-555-0200");
        assertEquals("+1-555-0200", dealer.getPhone());
    }

    @Test
    @DisplayName("setLocation should update dealer location")
    void setLocation() {
        dealer.setLocation("Chicago");
        assertEquals("Chicago", dealer.getLocation());
    }
}