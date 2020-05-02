import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.EntityX;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileProcessing {


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
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean;
    }

    public EntityX csvBeanToEntity(CsvX csvX){
        EntityX entityX = new EntityX();
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
        return entityX;
    }

    public void csvBeanToDb(CsvToBean<CsvX> csvToBean){
        DbSession db = new DbSession();

        csvToBean.forEach(v -> {
            db.objToDb(csvBeanToEntity(v));
        });
    }

    public void printCsvBean( CsvToBean<CsvX> csvToBean){
        csvToBean.forEach(v -> {
            System.out.println(v.getA());
            System.out.println(v.getB());
            System.out.println(v.getC());
            System.out.println(v.getD());
        });
    }

    public void printCsvContent(List<String[]> list){
        list.forEach(v -> {
            System.out.println(v[0]);
            System.out.println(v[1]);
            System.out.println(v[2]);
        });
    }


}
