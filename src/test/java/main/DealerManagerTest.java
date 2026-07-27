package main;

import models.Dealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DealerManagerTest {

    private DealerManager dealerManager;

    @BeforeEach
    void setUp() {
        dealerManager = new DealerManager();
    }

    @Test
    @DisplayName("loadDealers should attempt to read data into dealers list")
    void loadDealers() {
        dealerManager.loadDealers();
        assertNotNull(dealerManager.getDealers());
    }

    @Test
    @DisplayName("selectRandomDealers should return error if fewer than 4 dealers exist")
    void selectRandomDealers_NotEnoughDealers() {
        dealerManager.getDealers().add(new Dealer("D01", "Dealer One", "123", "NY"));
        dealerManager.getDealers().add(new Dealer("D02", "Dealer Two", "456", "LA"));

        String result = dealerManager.selectRandomDealers();

        assertEquals("Error: Not enough dealers in the list to pick 4 unique ones.", result);
        assertEquals(0, dealerManager.getSelectedDealers().size());
    }

    @Test
    @DisplayName("selectRandomDealers should pick 4 unique dealers when enough exist")
    void selectRandomDealers_Success() {
        dealerManager.getDealers().add(new Dealer("D01", "Dealer One", "111", "Location A"));
        dealerManager.getDealers().add(new Dealer("D02", "Dealer Two", "222", "Location B"));
        dealerManager.getDealers().add(new Dealer("D03", "Dealer Three", "333", "Location C"));
        dealerManager.getDealers().add(new Dealer("D04", "Dealer Four", "444", "Location D"));
        dealerManager.getDealers().add(new Dealer("D05", "Dealer Five", "555", "Location E"));

        String result = dealerManager.selectRandomDealers();

        assertEquals("4 unique dealers randomly selected Successfully", result);
        assertEquals(4, dealerManager.getSelectedDealers().size());
    }

    @Test
    @DisplayName("sortSelectedDealers should sort selected dealers alphabetically by location")
    void sortSelectedDealers() {
        dealerManager.getDealers().add(new Dealer("D01", "Dealer One", "111", "Tokyo"));
        dealerManager.getDealers().add(new Dealer("D02", "Dealer Two", "222", "Amsterdam"));
        dealerManager.getDealers().add(new Dealer("D03", "Dealer Three", "333", "New York"));
        dealerManager.getDealers().add(new Dealer("D04", "Dealer Four", "444", "Berlin"));

        dealerManager.selectRandomDealers();
        dealerManager.sortSelectedDealers();

        ArrayList<Dealer> sorted = dealerManager.getSelectedDealers();

        assertTrue(sorted.get(0).getLocation().compareTo(sorted.get(1).getLocation()) <= 0);
        assertTrue(sorted.get(1).getLocation().compareTo(sorted.get(2).getLocation()) <= 0);
        assertTrue(sorted.get(2).getLocation().compareTo(sorted.get(3).getLocation()) <= 0);
    }
}