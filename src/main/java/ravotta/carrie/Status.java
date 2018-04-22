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
    private EntityManager em;

    /**
     * no-arg constructor
     */
    public Status() {
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
        // return course list
        List<Courses> finalCourseList = new ArrayList<Courses>();

        // get current courses
        List<Courses> courseList = (List<Courses>) em.createQuery("SELECT c FROM Courses c").getResultList();

        // loop over all courses
        for (Courses c : courseList) {
            // get specific course information
            Courses course = ((Courses) em.find(Courses.class, c.getCourseid()));

            // course doesn't have registration yet; create empty registrar
            if (course.getRegistrar() == null) {
                Registrar registrar = new Registrar();
                registrar.setCourseid(c.getCourseid());
                registrar.setNumber_students_registered(0);

                // add registrar bean to course bean
                course.setRegistrar(registrar);
            }

            // add course to return list
            finalCourseList.add(course);
        }

        return finalCourseList;
    }
}
