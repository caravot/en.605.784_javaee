package ravotta.carrie;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Support bean for registering a student to a course
 */
@ManagedBean
@SessionScoped
public class RegistrationSupportBean implements Serializable {
    private static Database database;
    private int courseCapacity = 2;
    private String courseName;
    private String courseId;
    private boolean isRegistered = false;

    @ManagedProperty(value="#{courses}")
    private CoursesSupportBean coursesSupportBean;

    /**
     * Get courses bean
     *
     * @return CoursesBean bean
     */
    public CoursesSupportBean getCoursesSupportBean() {
        return coursesSupportBean;
    }

    /**
     * Set the courses bean
     *
     * @param coursesSupportBean
     */
    public void setCoursesSupportBean(CoursesSupportBean coursesSupportBean) {
        this.coursesSupportBean = coursesSupportBean;
    }

    /**
     * Check if the user was registered
     *
     * @return User was registered
     */
    public boolean isRegistered() {
        return isRegistered;
    }

    /**
     * Set if teh user was registered
     *
     * @param registered
     */
    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    /**
     * Set the course the user is trying to register for
     *
     * @param course Courseid_Coursename
     */
    public void setCourse(String course) {
        String[] parts = course.split("_");

        this.courseId = parts[0];
        this.courseName = parts[1];
    }

    /**
     * Set the max number of registrants for a course
     *
     * @param courseCapacity integer
     */
    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    /**
     * Get current course capacity
     *
     * @return Current course capacity
     */
    public int getCourseCapacity() {
        return courseCapacity;
    }

    /**
     * Get current course ID
     *
     * @return Current course ID
     */
    public String getCourseId() {
        return this.courseId;
    }

    /**
     * Add student to course specified
     *
     * @return message to user
     */
    public void addRegistrar() {
        database = new Database();

        // set course information
        setCourse(getCoursesSupportBean().getSelectedCourse());

        // get number of students currently registered
        int currentRegistered = database.selectRegistrar(courseId);

        // registration is still open; add student
        if (currentRegistered < getCourseCapacity()) {
            database.addRegistrar(courseId, currentRegistered + 1);

            setRegistered(true);
        }
        // registration is full
        else {
            setRegistered(false);
        }
    }
}
