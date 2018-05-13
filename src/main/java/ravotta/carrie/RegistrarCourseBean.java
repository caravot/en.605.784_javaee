package ravotta.carrie;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import weblogic.transaction.TimedOutException;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.*;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * Support bean for registering a student to a course
 */
@Local
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.BEAN)
public class RegistrarCourseBean {
    private static Database database;
    private int courseId;
    private int currentRegistered;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseID) {
        this.courseId = courseID;
    }

    public int getCurrentRegistered() {
        return currentRegistered;
    }

    public void setCurrentRegistered(int currentRegistered) {
        this.currentRegistered = currentRegistered;
    }

    /**
     * Add student to course specified
     *
     * @return message to user
     */
    public void addRegistrar() {
        database = new Database();
        this.setCourseId(1);
        this.setCurrentRegistered(1);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://localhost:7001");
        Context ctx = null;

        try {
            ctx = new InitialContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        System.out.println("Hello from addRegistrar()");
        try {
            UserTransaction ut = (UserTransaction) ctx.lookup("javax/transaction/UserTransaction");
            ut.setTransactionTimeout(1);
            ut.begin();
            System.out.println("\tStart");
            Thread.sleep(3000);
            System.out.println("End");
            ut.commit();
        } catch (NotSupportedException | RollbackException | HeuristicRollbackException |
                SystemException | HeuristicMixedException e) {
            System.out.println("ERROR ERROR: exceptionClause()");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("ERROR ERROR: InterruptedException()");
            e.printStackTrace();
        } catch (NamingException e) {
            System.out.println("ERROR ERROR: NamingException()");
            e.printStackTrace();
        }

//        try {
//            utx.setTransactionTimeout(1);
//            utx.begin();
//            System.out.println(">>> Executing");
//            Thread.sleep(3000);
//            utx.commit();
//            System.out.println(">>> Completed");
//        } catch (NotSupportedException | RollbackException | HeuristicRollbackException |
//                SystemException | HeuristicMixedException e) {
//            System.out.println("ERROR ERROR: exceptionClause()");
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            System.out.println("ERROR ERROR: InterruptedException()");
//            e.printStackTrace();
//        }

        // set course information
//        setCourse(getCoursesSupportBean().getSelectedCourse());
//
//        // get number of students currently registered
//        int currentRegistered = database.selectRegistrar(courseId);
//
//        // registration is still open; add student
//        if (currentRegistered < getCourseCapacity()) {
//            database.addRegistrar(courseId, currentRegistered + 1);
//
//            setRegistered(true);
//        }
//        // registration is full
//        else {
//            setRegistered(false);
//        }
    }

    @Override
    public String toString() {
        return "RegistrarCourseBean{" +
                "courseID=" + courseId +
                ", number_students_registered=" + currentRegistered +
                '}';
    }
}
