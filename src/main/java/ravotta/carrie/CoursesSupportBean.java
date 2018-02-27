package ravotta.carrie;

import java.util.List;

public class CoursesSupportBean {
    private static Database database;
    public static String dsName;

    public List<String> getCourses() {
        database = new Database();

        return database.selectCourses(dsName);
    }
}
