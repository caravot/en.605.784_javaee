package ravotta.carrie;

/**
 * Support bean for registering a student to a course
 */
public class RegistrationSupportBean {
    private static Database database;
    private int CourseCapacity;
    private String courseName;
    private String courseId;
    public static String dsName;
    public static String wlsUrl;

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
        CourseCapacity = courseCapacity;
    }

    /**
     * Add student to course specified
     *
     * @return message to user
     */
    public String addRegistrar() {
        database = new Database();

        // get number of students currently registered
        int currentRegistered = database.selectRegistrar(courseId, dsName, wlsUrl);

        // registration is still open; add student
        if (currentRegistered < CourseCapacity) {
            database.addRegistrar(courseId, currentRegistered + 1, dsName, wlsUrl);

            return "You have been registered to " + courseId + " " + courseName;
        }
        // registration is full
        else {
            return "Sorry, the registration to this course has been closed based on availability";
        }
    }
}
