package main;

import models.Dealer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

    private ArrayList<Dealer> randomDealers = new ArrayList<Dealer>();

    public String selectRandomDealers() {
        randomDealers.clear();

        if (dealers.size() < 4) {
            return "Error: Not enough dealers in the list to pick 4 unique ones.";
        }

        Random rand = new Random();
        ArrayList<Integer> pickedIndexes = new ArrayList<Integer>();

        while (pickedIndexes.size() < 4) {
            int randomIndex = rand.nextInt(dealers.size());

            boolean alreadyPicked = false;
            for (int i = 0; i < pickedIndexes.size(); i++) {
                if (pickedIndexes.get(i) == randomIndex) {
                    alreadyPicked = true;
                    break;
                }
            }
            if (!alreadyPicked) {
                pickedIndexes.add(randomIndex);
                randomDealers.add(dealers.get(randomIndex));
            }
        }
        return "4 unique dealers randomly selected Successfully";
    }

    public void sortSelectedDealers () {
        int n = randomDealers.size();
        for (int i = 0; i < n -1; i++) {
            for (int j = 0; j < n - i -1; j++) {
                Dealer d1 = randomDealers.get(j);
                Dealer d2 = randomDealers.get(j+1);

                if (d1.getLocation().compareTo(d2.getLocation()) > 0) {
                    randomDealers.set(j, d2);
                    randomDealers.set(j +1, d1);
                }
            }
        }
        System.out.println("Selected dealers sorted by location.");
    }

    public ArrayList<Dealer> getSelectedDealers () {
        return randomDealers;
    }

    public ArrayList<Dealer> getDealers() {
        return dealers;
    }
}
