package ravotta.carrie;

import javax.naming.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class StudentInfoJDNI {
    private static String url = null;
    private static Context ctx = null;
    private static int TOTAL_STUDENTS = 3;
    private static List<StudentInfo> studentList = new ArrayList<StudentInfo>();

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Usage: java examples.jndi.WebLogicContextExample WebLogicURL");
        }

        url = (args.length == 1) ? args[0] : null;

        try {
            createContext();
            addStudents();
            getStudents();
            closeContext();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            closeContext();
        }
    }

    public static void addStudents() throws NamingException {
        try {
            ctx.createSubcontext("StudentInfo");
            System.out.println("Subcontext 'StudentInfo' created");
        } catch (NameAlreadyBoundException e) {
            System.out.println("Subcontext 'StudentInfo' already exists; continuing with existing subcontext");
        }

        // create three student objects
        for (int i = 1; i <= TOTAL_STUDENTS; i++) {
//            StudentInfo studentInfo = new StudentInfo(
//                    "Student " + i,
//                    "Ravotta",
//                    "8500 Upper Sky Way",
//                    "123-456-7890",
//                    "carrie@gmail.com"
//            );

//            studentList.add(studentInfo);
//
//            String bindName = "StudentInfo." + studentInfo.getFirst_name();
//
//            try {
//                ctx.bind(bindName, studentInfo);
//                System.out.println("Bound '" + bindName + "' for the first time.");
//            } catch (NameAlreadyBoundException e) {
//                System.out.println("Overriding old binding. Rebinding '" + bindName + "'.");
//                ctx.rebind(bindName, studentInfo);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
        }
    }

    public static void getStudents() throws NamingException {
        System.out.println("\n\nSTUDENT INFO RETURNED FROM JNDI\n");
        for (StudentInfo student : studentList) {
            StudentInfo jndiStudent = (StudentInfo) ctx.lookup("StudentInfo." + student.getFirst_name());
            System.out.print(jndiStudent.toString() + "\n\n");
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
