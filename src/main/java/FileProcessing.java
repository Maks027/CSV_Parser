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

    public CsvWrite getCsvWrite(){
        return this.csvWrite;
    }


    public void initCsvWrite(String path){
        try {
            this.csvWrite = new CsvWrite(path);
        } catch (IOException e) {
            System.out.println("Could not create file");
            e.printStackTrace();
        }
    }

    BeanVerifier<CsvX> beanVerifier = csvX -> {
        if (csvX.verifyEmptyFields()){
            System.out.println("One field is empty");
            System.out.println(csvX.getA());
            csvWrite.writeBeanToCsv(csvX);
        }
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
