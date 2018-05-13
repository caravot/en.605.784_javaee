package ravotta.carrie;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Table;
import javax.transaction.*;
import javax.transaction.UserTransaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * All database methods/commands
 */
@ManagedBean
public class Database {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;
    private static String DATASOURCE_NAME = "jhuDataSource2";
    private static String WLS_URL = "t3://localhost:7001";

    /**
     * Create a database
     */
    public Database() {
        // do nothing
    }

    /**
     * Create connection to the database
     *
     */
    public static void createConnection() {
        // don't establish new connections if we have some already
        try {
            if (conn == null || conn.isClosed()) {
                try {
                    // get jndi for datasource
                    if (ctx == null) {
                        Hashtable env = new Hashtable();

                        // specifies the factory to be used to create the context
                        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

                        // specifies the URL of the WebLogic Server
                        env.put(Context.PROVIDER_URL, WLS_URL);

                        ctx = new InitialContext(env);
                    }
                    ds = (javax.sql.DataSource) ctx.lookup(DATASOURCE_NAME);
                    conn = ds.getConnection();
                } catch (Exception except) {
                    except.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a student to the database
     *
     * @param student Student bean
     */
    public static void addStudent(StudentInfo student) {
        // open database connection
        createConnection();

        String insertQuery = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, SSN, EMAIL, ADDRESS, USERID, PASSWORD) " +
                "values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            pstmt.setString(1, student.getFirst_name());
            pstmt.setString(2, student.getLast_name());
            pstmt.setString(3, student.getSsn());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getUserid());
            pstmt.setString(7, student.getPassword());

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }
    }

    /**
     * Determine if userid exists in the database
     *
     * @param userid Userid to lookup
     * @return boolean if userid exists
     */
    public static boolean validUserid(String userid) {
        ResultSet resultSet;

        // open database connection
        createConnection();

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT WHERE USERID = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, userid);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }

        return false;
    }

    /**
     * Select a student from the database
     *
     * @param userid Userid to query for
     * @param password Password to query for
     * @return StudentInfo object of the student looked up
     */
    public static StudentInfo selectStudent(String userid, String password) {
        ResultSet resultSet;

        // open database connection
        createConnection();

        StudentInfo studentInfo = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT WHERE USERID = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    studentInfo = new StudentInfo();
                    studentInfo.setFirst_name(resultSet.getString("first_name"));
                    studentInfo.setLast_name(resultSet.getString("last_name"));
                    studentInfo.setAddress(resultSet.getString("address"));
                    studentInfo.setSsn(resultSet.getString("ssn"));
                    studentInfo.setEmail(resultSet.getString("email"));
                    studentInfo.setUserid(resultSet.getString("userid"));
                    studentInfo.setPassword(resultSet.getString("password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }

        return studentInfo;
    }

    /**
     * Get all courses from database
     *
     * @return List<String> of courses
     */
    public static List<String> selectCourses() {
        ResultSet resultSet;
        List<String> courseList = new ArrayList<String>();

        // open database connection
        createConnection();

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM COURSES";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            resultSet = pstmt.executeQuery();

            try {
                while(resultSet.next()) {
                    courseList.add(resultSet.getString("COURSEID") + "_" + resultSet.getString("COURSE_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }

        return courseList;
    }

    public static UserTransaction getUserTransaction() {
        UserTransaction ut = new UserTransaction() {
            @Override
            public void begin() throws NotSupportedException, SystemException {
                System.out.println("Hello from begin()");
            }

            @Override
            public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
                    SecurityException, IllegalStateException, SystemException {
                System.out.println("Hello from commit()");
            }

            @Override
            public void rollback() throws IllegalStateException, SecurityException, SystemException {
                System.out.println("Hello from rollback()");
            }

            @Override
            public void setRollbackOnly() throws IllegalStateException, SystemException {
                System.out.println("Hello from setRollbackOnly()");
            }

            @Override
            public int getStatus() throws SystemException {
                System.out.println("Hello from getStatus()");
                return 0;
            }

            @Override
            public void setTransactionTimeout(int i) throws SystemException {
                System.out.println("Hello from setTransactionTimeout()");
            }
        };

        return ut;
    }

    /**
     * Register a student for a course
     *
     * @param courseid Courseid to register to
     * @param totalRegistered New value of total students registered
     */
    public static void addRegistrar(String courseid, int totalRegistered) {
        // open database connection
        createConnection();

        UserTransaction utx = getUserTransaction();

        try {
            utx.setTransactionTimeout(1);
            utx.begin();
            Thread.sleep(3000);

            String q;
            stmt = conn.createStatement();

            if (totalRegistered < 1) {
                q = "INSERT INTO REGISTRAR (number_students_registered, courseid) VALUES (?, ?)";
            } else {
                q = "UPDATE REGISTRAR SET number_students_registered = ? WHERE courseid = ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(q);
            pstmt.setInt(1, totalRegistered);
            pstmt.setString(2, courseid);
            pstmt.execute();
            utx.commit();
        } catch (SQLException | HeuristicRollbackException | HeuristicMixedException | InterruptedException |
                SystemException | NotSupportedException | RollbackException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            System.out.println("Hello from the addRegistrar finally statement");
            // close connection when done
            shutdown();
        }
    }

    /**
     * Get registration information for a course
     *
     * @param courseid Courseid to query for
     * @return number of students registered already
     */
    public static int selectRegistrar(String courseid) {
        ResultSet resultSet;
        int studentsRegistered = -1;

        // open database connection
        createConnection();

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM REGISTRAR WHERE courseid = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, courseid);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    studentsRegistered = resultSet.getInt("number_students_registered");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }

        return studentsRegistered;
    }

    /**
     * Disconnect from the database
     */
    public static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println(sqlExcept.getMessage());
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
