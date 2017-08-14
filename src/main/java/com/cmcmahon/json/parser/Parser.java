package com.cmcmahon.json.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Deslyxia on 8/8/17.
 */
public class Parser {

    public ArrayList<TreeMap<String, Object>> parseJsonFile (File jsonFile) {
        ArrayList<TreeMap<String, Object>> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.readValue(jsonFile, new TypeReference<ArrayList<TreeMap<String, Object>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}