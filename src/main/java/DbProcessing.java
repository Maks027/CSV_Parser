import org.sqlite.jdbc4.JDBC4Connection;

import java.sql.*;

public class DbProcessing {

    private Connection connection = null;
    private Statement statement = null;

    public DbProcessing() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createSqliteConnection(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create Sqlite in memory connection");
        }
    }

    public Statement getStatement(){
        return this.statement;
    }

    public void closeSqliteConnection(){
        try {
            if(this.connection != null)
                this.connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Connection can't be closed");
        }
    }

    public void createNewTable(Statement statement){
        try {
            statement.executeUpdate("drop table if exists X");
            statement.executeUpdate("create table X (A string, B string, C string, D string, E string, F string, G string, H string, I string, J string)");
            System.out.println("Created new table");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create new table");
        }

    }

    public void newEntry(CsvX csvX, Statement statement) {

        try {
            statement.executeUpdate("insert into X values("+
                    "'" + csvX.getA() + "'," +
                    "'" + csvX.getB() + "'," +
                    "'" + csvX.getC() + "'," +
                    "'" + csvX.getD() + "'," +
                    "'" + csvX.getE() + "'," +
                    "'" + csvX.getF() + "'," +
                    "'" + csvX.getG() + "'," +
                    "'" + csvX.getH() + "'," +
                    "'" + csvX.getI() + "'," +
                    "'" + csvX.getJ() + "')");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add new entry");
        }
    }

    public void printDatabaseContent(){

        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("select * from X");
            while(rs.next())
            {
                System.out.println("A = " + rs.getString("A") +
                        " ; B = " + rs.getString("B") +
                        " ; C = " + rs.getString("C") +
                        " ; D = " + rs.getString("D") +
                        " ; E = " + rs.getString("E") +
                        " ; F = " + rs.getString("F") +
                        " ; G = " + rs.getString("G") +
                        " ; H = " + rs.getString("H") +
                        " ; I = " + rs.getString("I") +
                        " ; J = " + rs.getString("J"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
