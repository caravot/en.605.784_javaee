// At this point you are ready to develop a Java program/client that will bind three StudentInfo objects into
// the WebLogic Server JNDI naming service. Each of them should be an instance of the StudentInfo class
// that you just developed. A client can use rebind( ) method instead of bind( ) to be able to rerun it multiple
// times.
//
// A few tips I would like to give you. As a Java bean, the StudentInfo class has to be of type Serializable.
// Also, since you want WebLogic Server to bind an object to JNDI tree, WebLogic Server has to be able to
// load StudentInfo class. So, you have to include a location (path) of StudentInfo class into CLASSPATH
// before starting WebLogic Server.

import javax.naming.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

public class StudentInfoJDNI {
    /**
     * Runs this example from the command line.
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Usage: java examples.jndi.WebLogicContextExample WebLogicURL");
        }
        else {
            Context ctx = null;

            try {
                String url = (args.length == 1) ? args[0] : null;
                System.out.println(url);

                Hashtable env = new Hashtable();
                // specifies the factory to be used to create the context
                env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

                if (url != null) {
                    // specifies the URL of the WebLogic Server. Defaults to t3://localhost:7001
                    env.put(Context.PROVIDER_URL, url);
                }

                ctx = new InitialContext(env);
                System.out.println("Initial context created");

                try {
                    ctx.createSubcontext("StudentInfo");
                    System.out.println("Subcontext 'StudentInfo' created");
                }
                catch (NameAlreadyBoundException e) {
                    System.out.println("Subcontext 'StudentInfo' already exists; continuing with existing subcontext");
                }

                // create three student objects
                for (int i = 1; i <= 3; i++) {
                    StudentInfo studentInfo = new StudentInfo(
                            "Student " + i,
                            "Ravotta",
                            "8500 Upper Sky Way",
                            "123-456-7890",
                            "carrie@gmail.com"

                    );

                    String bindName = "StudentInfo.id_" + i;

                    // Create a unique String object (bindStr) and bind it to (bindName)
//                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.getDefault());
//                    bindStr = "StudentInfo object created at " + df.format(new Date());

                    try {
                        ctx.bind(bindName, studentInfo);
                        System.out.println("Bound '" + bindName + "' for the first time.");
                    } catch (NameAlreadyBoundException e) {
                        System.out.println("Overriding old binding. Rebinding '" + bindName + "'.");
                        // Force a new binding
                        ctx.rebind(bindName, studentInfo);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    StudentInfo studentInfo1 = (StudentInfo) ctx.lookup(bindName);
                    System.out.println(studentInfo1);
                    System.out.println(studentInfo.toString());
                    if (studentInfo1.equals(studentInfo)) {
                        System.out.println(bindName + " finished successfully.");
                    } else {
                        System.out.println(bindName + " failed.");
                    }
                }
            }
            catch (NamingException e) {
                System.out.println(e.toString());
            }
            catch (Exception e) {
                System.out.println(e.getStackTrace());
                System.out.println(e.getMessage());
            }
            finally {
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (NamingException e) {
                        System.out.println("Failed to close context due to: " + e);
                    }
                }
            }
        }
    }
}
