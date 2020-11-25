package com.sda.service;

import com.sda.utils.LineOperator;
import com.sda.utils.NamesOperator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EncoderServiceImpl implements EncoderService {

    NamesOperator namesOperator = new NamesOperator();
    LineOperator lineOperator = new LineOperator();

    @Override
    public String encode(String textToConvert, int randomStringsLength) throws IOException {
        String encodedCode = textToConvert;
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

