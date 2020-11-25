package com.sda.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LineOperator {
    List<String> linesToSpaghettize = new ArrayList<>();
    List<String> untouchableLines = new ArrayList<>();

    public String convertToSpaghetti(String codeToConvert) {
        linesToSpaghettize.clear();
        untouchableLines.clear();
        // get every new line by splitting codeToConvert by \\r?\\n
        String[] separatedCodeLines = codeToConvert.split("\\r?\\n");

        // add all lines to 2 lists - in linesToSpaghettize we'll keep all lines which will be 'spaghettized' (new word, fantastic!)
        linesToSpaghettize.addAll(Arrays.asList(separatedCodeLines));
        untouchableLines.addAll(Arrays.asList(separatedCodeLines));

        // trim all lines in these 2 lists
        untouchableLines = untouchableLines.stream().map(String::trim).collect(Collectors.toList());
        linesToSpaghettize = linesToSpaghettize.stream().map(String::trim).collect(Collectors.toList());

        // get only lines with '#'.
        // we need to keep them clear, because sourcepawn compiler needs #include, #pragma etc as separated lines.

        untouchableLines = untouchableLines.stream()
                .filter(item -> item.indexOf("#") == 0)
                .collect(Collectors.toList());

        // get only lines without '#' and with any content
        // of course we are going to skip all commented lines too.
        linesToSpaghettize = linesToSpaghettize.stream()
                .filter(item -> !item.isEmpty() && item.indexOf("#") != 0 && item.indexOf("//") != 0)
                .collect(Collectors.toList());
        // there is a limit - we can keep in 1 line only 4095 symbols, so first - check length,
        // if its more than 4095, add \n. Of course we can safely keep untouchableLines at the top of this code.
        codeToConvert = String.join("\n", untouchableLines);
        // here, we need to add \n in the end, to get everything else under untouchable lines
        codeToConvert = codeToConvert + "\n";

        String codeBuffer = codeToConvert;
        String lineToSpaghettize;
        for (int i = 0; i < linesToSpaghettize.size(); i++) {
            lineToSpaghettize = linesToSpaghettize.get(i);
            if (codeBuffer.length() + lineToSpaghettize.length() > 4095) {
                codeToConvert = codeToConvert.concat("\n");
                codeBuffer = "";
            }

            codeToConvert = codeToConvert.concat(lineToSpaghettize);
            codeBuffer = codeBuffer.concat(lineToSpaghettize);
        }
        return codeToConvert;
    }
}
