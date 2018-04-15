package ravotta.carrie;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Status information about registration
 */
@Stateless(mappedName = "StatusBean")
public class Status {
    // database connection
    private static Database database;

    public Status() {
        // do nothing
    }

    /**
     * Get registration status for a specific course
     *
     * @param cid Course id to query for
     * @return list of course information
     */
    public List<Course> getStatus(int cid) {
        database = new Database();
        return database.getRegistrationList(cid);
    }

    /**
     * Get registration for all courses
     *
     * @return list of single course information
     */
    public List<Course> getAllStatus() {
        database = new Database();
        return database.getRegistrationList();
    }
}
