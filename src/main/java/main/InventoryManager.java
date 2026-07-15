package main;

import models.Part;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

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



}
