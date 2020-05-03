import lombok.Getter;

import java.sql.*;

public class Database {
    @Getter
    private Connection connection = null;
    @Getter
    private PreparedStatement preparedStatement = null;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            createNewTable();
            this.preparedStatement = this.connection.prepareStatement("INSERT INTO X VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create Sqlite in-memory connection");
        }

    }

    public void createNewTable() throws SQLException {
        Statement st = null ;
        try {
            st = this.connection.createStatement();
            st.executeUpdate("drop table if exists X");
            st.executeUpdate("create table X (A string, B string, C string, D string, E string, F string, G string, H string, I string, J string)");
            System.out.println("Created new table");
        } finally {
            st.close();
        }
    }

    public void newEntry(CsvX csvX) {

        try {
            preparedStatement.setString(1, csvX.getA());
            preparedStatement.setString(2, csvX.getB());
            preparedStatement.setString(3, csvX.getC());
            preparedStatement.setString(4, csvX.getD());
            preparedStatement.setString(5, csvX.getE());
            preparedStatement.setString(6, csvX.getF());
            preparedStatement.setString(7, csvX.getG());
            preparedStatement.setString(8, csvX.getH());
            preparedStatement.setString(9, csvX.getI());
            preparedStatement.setString(10, csvX.getJ());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to add new entry | " + e.getMessage());
        }
    }

    public void printDatabaseContent(){
        ResultSet rs = null;
        Statement st = null;
        try {
            st = this.connection.createStatement();
            rs = st.executeQuery("select * from X");
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
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
