This is a Java application that consumes a CSV file, parse the data 
and insert to a SQLite In-Memory database.

*Cloning:*

The project was created using IntellijIDEA and can be cloned from this
repository using `git clone https://github.com/Maks027/CSV_Parser.git`.
Note that it should be imported a Maven project.

*Usage:*

The conversion can be done by calling `csvToDb("Absolute path to .csv file")` 
from `FileProcessing` class. The method returns a `Database` object that
 contains an opened in-memory database connection with the created table. 
 The opened connection can be retrieved for future use using `getConnection()` method
 from `Database` class. Once all required transactions are done, the 
 connection should be closed with `closeConnection()` method. 
 
 Every record is verified to contain the right number of data elements to match 
 the columns. The records that do not match the column count are written to the 
 *bad-data-timestamp.csv* file that is created in the same directory 
 as processed file. Additionally, a *parse.log* file is created in 
 the same directory that contains the total number of processed records
 as well as the number of successful and failed records.
 
 *Usage example:*
 ```
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
```   
In the example above, the *sample.csv* file is parsed and the retrieved
data is inserted to an in-memory Sqlite database. The connection to that
database is obtained using `database.getConnection()`. Then, a new 
Statement and a ResultSet are created to execute an SQL query that
selects data from the first two columns A and B from X table. The 
results are printed to the console. 


*The general approach to solve the  problem:*

The .csv file is processed using the open source library *OpenCSV*.
The records are read into Beans whose fields are mapped by name to
the fields of the .csv file. The records are processed one by one.
Once a line is parsed, a new database entry is created. The connection
to Sqlite im-memory database is established using *sqlite-jdbc* driver.
All SQL statements are executed using  *java.sql* library for performance
enhancement. 

All .csv parsing exceptions are collected and the faulty lines are 
written in the end to an *bad-data-timestamp.scv*  file. The total 
number of processed records, successful and failed records is 
counted and written to the log file. 

