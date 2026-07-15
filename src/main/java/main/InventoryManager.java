package main;

import models.Part;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<Part> parts = new ArrayList<>();

    public void loadParts() {

        String fileName = "inventory_clean.txt";
        parts.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine())!= null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String [] data = line.split(",");
                if (data.length == 8) {
                    String partCode = data[0].trim();
                    String name = data[1].trim();
                    String brand = data[2].trim();
                    double price = Double.parseDouble(data[3].trim());
                    int quantity = Integer.parseInt(data[4].trim());
                    String category = data[5].trim();
                    String dateAdded = data[6].trim();
                    String imageFile = data[7].trim();

                    parts.add(new Part(partCode, name, brand, price, quantity, category, dateAdded, imageFile));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    public void addPart (Part part) {
        if (part != null) {
            for (int i = 0; i < parts.size(); i++) {
                if (parts.get(i).getPartCode().equals(part.getPartCode())) {
                    System.out.println("Part with code " + part.getPartCode() + " already exists!");
                    return;
                }
            }
            parts.add(part);
            System.out.println("Part added successfully: " + part.getName());
        }
    }

    public void deletePart (String partCode) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getPartCode().equalsIgnoreCase(partCode)) {
                parts.remove(i);
                System.out.println("Part " + partCode + " removed.");
                return;
            }
        }
        System.out.println("Part " + partCode + " not found.");

    }

    public void updatePart (String partCode, String newName, String newBrand, double newPrice, int newQuantity, String newCategory, String newDateAdded, String newImageFile) {
        for (int i =0; i < parts.size(); i++) {
            Part part = parts.get(i);
            if (part.getPartCode().equalsIgnoreCase(partCode)) {
                part.setName(newName);
                part.setBrand(newBrand);
                part.setPrice(newPrice);
                part.setQuantity(newQuantity);
                part.setCategory(newCategory);
                part.setDateAdded(newDateAdded);
                part.setImageFile(newImageFile);
                return;
            }
        }
        System.out.println("Part " + partCode + " updated successfully.");
        return;
    }

    public void sortParts() {
        int n = parts.size();
        for (int i =0; i < n-1; i++) {
            for (int j =0; j < n-i-1; j++) {
                Part p1 = parts.get(j);
                Part p2 = parts.get(j+1);

                int compCat = p1.getCategory().compareTo(p2.getCategory());
                boolean swap = false;
                if (compCat > 0) {
                    swap = true;
                } else if (compCat == 0) {
                    if (p1.getPartCode().compareTo(p2.getPartCode()) > 0) {
                        swap = true;
                    }
                }
                if (swap) {
                    parts.set(j, p2);
                    parts.set(j + 1, p1);
                }
            }
        }
    }

    public ArrayList<Part> getLowStockParts() {
        ArrayList<Part> lowStock = new ArrayList<Part>();
        int lowStockThreshold = 5;
        for (int i =0; i < parts.size(); i++){
            if (parts.get(i).getQuantity() < lowStockThreshold){
                lowStock.add(parts.get(i));
            }
        }
        return lowStock;
    }
}
