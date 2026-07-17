package main;

import models.Dealer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DealerManager {

    private ArrayList<Dealer> dealers = new ArrayList<>();

    public void loadDealers () {

        String fileName = "dealers_clean.txt";
        dealers.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine())!= null) {
                if (line.trim().isEmpty()){
                    continue;
                }

                String [] data = line.split(",");
                if (data.length == 4){
                    String dealerCode = data [0].trim();
                    String name = data [1].trim();
                    String phone = data [2].trim();
                    String location = data [3].trim();

                    dealers.add(new Dealer(dealerCode,name, phone, location));
                }

            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }

    }
}
