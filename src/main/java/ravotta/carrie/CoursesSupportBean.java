package ravotta.carrie;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Support bean for displaying the course list
 */
@ManagedBean(name="courses")
@SessionScoped
public class CoursesSupportBean implements Serializable {
    private String selectedCourse;
    private List<String> courseList;

    /**
     * Initialize courses list and set selected course to first item
     */
    public CoursesSupportBean() {
        Database database = new Database();

        courseList = database.selectCourses();
    }

    /**
     * Get list of courses
     *
     * @return List of courses in registrar
     */
    public List<String> getCourses() {
        return courseList;
    }

    /**
     * Get selected course name
     *
     * @return selected course
     */
    public String getSelectedCourse() {
        return selectedCourse;
    }

    /**
     * Set course selected
     *
     * @param selectedCourse course selected
     */
    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

}
