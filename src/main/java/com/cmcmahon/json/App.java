package com.cmcmahon.json;

import com.cmcmahon.json.output.CSVOutput;
import com.cmcmahon.json.parser.CSVParser;
import com.cmcmahon.json.parser.Parser;
import com.cmcmahon.json.util.CommandLineUtil;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
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

        if (hasOption("i") && hasOption("o") && hasOption("c")) {
            File folder = new File(cmd.getOptionValue("i"));
            File csv = new File(cmd.getOptionValue("c"));
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
            fileArrayList.parallelStream().forEach(f -> {
                try {
                    processFile(f, outputDir, csv);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static void processFile(File inputFile, String outputDir, File csvFile) throws FileNotFoundException {
        Parser parser = new Parser();
        CSVParser csvParser = new CSVParser();
        CSVOutput csvOutput = new CSVOutput();

        ArrayList<ArrayList<TreeMap<String,Object>>> parsedFilesList = new ArrayList<>();
        ArrayList<TreeMap<String, Object>> objectMap = parser.parseJsonFile(inputFile);
        ArrayList<TreeMap<String, Object>> csvMap = csvParser.parseCsvFile(csvFile);

        parsedFilesList.add(objectMap);
        parsedFilesList.add(csvMap);


        String name = inputFile.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }

        String outputFile = outputDir + name + ".csv";

        try {
            csvOutput.outputCsv(parsedFilesList,outputFile);
        } catch (IOException e) {
           csvOutput.close();
        }
    }
}
