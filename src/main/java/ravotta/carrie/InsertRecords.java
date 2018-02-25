package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Hashtable;

public class InsertRecords {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String url = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    public static void main(String[] args) throws NoSuchMethodException {
        createContext();
        createConnection();
        buildTables();
        readCSVFile("mock_students.csv", "students");
        readCSVFile("mock_courses.csv", "courses");
        shutdown();
        closeContext();
    }

    private static void createConnection() {
        try {
            ds = (javax.sql.DataSource) ctx.lookup("jhuDataSource2");
            conn = ds.getConnection();
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static void buildTables() {
        try {
            Statement stmt = conn.createStatement();

            stmt.execute("DROP TABLE STUDENT");
            stmt.execute("DROP TABLE COURSES");
            stmt.execute("DROP TABLE REGISTRAR");
        } catch (SQLException ex) {
            // continue to flow
        }

        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // drop previously created tables

            // Create the STUDENT table
            stmt.execute("CREATE TABLE STUDENT (" +
                    "FIRST_NAME varchar(40)," +
                    "LAST_NAME varchar(40)," +
                    "SSN char(11)," +
                    "EMAIL varchar(40)," +
                    "ADDRESS varchar(40)," +
                    "USERID varchar(8)," +
                    "PASSWORD varchar(8)," +
                    "UNIQUE(FIRST_NAME, LAST_NAME))");

            // Create the COURSES table
            stmt.execute("CREATE TABLE COURSES (" +
                    "COURSEID INTEGER," +
                    "COURSE_NAME varchar(40)," +
                    "UNIQUE(COURSEID))");

            // Create the REGISTRAR table
            stmt.execute("CREATE TABLE REGISTRAR (" +
                    "COURSEID INTEGER," +
                    "number_students_registered INTEGER," +
                    "UNIQUE(COURSEID))");
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
    }

    private static void insertCourses(String[] row) {
        String insertQuery = "INSERT INTO COURSES (COURSEID, COURSE_NAME) values (?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, row[0]);
            pstmt.setString(2, row[1]);

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
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

    private static void readCSVFile(String fileName, String tableName) {
        String csvFile = InsertRecords.class.getClassLoader().getResource(fileName).getFile();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                if (tableName.equals("students")) {
                    insertStudent(line.split(cvsSplitBy));
                } else if (tableName.equals("courses")) {
                    insertCourses(line.split(cvsSplitBy));
                }
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

    private static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                ds.getConnection();
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println(sqlExcept.getMessage());
        }
    }


    public static void createContext() {
        try {
            Hashtable env = new Hashtable();

            // specifies the factory to be used to create the context
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            if (url != null) {
                // specifies the URL of the WebLogic Server. Defaults to t3://localhost:7001
                env.put(Context.PROVIDER_URL, url);
            }

            ctx = new InitialContext(env);
            System.out.println("Initial context created");
        } catch (NamingException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeContext() {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                System.out.println("Failed to close context due to: " + e);
            }
        }
    }
}
