package com.example._609835;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class InventoryCleaner {

    private static String getField(String value, String fieldName){
        if (value.trim().isEmpty()){
            return "No " + fieldName;
        }
        return value.trim();
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
                String [] data = null;

                if(line.contains("|")){
                    data = line.split("\\|");
                } else if (line.contains(";")) {
                    data = line.split(";");
                }else if (line.contains(",")){
                    data = line.split(",");
                } else {
                    continue;
                }

                if (data != null && data.length> 1){
                    String price = data[3];

                    String cleanPrice = price.replace("Rs.","")
                            .replace("rs", "")
                            .replace("Rs","")
                            .trim();
                }

            }
        } catch (IOException e){
            System.out.println("An error occurred while reading the file " + file);
        }

    }


}
