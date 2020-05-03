import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class FileProcessing {

    private int totalRecords = 0;
    private int failedRecords = 0;
    private int successfulRecords = 0;
    Logs logs;

    public void printLog(String path){
        System.out.println("File parsing finished successfully:");
        System.out.println(this.totalRecords + " records received");
        System.out.println(this.successfulRecords + " records successful");
        System.out.println(this.failedRecords + " failed records");

        this.logs = new Logs(path + "/parse.log");

        logs.logToFile("File parsing finished successfully:");
        logs.logToFile(this.totalRecords + " records received");
        logs.logToFile(this.successfulRecords + " records successful");
        logs.logToFile(this.failedRecords + " failed records");
    }

    public CSVWriter createCsvFile(String path){
        CSVWriter csvWriter = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
        try {
            csvWriter = new CSVWriter(new FileWriter(path + "/bad-data-" + sdf.format(timestamp) +".csv"));
        } catch (IOException e) {
            System.out.println("Could not create file");
            e.printStackTrace();
        }
        return csvWriter;
    }



    public Reader openFile(String fileName) {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    public CsvToBean<CsvX> xCsvToBean(Reader reader){
        CsvToBean<CsvX> csvToBean = new CsvToBeanBuilder(reader)
                .withType(CsvX.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withThrowExceptions(false)
                .build();
        return csvToBean;
    }


    public Database csvToDb(String path)  {
        CsvToBean<CsvX> csvToBean = xCsvToBean(openFile(path));
        Database db = new Database();
        csvToBean.forEach(csvX -> {
            totalRecords++;
            db.newEntry(csvX);
        });

        System.out.println("Database successfully created");

        List<CsvException> exceptions = csvToBean.getCapturedExceptions();

        if (!exceptions.isEmpty()){
            CSVWriter csvWriter = createCsvFile(Paths.get(path).getParent().toString());
            exceptions.forEach(e -> {
                csvWriter.writeNext(e.getLine());
                failedRecords++;
            });
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printLog(Paths.get(path).getParent().toString());
        return db;
    }
}
