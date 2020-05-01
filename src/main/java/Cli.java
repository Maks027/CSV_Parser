import org.apache.commons.cli.*;

public class Cli {

    public void runCli(String[] args){
        CommandLine commandLine = parseArg(args);

        if (commandLine.hasOption("filename")){
            System.out.println(commandLine.getOptionValue("filename"));
            String fileName = commandLine.getOptionValue("filename");

        } else {
          System.out.println("Invalid argument");
        }

    }

    private CommandLine parseArg(String[] args){
        Options options = getOptions();

        CommandLine commandLine = null;
        CommandLineParser cliParser = new DefaultParser();

        try {
            commandLine = cliParser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Failed to parse command line arguments");
            e.printStackTrace();
            System.exit(1);
        }
        return commandLine;
    }

    private Options getOptions() {

        Options options = new Options();

        options.addOption("f", "filename", true, "file name to load data from");
        return options;
    }
}
