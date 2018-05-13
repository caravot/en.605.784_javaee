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
@ManagedBean
@SessionScoped
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@TransactionManagement(TransactionManagementType.BEAN)
public class RegistrarCourseBean implements Serializable {
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
    public void addRegistrar(int timeout) {
        database = new Database();
        this.setCourseId(1);
        this.setCurrentRegistered(1);

        try {
            System.out.println("Start");
            Thread.sleep(31000);
        } catch (InterruptedException ie) {
            System.out.println("Got an error");
            ie.printStackTrace();
            throw new EJBException(ie);
        }


        database.addRegistrar("1", 2);
    }

    /**
     * Add student to course specified
     *
     * @return message to user
     */
    public void addRegistrar2() {
        database = new Database();
        this.setCourseId(1);
        this.setCurrentRegistered(1);

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
