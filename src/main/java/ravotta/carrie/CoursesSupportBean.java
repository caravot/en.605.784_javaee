package ravotta.carrie;

import java.util.List;

/**
 * Support bean for displaying the course list
 */
public class CoursesSupportBean {
    private static Database database;
    public static String dsName;
    public static String wlsUrl;

    /**
     * Get list of courses
     *
     * @return List of courses in registrar
     */
    public List<String> getCourses() {
        database = new Database();

        return database.selectCourses(dsName, wlsUrl);
    }
}
