import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logs {
    private Logger logger;
    private FileHandler fileHandler;

    public Logs(String path) {
        this.logger = Logger.getLogger("ParsingLog");

        try {
            fileHandler = new FileHandler(path);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logToFile(String info){
        this.logger.info(info);
    }
}
