package com.sda.service;

import com.sda.utils.LineOperator;
import com.sda.utils.NamesOperator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncoderServiceImpl implements EncoderService {

    NamesOperator namesOperator = new NamesOperator();
    LineOperator lineOperator = new LineOperator();

    @Override
    public String encode(String textToConvert, int randomStringsLength) throws IOException {
//        namesOperator.setCode(textToConvert);
//        List<String> variableNames;
//        List<String> functionNames;
//
//        variableNames = namesOperator.getAllVariableNames;
//        functionNames = namesOperator.getAllFunctionNames;
//
//        variableNames.addAll(functionNames);
//        renameAllNamesToRandomStrings(variableNames);


        // 1.list - get all variable names
        // 2.list - get all functions names
        // 3.concat 2 lists
        // 4.rename all to random strings

        List<String> allNames = namesOperator.findAndChangeAllVariablesAndFunctionNames(textToConvert, randomStringsLength);
        return namesOperator.getCode();
//        List typesList = csvReader.getTypesList();
//        List<String> variableOrFunctionNames = sourcePawnEncoder.searchForAllVariableOrFunctionNames(textToConvert);
//        encodedCode = sourcePawnEncoder.encodeAllVariableOrFunctionNames(variableOrFunctionNames, encodedCode);
    }

    @Override
    public String convertCodeToSpaghetti(String textToConvert) {
        textToConvert = lineOperator.convertToSpaghetti(textToConvert);
        return textToConvert.trim();
    }
}

