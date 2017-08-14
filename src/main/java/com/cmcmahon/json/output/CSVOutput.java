package com.cmcmahon.json.output;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by chrismcmahon on 8/14/17.
 *
 */
public class CSVOutput {
    private TreeMap <String,String> keyMap = new TreeMap<>();
    private File fout;
    private FileOutputStream fos;
    private BufferedWriter bw;

    public void outputCsv (ArrayList<TreeMap<String, Object>> objectMap, String outputFile) throws IOException {
        fout = new File(outputFile);
        fos = new FileOutputStream(fout);
        bw = new BufferedWriter(new OutputStreamWriter(fos));


        //Build Keymap with no duplicates
        for (TreeMap<String,Object> obj : objectMap) {
            for (String key : obj.keySet()) {
                keyMap.put(key,null);
            }
        }

        //Check that we actually parsed something
        if (keyMap.size() > 0) {
            //Output first line of CSV which is the field names
            bw.write(getHeadings());
            bw.newLine();

            //Output the data from each json object
            for (TreeMap<String, Object> obj : objectMap) {
                for (String key : keyMap.keySet()) {
                    if (obj.keySet().contains(key)) {
                        keyMap.put(key, obj.get(key).toString());
                    } else {
                        keyMap.put(key, null);
                    }
                }
                bw.write(getOutputLine());
                bw.newLine();
            }
        }
        close();
    }

    private String  getOutputLine() {
        StringBuilder sb = new StringBuilder();
        for (String key : keyMap.keySet()) {

            sb.append(keyMap.get(key));
            if (!key.equals(keyMap.lastEntry().getKey())) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String getHeadings() {
        StringBuilder sb = new StringBuilder();
        for (String key : keyMap.keySet()) {
            sb.append(key);
            if (!key.equals(keyMap.lastEntry().getKey())) {
                sb.append(",");
            } else {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void close(){
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
