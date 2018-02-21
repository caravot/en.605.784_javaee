package ravotta.carrie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InsertRecords {
    private static String dbURL = "jdbc:derby:JHU;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void main(String[] args) {
        createConnection();
//        buildStudentTable();
//        readCSVFile();
        selectStudents();
        shutdown();
    }

    private static void createConnection() {
        try {
            conn = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static void buildStudentTable() {
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE STUDENT (" +
                    "FIRST_NAME varchar(40)," +
                    "LAST_NAME varchar(40)," +
                    "SSN char(11)," +
                    "EMAIL varchar(40)," +
                    "ADDRESS varchar(40)," +
                    "USERID varchar(8)," +
                    "PASSWORD varchar(8))");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private static void insertStudent(String[] row) {
        String insertQuery = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, SSN, EMAIL, ADDRESS, USERID, PASSWORD) " +
                "values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            for (int i = 1; i <= 7; i++) {
                // prepared statement index starts at 1 not 0
                pstmt.setString(i, row[i - 1]);
            }

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readCSVFile() {
        String csvFile = InsertRecords.class.getClassLoader().getResource("MOCK_DATA.csv").getFile();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                insertStudent(line.split(cvsSplitBy));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void selectStudents() {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet results = stmt.executeQuery("SELECT * FROM STUDENT ORDER BY FIRST_NAME");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();

            for (int i = 1; i <= numberCols; i++) {
                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
            }

            System.out.println("\n-------------------------------------------------");
            System.out.println("\n\t\tGET STUDENTS TRAVERSING FORWARD (USERID DESC");
            System.out.println("\n-------------------------------------------------");

            while (results.next()) {
              String rowResult = "";

                for (int i = 1; i <= 7; i++) {
                    String tmp = results.getString(i);

                    rowResult += tmp + "\t\t";
                }

                System.out.println(rowResult);
            }

            System.out.println("\n-------------------------------------------------");
            System.out.println("\n\t\tGET STUDENTS TRAVERSING BACKWARD (USERID ASC)");
            System.out.println("\n-------------------------------------------------");

            // move cursor to last record
            results.last();
            while (results.previous()) {
                String rowResult = "";

                for (int i = 1; i <= 7; i++) {
                    String tmp = results.getString(i);

                    rowResult += tmp + "\t\t";
                }

                System.out.println(rowResult);
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    private static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL);
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println(sqlExcept.getMessage());
        }
    }
}
