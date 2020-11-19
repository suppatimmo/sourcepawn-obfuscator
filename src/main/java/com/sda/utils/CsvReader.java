package com.sda.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private List<String> typesList = new ArrayList<String>();
    private List<String> untouchableNames = new ArrayList<String>();

    public List<String> getTypesList() {
        if (this.typesList.size() == 0)
            reloadTypesList();
        return this.typesList;
    }

    public List<String> getUntouchableNamesList() {
        if (this.untouchableNames.size() == 0)
            reloadUntouchableNamesList();
        return this.untouchableNames;
    }

    public void reloadTypesList() {
        // getting new list once per boot
        this.typesList.clear();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("classesAndTypes.csv"); // loading from file
        assert inputStream != null;
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                this.typesList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Reported exception in CsvReader, in function 'reloadTypesList()'.");
        }
    }

    public void reloadUntouchableNamesList() {
        // getting new list once per boot
        this.untouchableNames.clear();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("forwards.csv"); // loading from file
        assert inputStream != null;
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                this.untouchableNames.add(line);
            }
        }
        catch(IOException e){
                System.out.println("Reported exception in CsvReader, in function 'reloadUntouchableNamesList()'.");
            }
        }
    }
