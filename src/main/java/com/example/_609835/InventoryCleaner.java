package com.example._609835;

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

    }
}
