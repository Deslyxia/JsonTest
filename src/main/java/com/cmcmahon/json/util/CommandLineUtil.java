package com.cmcmahon.json.util;

import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * Created by chrismcmahon on 8/14/17.
 */
public class CommandLineUtil {
    private static Options options;
    private static CommandLine cmd;


    static {
        options = new Options();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("This help message.")
                .build();
        options.addOption(help);

        Option inputFile = Option.builder("i")
                .longOpt("inputfile")
                .argName("INPUT_FILE_LOCATION")
                .desc("Directory to find input files.")
                .required(true)
                .type(String.class)
                .hasArg()
                .build();
        options.addOption(inputFile);

        Option csvInputFile = Option.builder("c")
                .longOpt("csvFile")
                .argName("INPUT_FILE_LOCATION")
                .desc("Directory to find input files.")
                .required(true)
                .type(String.class)
                .hasArg()
                .build();
        options.addOption(csvInputFile);

        Option outputFile = Option.builder("o")
                .longOpt("outputfile")
                .argName("OUTPUT_FILE_LOCATION")
                .desc("Directory to write output file.")
                .required(true)
                .type(String.class)
                .hasArg()
                .build();
        options.addOption(outputFile);

    }

    protected static void addOption(Option o) {
        options.addOption(o);
    }

    protected static void addOptions(Options os) {
        for (Option o : os.getOptions()) {
            options.addOption(o);
        }
    }

    protected static CommandLine parseCommandLine(String[] args, String className) throws SecurityException, IOException {
        if (cmd != null) {
            return cmd;
        }

        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments." + e);
            helpFormatter.printHelp(className, options);
            System.exit(1);
        }

        if (cmd.hasOption("help")) {
            helpFormatter.printHelp(className, options);
            System.exit(0);
        }

        return cmd;
    }

    protected static boolean hasOption(String option) {
        if (cmd == null) {
            System.out.println("Command line not yet parsed. Call parseCommandLine() first.");
            System.exit(1);
        }

        return cmd.hasOption(option);
    }

}
