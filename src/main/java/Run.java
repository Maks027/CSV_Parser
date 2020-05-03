import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Run {
    public static void main(String[] args) throws SQLException {
        FileProcessing fp = new FileProcessing();
        Database database = fp.csvToDb("F:/Desktop/sample.csv");

        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select A, B from X");
        while (resultSet.next()){
            System.out.println("A = " + resultSet.getString("A") + " ; " +
                    "B = " + resultSet.getString("B"));
        }
        statement.close();
        connection.close();
    }
}
