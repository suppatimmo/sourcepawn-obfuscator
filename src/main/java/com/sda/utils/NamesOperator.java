package com.sda.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NamesOperator {
    CsvReader csvReader = new CsvReader();
    List<String> variablesAndFunctionsNames = new ArrayList<>();
    List<String> untouchableNames = csvReader.getUntouchableNamesList();
    StringRandomizer stringRandomizer = new StringRandomizer();
    String codeToEncode;
    String encodedCode;
    int generatedStringLength;

    public String getCode() {
        return encodedCode;
    }

    public void setCode(String codeToEncode) {
        this.codeToEncode = codeToEncode;
    }

    public List<String> findAndChangeAllVariablesAndFunctionNames(String textToEncode, int randomStringLength) throws IOException {
        this.codeToEncode = this.encodedCode = textToEncode;
        this.variablesAndFunctionsNames.clear();
        this.generatedStringLength = randomStringLength;

        while (isMoreVariablesToTranslate(this.codeToEncode)) {
            for (String variablesAndFunctionsName : this.variablesAndFunctionsNames) {
                // we need to replace all occurrences of variables/function names in given String, because we don't want to
                // iterate by the same all the time.. moreover, we'll show only encoded code, so replace values now
                this.codeToEncode = this.codeToEncode.replaceAll("\\b" + variablesAndFunctionsName + "\\b",
                        stringRandomizer.generateString(generatedStringLength));

                this.encodedCode = this.encodedCode.replaceAll("\\b" + variablesAndFunctionsName + "\\b",
                        stringRandomizer.generateString(generatedStringLength));
                this.variablesAndFunctionsNames.remove(variablesAndFunctionsName); // some optimization here.. i think
                break;
            }
        }
        return variablesAndFunctionsNames;
    }

    private boolean isStringArrayContainingName(String[] line) {
        // if length of given array is greater or equals 2, then we have to deal with name inside it
        return line.length >= 2;
    }

    private boolean isLineContainingOneName(String line) {
        String separatedLine[] = line.split("[;\\r]");

        // if line don't have "," inside - its simply one name of variable here
        if (!separatedLine[0].contains(","))
            return true;

        // so if we have "," then check if "=" appears..
        if (separatedLine[0].contains("=")) {
            // if yes, then there's NO WAY to get ',' after '=' with one variable declaration/initialization
            return separatedLine[0].indexOf(',') > separatedLine[0].indexOf('=');
        }

        // then finally, if there's ";", we must have to deal with complex line of code
        if (!separatedLine[0].contains(";"))
            return true;

        // otherwise.. we have ',', we don't have '=', so its multiple declaration/initialization
        return false;
    }

    /*
    fuck, try this:
    bool SavePlayerSigil(int client, int sigilID, int sigilPower = 0, bool sigilStatus = false, bool hasSigil = true) {
     */
    private void renameAndReplaceTypeOrClass(String line) {
        for (int i = 0; i < csvReader.getTypesList().size(); i++) {
            if (line.contains(csvReader.getTypesList().get(i))) {

            }
        }
    }

    private List<String> getAllNamesFromComplexLine(String complexLine) {
        List<String> allNames = new ArrayList<>();
        String[] splittedLine;
        // split for every possible name inside complex line
        splittedLine = complexLine.split("[;{]");
        splittedLine = splittedLine[0].split("[,]");

        for (int i = 0; i < splittedLine.length; i++) {
            splittedLine[i] = splittedLine[i].trim();
            if (splittedLine[i].contains("[")) {
                splittedLine[i] = splittedLine[i].substring(0, splittedLine[i].indexOf("["));
                allNames.add(splittedLine[i]);
            } else if (splittedLine[i].contains("=")) {
                splittedLine[i] = splittedLine[i].substring(0, splittedLine[i].indexOf("="));
            }
        }

        for (String s : splittedLine) {
            if (!s.isEmpty())
                allNames.add(s);
        }

        return allNames;
    }


    /* TESTING GETTING FUNCTION NAMES
//    public boolean isFunctionName(String line) {
//        // if there is not any (, then it can't be a function name..
//        if (!line.contains("(")) {
//            return false;
//        }
//
//        // for example : public void Example(int something, char[] something2) { };
//        if (line.indexOf('(') < line.indexOf(',') || line.indexOf(',') == -1) {
//            return true;
//        }
//        return false;
//    }
//    private boolean searchForArraysNames(String codeToEncode, String pattern) {
//        String[] splittedByPattern = codeToEncode.split(pattern);
//        if (splittedByPattern.length > 1) {
//            System.out.println(splittedByPattern[1].trim()); // got it?!
//        }
//        return false;
//    }
 */
    private boolean searchForNextName(String codeToEncode, String pattern) {
        // split codeToEncode by given pattern, as a way of searching for potential candidate to be a name of variable/function
        String[] splittedByType = codeToEncode.split(pattern);

        if (isStringArrayContainingName(splittedByType)) {
            if (isLineContainingOneName(splittedByType[1])) {
                // thanks to Mike ♥.. I was stuck af splitting by \\[ :D
                String[] splittedByOperator = splittedByType[1].split("[\r\\[,(;= ]");
                // we need to make sure that someone just didn't pasted something wrong to encode..

                splittedByOperator[0] = splittedByOperator[0].replaceAll("[^a-zA-Z0-9_-]", "");
                splittedByOperator[0] = splittedByOperator[0].trim();
                if (isNameAllowedToTranslate(splittedByOperator[0])) {
                    variablesAndFunctionsNames.add(splittedByOperator[0]);
                    this.codeToEncode = this.codeToEncode.replaceFirst(pattern, stringRandomizer.generateString(7) + " ");
                    return true;
                }
            } else {
                List<String> allNamesFromComplexLine = getAllNamesFromComplexLine(splittedByType[1]);
                for (String s : allNamesFromComplexLine) {
                    if (isNameAllowedToTranslate(s)) {
                        variablesAndFunctionsNames.add(s);
                    }
                }
                this.codeToEncode = this.codeToEncode.replaceFirst(pattern, stringRandomizer.generateString(7) + " ");
                return true;
            }
        }
        return false;
    }

    private boolean isMoreVariablesToTranslate(String codeToEncode) throws IOException {
        // iterate all types available in classesAndTypes.csv
        for (int i = 0; i < csvReader.getTypesList().size(); i++) {
            // get the type by iterator
            String pattern = csvReader.getTypesList().get(i);
            if (searchForNextName(codeToEncode, pattern + " ")) {
                return true;
            }
//            else {
//                searchForArraysNames(codeToEncode, pattern);
//            }
        }
        return false;
    }

    private boolean isNameAllowedToTranslate(String name) {
        return !this.untouchableNames.contains(name) && !name.isEmpty();
    }
}
