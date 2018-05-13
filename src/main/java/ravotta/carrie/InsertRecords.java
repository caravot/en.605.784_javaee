package ravotta.carrie;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Hashtable;

/**
 * Recreate the database
 */
public class InsertRecords {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String url = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    /**
     * Run all functions to recreate database
     *
     * @param args
     * @throws NoSuchMethodException
     */
    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        createContext();
        createConnection();

//        try {
//            UserTransaction ut = (UserTransaction) ctx.lookup("javax/transaction/UserTransaction");
//            ut.setTransactionTimeout(1);
//            ut.begin();
//            Thread.sleep(3000);
//            ut.commit();
//        } catch (NotSupportedException | RollbackException | HeuristicRollbackException |
//                SystemException | HeuristicMixedException e) {
//            System.out.println("ERROR ERROR: exceptionClause()");
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            System.out.println("ERROR ERROR: InterruptedException()");
//            e.printStackTrace();
//        } catch (NamingException e) {
//            System.out.println("ERROR ERROR: NamingException()");
//            e.printStackTrace();
//        }

//        removeStudents();
//        getStudents();
//        getAllStatus();
//        buildTables();
//        readCSVFile("mock_students.csv", "students");
//        readCSVFile("mock_courses.csv", "courses");
        //simulateRegistrarCourseBean();
        //getRegistrar();
        RegistrarCourseBean registrarCourseBean = new RegistrarCourseBean();
        registrarCourseBean.addRegistrar();
        shutdown();
        closeContext();
    }

    private static void simulateRegistrarCourseBean() {
        try {
            // 15 seconds sleep
            Thread.sleep(15000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            throw new EJBException(ie);
        }
    }

    /**
     * Create connection to the database
     */
    private static void createConnection() {
        try {
            ds = (javax.sql.DataSource) ctx.lookup("jhuDataSource2");
            conn = ds.getConnection();
        } catch (Exception except) {
            except.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * Drop tables if they exist and recreate them
     */
    public static void buildTables() {
        // try to drop the tables
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

            // Create the STUDENT table
            stmt.execute("CREATE TABLE STUDENT (" +
                    "FIRST_NAME varchar(40)," +
                    "LAST_NAME varchar(40)," +
                    "SSN char(11)," +
                    "EMAIL varchar(40)," +
                    "ADDRESS varchar(40)," +
                    "USERID varchar(8)," +
                    "PASSWORD varchar(8)," +
                    "UNIQUE(USERID))");

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
        }
    }

    /**
     * Insert courses
     *
     * @param row
     */
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

    /**
     * Insert a single student record
     *
     * @param row
     */
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

    /**
     * Read a CSV file of data and insert into appropriate table
     *
     * @param fileName name of the CSV file to read
     * @param tableName name of the table to insert into
     */
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

    public static void removeStudents() {
        ResultSet resultSet;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "DELETE FROM STUDENT WHERE FIRST_NAME = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, "Carrie");

            pstmt.execute();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void getRegistrar() {
        ResultSet resultSet;
        RegistrarCourseBean registrarCourseBean = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM REGISTRAR";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            resultSet = pstmt.executeQuery();

            try {
                while (resultSet.next()) {
                    registrarCourseBean = new RegistrarCourseBean();
                    registrarCourseBean.setCourseId(resultSet.getInt("COURSEID"));
                    registrarCourseBean.setCurrentRegistered(resultSet.getInt("number_students_registered"));
                    System.out.println(registrarCourseBean.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void getStudents() {
        ResultSet resultSet;
        StudentInfo studentInfo = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            resultSet = pstmt.executeQuery();

            try {
                while (resultSet.next()) {
                    studentInfo = new StudentInfo();
                    studentInfo.setFirst_name(resultSet.getString("first_name"));
                    studentInfo.setLast_name(resultSet.getString("last_name"));
                    studentInfo.setAddress(resultSet.getString("address"));
                    studentInfo.setSsn(resultSet.getString("ssn"));
                    studentInfo.setEmail(resultSet.getString("email"));
                    studentInfo.setUserid(resultSet.getString("userid"));
                    studentInfo.setPassword(resultSet.getString("password"));
                    System.out.println(studentInfo.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void getAllStatus() {
        ResultSet resultSet;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT C.COURSEID, C.COURSE_NAME " +
                    "FROM REGISTRAR R RIGHT JOIN COURSES C ON C.COURSEID=R.COURSEID";
//            String insertQuery = "SELECT * FROM REGISTRAR R INNER JOIN COURSES C ON C.COURSEID=R.COURSEID";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            resultSet = pstmt.executeQuery();

            try {
                while (resultSet.next()) {
//                    System.out.println(resultSet.toString());
//                    System.out.println(resultSet.getString("course_name"));
                    System.out.println(resultSet.getInt("courseid"));
//                    System.out.println(resultSet.getInt("number_students_registered"));
                    System.out.println("=========");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    /**
     * Close database connection
     */
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


    /**
     * Make connection to WLS
     */
    public static void createContext() {
        try {
            Hashtable env = new Hashtable();

            // specifies the factory to be used to create the context
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            // specifies the URL of the WebLogic Server
            env.put(Context.PROVIDER_URL, "t3://localhost:7001");
            env.put("weblogic.jndi.createIntermediateContexts", "true");

            ctx = new InitialContext(env);
            System.out.println("Initial context created");
        } catch (NamingException e) {
            System.out.println(e.toString());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Close connection to WLS
     */
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
