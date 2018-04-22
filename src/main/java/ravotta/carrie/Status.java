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
    public List<CoursesBean> getStatus(int cid) {
        List<CoursesBean> cList = new ArrayList<CoursesBean>();
        cList.add(((CoursesBean) em.find(CoursesBean.class, cid)));
        return cList;
    }

    /**
     * Get registration for all courses
     *
     * @return list of single course information
     */
    public List<CoursesBean> getAllStatus() {
        // return course list
        List<CoursesBean> finalCourseList = new ArrayList<CoursesBean>();

        // get current courses
        List<CoursesBean> courseList = (List<CoursesBean>) em.createQuery("SELECT c FROM CoursesBean c").getResultList();

        // loop over all courses
        for (CoursesBean c : courseList) {
            // get specific course information
            CoursesBean course = ((CoursesBean) em.find(CoursesBean.class, c.getCourseid()));

            // course doesn't have registration yet; create empty registrar
            if (course.getRegistrar() == null) {
                RegistrarBean registrar = new RegistrarBean();
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
