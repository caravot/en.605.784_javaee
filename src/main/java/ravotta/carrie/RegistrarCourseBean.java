package ravotta.carrie;

import javax.ejb.*;
import javax.inject.Inject;
import javax.transaction.*;

/**
 * Support bean for registering a student to a course
 */
@Stateless(mappedName = "RegistrarCourseBean")
@TransactionManagement(TransactionManagementType.BEAN)
public class RegistrarCourseBean {
    private static Database database;
    private int courseId;
    private int currentRegistered;

    @Inject
    private UserTransaction ut;

    public int getCourseId() {
        return courseId;
    }

    public String getCourseIdString() {
        return courseId + "";
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

        try {
            ut.setTransactionTimeout(3);
            ut.begin();

            Thread.sleep(timeout);

            ut.commit();

            database.addRegistrar(this.getCourseIdString(), this.currentRegistered + 1);
        } catch (InterruptedException ie) {
            System.out.println("\tInterruptedException error");
            throw new EJBException(ie);
        } catch (SystemException e) {
            System.out.println("\tInterruptedException error");
        } catch (HeuristicMixedException | NotSupportedException | HeuristicRollbackException | RollbackException e) {
            System.out.println("\tGot an error: ");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "RegistrarCourseBean{" +
                "courseID=" + courseId +
                ", number_students_registered=" + currentRegistered +
                '}';
    }
}
