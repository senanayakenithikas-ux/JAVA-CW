package utils;

import main.InventoryManager;
import models.Part;

import java.util.ArrayList;

public class InventorySearch {
    private InventoryManager inventoryManager;

    public InventorySearch(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public ArrayList<Part> search(String keyword, String category, double minPrice, double maxPrice) {
        ArrayList<Part> result = new ArrayList<>();
        ArrayList<Part> allParts = inventoryManager.getParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            boolean matches = true;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String cleanKeyword = keyword.trim().toLowerCase();
                boolean nameMatches = part.getName().toLowerCase().contains(cleanKeyword);
                boolean brandMatches = part.getBrand().toLowerCase().contains(cleanKeyword);

                if (!nameMatches && !brandMatches) {
                    matches = false;
                }
            }
            if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All")) {
                if (!part.getCategory().equalsIgnoreCase(category.trim())) {
                    matches = false;
                }
            }

            if (minPrice >= 0 && maxPrice >= 0 && maxPrice >= minPrice) {
                if (part.getPrice() < minPrice || part.getPrice() > maxPrice) {
                    matches = false;
                }
            }

            if (matches) {
                result.add(part);
            }
        }
        return result;
    }
}
