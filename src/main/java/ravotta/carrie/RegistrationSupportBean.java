package ravotta.carrie;

public class RegistrationSupportBean {
    private static Database database;
    private int CourseCapacity;
    private String courseName;
    private String courseId;
    public static String dsName;

    public void setCourse(String course) {
        String[] parts = course.split("_");

        this.courseId = parts[0];
        this.courseName = parts[1];
    }

    public void setCourseCapacity(int courseCapacity) {
        CourseCapacity = courseCapacity;
    }

    public String addRegistrar() {
        database = new Database();

        int currentRegistered = database.selectRegistrar(courseId, dsName);

        if (currentRegistered < CourseCapacity) {
            database.addRegistrar(courseId, currentRegistered + 1, dsName);

            return "You have been registered to " + courseId + " " + courseName;
        } else {
            return "Sorry, the registration to this course has been closed based on availability";
        }
    }
}
