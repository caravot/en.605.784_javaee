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
    public List<Courses> getStatus(int cid) {
        List<Courses> cList = new ArrayList<Courses>();
        cList.add(((Courses) em.find(Courses.class, cid)));
        return cList;
    }

    /**
     * Get registration for all courses
     *
     * @return list of single course information
     */
    public List<Courses> getAllStatus() {
        List<Courses> finalCourseList = new ArrayList<Courses>();

        // get current courses
        List<Courses> courseList = (List<Courses>) em.createQuery("SELECT c FROM Courses c").getResultList();

        for (Courses c : courseList) {
            Courses course = ((Courses) em.find(Courses.class, c.getCourseid()));

            // course doesn't have registration yet; create empty registrar
            if (course.getRegistrar() == null) {
                Registrar registrar = new Registrar();
                registrar.setCourseid(c.getCourseid());
                registrar.setNumber_students_registered(0);

                course.setRegistrar(registrar);
            }

            finalCourseList.add(course);
        }

        return finalCourseList;
    }
}
