package com.cmcmahon.json.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by chrismcmahon on 8/17/17.
 */
public class CSVParser {

    public ArrayList<TreeMap<String,Object>> parseCsvFile (File csvFile) throws FileNotFoundException{
        ArrayList<TreeMap<String,Object>> data = new ArrayList<>();
        FileReader fr = new FileReader(csvFile);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> keyArray = new ArrayList();
        Boolean firstLine = true;
        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                //TODO:  new Treemap
                TreeMap<String, Object> dataLine = new TreeMap<>();

                //TODO: Split String on comma
                String[] dataSplit = line.split(",");

                if (firstLine) {
                    for(String s : dataSplit) {
                        keyArray.add(s);
                    }
                    firstLine = false;
                } else {
                    //TODO: Add data to treemap
                    for (int i = 0; i < dataSplit.length; i++) {
                        dataLine.put(keyArray.get(i),dataSplit[i]);
                    }
                }

                //TODO: Add Map to Arraylist
                data.add(dataLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return data;
    }
}
