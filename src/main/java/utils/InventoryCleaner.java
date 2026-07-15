package models;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;


public class InventoryCleaner {

    private static final DateTimeFormatter[] dateFormatters = {
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd/MM/yyyy").toFormatter(),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("yyyy-MM-dd").toFormatter(),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MM-yyyy").toFormatter(),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("yyyy/MM/dd").toFormatter(),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MMM dd, yyyy").toFormatter(Locale.ENGLISH),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH),
            new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MMM dd yyyy").toFormatter(Locale.ENGLISH),
    };

    private static final DateTimeFormatter targetFormatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

    private static String normalizeDate(String rawDate){
        if (rawDate == null || rawDate.trim().isEmpty() || rawDate.startsWith("No")){
            return rawDate;
        }
        String cleanedDate = rawDate.trim();

        for (DateTimeFormatter formatter : dateFormatters){
            try {
                LocalDate date = LocalDate.parse(cleanedDate, formatter);
                return date.format(targetFormatters);
            }catch (DateTimeException ignored){

            }
        }
        return rawDate;
    }

    public static void main (String[] args){
        String file = "inventory_legacy.txt";
        String outFile = "inventory_clean.txt";


        try (FileReader filereader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(filereader);
             FileWriter fileWriter = new FileWriter(outFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)){

            String line;

            while ((line = bufferedReader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }

                line = line.replaceAll("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(\\d{1,2}),\\s+(\\d{4})", "$1 $2 $3");

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

                if (data != null && data.length>= 7){
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
                    String rawCategory = getField(data[5],"Category");
                    String category = rawCategory.substring(0,1).toUpperCase() + rawCategory.substring(1).toLowerCase();
                    String dateAdded = normalizeDate(getField(data[6], "Date added"));
                    String imageFile = data.length > 7 ? getField(data[7], "Image file") : "No Image";
                    Part part = new Part(partCode, name, brand, priceValue, quantity, category, dateAdded, imageFile);
                    String cleanRow = part.getPartCode() + "," + part.getName() + "," + part.getBrand() + "," + part.getPrice() + "," + part.getQuantity() + "," + part.getCategory() + "," + part.getDateAdded() + "," + part.getImageFile();

                    printWriter.println(cleanRow);

                }
            }
            System.out.println("\n All parts written to " + outFile);
        } catch (IOException e){
            System.out.println("An error occurred while reading the file " + file);
        }


    }



}
