package com.cmcmahon.json;

import com.cmcmahon.json.output.CSVOutput;
import com.cmcmahon.json.parser.Parser;
import com.cmcmahon.json.util.CommandLineUtil;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;


public class App extends CommandLineUtil{
    public static void main( String[] args ){
        CommandLine cmd = null;
        try {
            cmd = parseCommandLine(args,App.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hasOption("i") && hasOption("o")) {
            File folder = new File(cmd.getOptionValue("i"));
            String outputDir = cmd.getOptionValue("o");
            //We could filter this file list on ONLY .json files
            FilenameFilter jsonFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String lowercaseName = name.toLowerCase();
                    if (lowercaseName.endsWith(".json")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            File[] fileList = folder.listFiles(jsonFilter);
            ArrayList<File> fileArrayList = new ArrayList<>(Arrays.asList(fileList));
            fileArrayList.parallelStream().forEach(f -> processFile(f,outputDir));
        }

    }

    public static void processFile(File inputFile, String outputDir) {
        Parser parser = new Parser();
        CSVOutput csvOutput = new CSVOutput();
        ArrayList<TreeMap<String, Object>> objectMap = parser.parseJsonFile(inputFile);

        String name = inputFile.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }

        String outputFile = outputDir + name + ".csv";

        try {
            csvOutput.outputCsv(objectMap,outputFile);
        } catch (IOException e) {
           csvOutput.close();
        }
    }
}
