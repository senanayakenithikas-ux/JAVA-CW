package models;

import java.io.*;


public class DealerCleaner {

    private static String getField(String value, String fieldName){
        if (value.trim().isEmpty()){
            return "No " + fieldName;
        }
        return value.trim();
    }

    private static int countChar(String str, char ch){
        int count = 0;
        for (int i=0; i< str.length(); i++){
            if(str.charAt(i) == ch){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args){
        String file = "dealers_legacy.txt";
        String outFile = "dealers_clean.txt";

        try (FileReader filereader  = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(filereader);
             FileWriter fileWriter = new FileWriter(outFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)){

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
                    line = line.replace(";","|").replace(",","|");
                    data = line.split("\\|");
                } else if (semiCount > pipeCount && semiCount > commaCount){
                    line = line.replace("|",";").replace(",",";");
                    data = line.split(";");
                } else if (commaCount > pipeCount && commaCount > semiCount){
                    line = line.replace("|",",").replace(";",",");
                    data = line.split(",");
                } else {
                    System.out.println("Skipped delimiter: " + line.trim());
                    continue;
                }

                if (data != null && data.length>=4){
                    String dealerCode = getField(data[0], "dealer code");
                    String name = getField(data[1], "name");
                    String phone = getField(data[2], "phone");
                    String location = getField(data[3], "location");
                    Dealer dealer = new Dealer(dealerCode, name, phone, location);
                    String cleanRow = dealer.getDealerCode() + "," + dealer.getName() + "," + dealer.getPhone() + "," + dealer.getLocation();

                    printWriter.println(cleanRow);
                }
            }
            System.out.println("\n All dealers written to " + outFile);
        } catch (IOException e){
            System.out.println("An error occurred while reading the file " + file);


    }
    }
}
