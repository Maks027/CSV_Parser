import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cli {
    private Path globalPath;

    public void runCli(String[] args){
        CommandLine commandLine = parseArg(args);


        if (commandLine.hasOption("filename")){
            System.out.println(commandLine.getOptionValue("filename"));
            String fileName = commandLine.getOptionValue("filename");

            globalPath = Paths.get(fileName).getParent();
            FileProcessing file = new FileProcessing(globalPath.toString());

            try {
                System.out.println("File parsing started");
                file.csvBeanToDb(file.xCsvToBean(file.openFile(fileName)));
                file.getCsvWrite().closeWriter();
                file.printLog();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not open the file");
            }

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
