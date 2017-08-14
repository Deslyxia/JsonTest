package com.cmcmahon.json.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Deslyxia on 8/8/17.
 */
public class Parser {

    public ArrayList<HashMap<String, Object>> parseJsonFile (File jsonFile) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.readValue(jsonFile, new TypeReference<ArrayList<HashMap<String, Object>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
