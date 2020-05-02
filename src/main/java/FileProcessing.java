import com.opencsv.CSVReader;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvValidationException;
import entities.EntityX;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileProcessing {
    private CsvWrite csvWrite;
    private int totalRecords = 0;
    private int failedRecords = 0;
    private int successfulRecords = 0;

    Logs logs;

    public FileProcessing(String globalPath) {
       this.logs = new Logs(globalPath + "parse.log");

        try {
            this.csvWrite = new CsvWrite(globalPath + "bad-data.csv");
        } catch (IOException e) {
            System.out.println("Could not create file");
            e.printStackTrace();
        }
    }

    public void printLog(){
        System.out.println("File parsing finished successfully:");
        System.out.println(this.totalRecords + " records received");
        System.out.println(this.successfulRecords + " records successful");
        System.out.println(this.failedRecords + " failed records");

        logs.logToFile("File parsing finished successfully:");
        logs.logToFile(this.totalRecords + " records received");
        logs.logToFile(this.successfulRecords + " records successful");
        logs.logToFile(this.failedRecords + " failed records");
    }

    public CsvWrite getCsvWrite(){
        return this.csvWrite;
    }


    BeanVerifier<CsvX> beanVerifier = csvX -> {
        if (csvX.verifyEmptyFields()){
            csvWrite.writeBeanToCsv(csvX);
            failedRecords++;
        } else {
            successfulRecords++;
        }
        totalRecords++;
        return true;
    };

    public Reader openFile(String fileName) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        return reader;
    }

    public List<String[]> readCsvLine(Reader reader) throws IOException, CsvValidationException {
        List<String[]> list = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        while ((line = csvReader.readNext()) != null){
            list.add(line);
        }
        return list;
    }


    public CsvToBean<CsvX> xCsvToBean(Reader reader){

        CsvToBean<CsvX> csvToBean = new CsvToBeanBuilder(reader)
                .withType(CsvX.class)
                .withVerifier(beanVerifier)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean;
    }

    public void csvBeanToEntity(CsvX csvX, EntityX entityX){
        entityX.setA(csvX.getA());
        entityX.setB(csvX.getB());
        entityX.setC(csvX.getC());
        entityX.setD(csvX.getD());
        entityX.setE(csvX.getE().getBytes());
        entityX.setF(csvX.getF());
        entityX.setG(csvX.getG());
        entityX.setH(Boolean.valueOf(csvX.getH()));
        entityX.setI(Boolean.valueOf(csvX.getI()));
        entityX.setJ(csvX.getJ());
    }

    public void csvBeanToDb(CsvToBean<CsvX> csvToBean){
        DbSession db = new DbSession();
        EntityX entityX = new EntityX();
        csvToBean.forEach(v -> {

            csvBeanToEntity(v, entityX);
            db.objToDb(entityX);
        });
    }

}
