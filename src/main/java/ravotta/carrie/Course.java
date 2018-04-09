package ravotta.carrie;

import java.io.Serializable;

/**
 * Course information
 */
public class Course implements Serializable {
    private int courseid;
    private int numRegistered;
    private String course_name;

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getNumRegistered() {
        return numRegistered;
    }

    public void setNumRegistered(int numRegistered) {
        this.numRegistered = numRegistered;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseid=" + courseid +
                ", numRegistered=" + numRegistered +
                ", course_name='" + course_name + '\'' +
                '}';
    }
}
