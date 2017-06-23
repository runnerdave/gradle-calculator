package net.runnerdave;

import org.apache.commons.cli.*;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by David A. Jiménez (e67997) on 23/06/2017.
 */
public class Calculator {
    private static final Logger log = Logger.getLogger(Calculator.class.getName());

    public static void main(String[] args) {
        if (args.length > 0) {
            generateCommandLine(generateOptions(), args);
        }
    }

    /**
     * "Parsing" stage of command-line processing demonstrated with
     * Apache Commons CLI.
     *
     * @param options              Options from "definition" stage.
     * @param commandLineArguments Command-line arguments provided to application.
     * @return Instance of CommandLine as parsed from the provided Options and
     * command line arguments; may be {@code null} if there is an exception
     * encountered while attempting to parse the command line options.
     */
    private static CommandLine generateCommandLine(final Options options, final String[] commandLineArguments) {
        final CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = cmdLineParser.parse(options, commandLineArguments);
            if (commandLine.hasOption("h")) {
                printHelp(generateOptions(), System.out);
            } else if(commandLine.hasOption("u")) {
                printUsage(generateOptions(), System.out);
            } else {
                if (commandLine.hasOption("v1") && commandLine.hasOption("v2") && commandLine.hasOption("op")) {
                    Operation op = Operation.parseOperation(commandLine.getOptionValue("op"));
                    Integer value1 = Integer.valueOf(commandLine.getOptionValue("v1"));
                    Integer value2 = Integer.valueOf(commandLine.getOptionValue("v2"));
                    System.out.println(op);
                    System.out.println(op.calculate().apply(value1, value2));
                }
            }


        } catch (ParseException | RuntimeException parseException) {
            String message = parseException.getMessage();
            if(parseException instanceof NullPointerException) {
                message = "Invalid operation.";
            }
            log.severe("ERROR: Unable to parse command-line arguments "
                    + Arrays.toString(commandLineArguments) + " due to: "
                    + message);
        }
        return commandLine;
    }

    /**
     * "Definition" stage of command-line parsing with Apache Commons CLI.
     *
     * @return Definition of command-line options.
     */
    private static Options generateOptions() {
        final Option operation = Option.builder("op")
                .required(false)
                .longOpt("--operation")
                .hasArg()
                .desc("Operation to be performed.")
                .build();

        final Option value1 = Option.builder("v1")
                .required(false)
                .longOpt("--value1")
                .hasArg()
                .desc("1st Operand to calculate.")
                .build();

        final Option value2 = Option.builder("v2")
                .required(false)
                .longOpt("--value2")
                .hasArg()
                .desc("2nd Operand to calculate.")
                .build();

        final Option helpOption = Option.builder("h")
                .required(false)
                .longOpt("--help")
                .hasArg(false)
                .desc("help!!.")
                .build();

        final Option usageOption = Option.builder("u")
                .required(false)
                .longOpt("--usage")
                .hasArg(false)
                .desc("usage.")
                .build();

        final Options options = new Options();
        options.addOption(operation);
        options.addOption(helpOption);
        options.addOption(usageOption);
        options.addOption(value1);
        options.addOption(value2);
        return options;
    }

    /**
     * Generate help information with Apache Commons CLI.
     *
     * @param options Instance of Options to be used to prepare
     *                <p>
     *                help formatter.
     * @param out
     * @return HelpFormatter instance that can be used to print
     * <p>
     * help information.
     */
    private static void printHelp(final Options options, final PrintStream out) {
        final HelpFormatter formatter = new HelpFormatter();
        final String syntax = "Main";
        final String usageHeader = "Example of Using Calculator: -u [value1] -op [ADD|SUBTRACT|DIVIDE|MULTIPLY] -t [value2]";
        final String usageFooter = "See code for further details.";
        out.println("\n====");
        out.println("HELP");
        out.println("====");
        formatter.printHelp(syntax, usageHeader, options, usageFooter);
    }

    private static void printUsage(final Options options, final PrintStream out)
    {
        final HelpFormatter formatter = new HelpFormatter();
        final String syntax = "Main";
        out.println("\n=====");
        out.println("USAGE");
        out.println("=====");
        final PrintWriter pw  = new PrintWriter(out);
        formatter.printUsage(pw, 80, syntax, options);
        pw.flush();
    }
}
