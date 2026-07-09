package com.example._609835;
import java.io.*;


public class InventoryCleaner {

    private static String getField(String value, String fieldName){
        if (value.trim().isEmpty()){
            return "No " + fieldName;
        }
        return value.trim();
    }

    private static int countChar(String str, char ch){
        int count = 0;
        for (int i =0; i < str.length(); i++){
            if (str.charAt(i) == ch){
                count++;
            }
        }
        return count;
    }
    public static void main (String[] args){
        String file = "inventory_legacy.txt";


        try (FileReader filereader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(filereader)) {

            String line;

            while ((line = bufferedReader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }

                int commaCount = countChar(line, ',');
                int pipeCount = countChar(line, '|');
                int semiCount = countChar(line, ';');

                String [] data = null;

                if (pipeCount > semiCount && pipeCount > commaCount){
                    data = line.split("\\|");
                } else if (semiCount > pipeCount && semiCount > commaCount){
                    data = line.split(";");
                } else if (commaCount > pipeCount && commaCount > semiCount){
                    data = line.split(",");
                } else {
                    System.out.println("Skipped delimiter: " + line.trim());
                    continue;
                }

                if (data != null && data.length> 1){
                    String partCode = getField(data[0], "Part code");
                    String name = getField(data[1], "Name");
                    String brand = getField(data[2], "Brand");
                    String price = data[3];
                    String cleanPrice = price.replace("Rs.","")
                            .replace("rs", "")
                            .replace("Rs","")
                            .trim();
                    double priceValue = 0.0;
                    if (!cleanPrice.isEmpty()){
                        priceValue = Double.parseDouble(cleanPrice);
                    }
                    String quantityStr = getField(data[4], "Quantity");
                    int quantity = 0;
                    if (!quantityStr.startsWith("No")){
                        quantity = Integer.parseInt(quantityStr);
                    }
                    String category = getField(data[5],"Category");
                    String dateAdded = getField(data[6], "Date added");
                    String imageFile = getField(data[7], "Image file");
                    Part part = new Part(partCode, name, brand, priceValue, quantity, category, dateAdded, imageFile);
                    System.out.println("Parsed: " + part.getPartCode() + " - " + part.getName());

                }



            }
        } catch (IOException e){
            System.out.println("An error occurred while reading the file " + file);
        }

    }


}
