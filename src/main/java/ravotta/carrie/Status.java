package ravotta.carrie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Status information about registration
 */
@Stateless(mappedName = "StatusBean")
public class Status {
    @PersistenceContext(unitName = "assignment10")
    // database connection
//    private static Database database;
    private EntityManager em;

    public Status() {
        // do nothing
    }

    /**
     * Get registration status for a specific course
     *
     * @param cid Course id to query for
     * @return list of course information
     */
    public List<CourseOLD> getStatus(int cid) {
//        database = new Database();
//        return database.getRegistrationList(cid);
        List<CourseOLD> cList = new ArrayList<CourseOLD>();
        Courses courseBean = ((Courses) em.find(Courses.class, cid));
        Registrar registrarBean = ((Registrar) em.find(Registrar.class, cid));
        System.out.println(registrarBean.toString());
        System.out.println(courseBean.toString());
        //cList.add(courseBean);
        return cList;
    }

    /**
     * Get registration for all courses
     *
     * @return list of single course information
     */
    public List<CourseOLD> getAllStatus() {
        List<CourseOLD> cList = new ArrayList<CourseOLD>();
        List<Courses> courses = (List<Courses>) em.createQuery("SELECT c FROM COURSES c").getResultList();

        for (Courses c : courses) {
            System.out.println(c.toString());
            Registrar registrarBean = ((Registrar) em.find(Registrar.class, c.getCourseid()));
            System.out.println(registrarBean.toString());
            System.out.println(c.toString());
        }
        return cList;
//        database = new Database();
//        return database.getRegistrationList();
    }
}
