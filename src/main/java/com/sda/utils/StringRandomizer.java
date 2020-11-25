package com.sda.utils;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class StringRandomizer {
    List<String> randomizedStrings = new ArrayList<String>();

    public String generateString(int lengthOfTheString) {
        // all we need is that 1st symbol must be a letter
        Random random = new Random();
        // that's why we're grab it from ASCII
        int first = random.nextInt(26) + 65;
        // and convert it to char
        char firstChar = (char) first;
        // First letter need to be lowercase, because of .sp compiler, so we will fix this NOW)
        String generatedString = String.valueOf(firstChar).toLowerCase();
        // we're going to prepare a String, from which we will get a randomized suffix
        String alpaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(lengthOfTheString); // here it is - StringBuilder as incoming hero
        for (int i = 0; i < lengthOfTheString-1; i++) {
            // so basically we are generating a random number between 0 and length of String containing letters and numbers
            int index = (int) (alpaNumericString.length() * Math.random());
            // and just append randomized character using StringBuilder
            stringBuilder.append(alpaNumericString
                    .charAt(index));
        }
        // and connect our first lowercase letter with suffix
        generatedString = generatedString + stringBuilder.toString();
        // stop - we need to make sure, that our generated String is NOT being used already (~0.00001%, but still)
        if (randomizedStrings.contains(generatedString)) {
            generateString(lengthOfTheString);
        }
        randomizedStrings.add(generatedString);
        return generatedString;
    }
}
