package com.example._609835;

import jdk.internal.org.jline.reader.Buffer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryCleaner {
    public static String[] splitLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new String[0];
            }

            String placeHolder = "##COMMA##";
            String dataPattern = "\b(?:[A-Za-z]{3,}\\s+\\d{1,2}|\\d{1,2}\\s+[A-Za-z]{3,}),\\s+\\d{4}";

            Pattern pattern = Pattern.compile(dataPattern);
            Matcher matcher = pattern.matcher(line);

            boolean containsComma = false;

            StringBuilder stringBuilder = new StringBuilder();
            int lastEnd = 0;

            while (dataPattern.find()) {
                containsComma = true;
                stringBuilder.append(Line, lastEnd, dataPattern.start());

                String matchedDate = matcher.group();
                String protecteDate = matchedDate.replace(",", placeHolder);
                stringBuilder.append(protecteDate);

                lastEnd = matcher.end();

            }
            stringBuilder.append(line, lastEnd, line.length());

            if (containsComma){
                line = stringBuilder.toString();
            }

            String[] splitValues = line.split("\\s*[,|;]\\s*",-1);

            if (containsComma){
                for (int i = 0; i < splitValues.length; i++){
                    if (splitValues[i].contains(placeHolder)){
                        splitValues[i] = splitValues[i].replace(placeHolder, ",");
                    }
                }
            }

            if (splitValues.length > 8 && splitValues[splitValues.length - 1].trim().isEmpty()){
                splitValues = Arrays.copyOf(splitValues, 8);
            }

            return splitValues;

    }

    public static void main(String[] args){
        String file = "inventory_legacy.txt";
        int lineCount = 1;
        boolean validData = true;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = bufferedReader.readLine()) != null){
                if (line.trim().isEmpty()) continue;

                String[] fields = splitLine(line);

                if (fields.length != 8){
                    System.err.printf("ERROR: Line %02d failed! Expected 8 but got %d.%n", lineCount, fields.length);
                    System.err.println("Problematic data: " + Arrays.toString(fields));
                    validData = false;
                }

                lineCount++;
            }

            if (validData){
                System.out.println("All lines processed successfully with exactly 8 fields.");
            }
        } catch (IOException e){
            System.err.println("CRITICAL ERROR: Could not find or read '" + file + "'.");
            e.printStackTrace();
        }
    }


}
