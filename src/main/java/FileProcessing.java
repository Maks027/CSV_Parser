import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

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
