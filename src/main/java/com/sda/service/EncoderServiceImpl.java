package com.sda.service;

import com.sda.utils.NameSearcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EncoderServiceImpl implements EncoderService {

    NameSearcher nameSearcher = new NameSearcher();

    @Override
    public String encode(String textToConvert) throws IOException {
        String encodedCode = textToConvert;
        List<String> allNames = nameSearcher.getVariablesAndFunctionsNames(textToConvert);
        return nameSearcher.getCode();
//        List typesList = csvReader.getTypesList();
//        List<String> variableOrFunctionNames = sourcePawnEncoder.searchForAllVariableOrFunctionNames(textToConvert);
//        encodedCode = sourcePawnEncoder.encodeAllVariableOrFunctionNames(variableOrFunctionNames, encodedCode);
    }
}

