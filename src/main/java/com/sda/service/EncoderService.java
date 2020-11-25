package com.sda.service;

import java.io.IOException;

public interface EncoderService {
    String encode(String textToConvert, int randomStringsLength) throws IOException;
    String convertCodeToSpaghetti(String textToConvert);
}
