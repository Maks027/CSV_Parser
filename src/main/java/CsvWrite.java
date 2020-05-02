import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class CsvWrite {

    Writer writer;
    StatefulBeanToCsv<CsvX> beanToCsv;

    public CsvWrite(String path) throws IOException {
        this.writer = new FileWriter(path);
        beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withApplyQuotesToAll(false)
                .build();
    }

    public void writeBeanToCsv(CsvX csvX){
        try {
            this.beanToCsv.write(csvX);
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter(){
        try {
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
