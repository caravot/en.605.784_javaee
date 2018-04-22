package ravotta.carrie;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the COURSE database table.
 *
 */
@Entity
public class Courses implements Serializable {
    private int courseid;
    private String course_name;

    // no arg-constructor
    public Courses() {

    }

    @Column(name="COURSEID")
    @Id
    public int getCourseid() {
        return this.courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    @Column(name="COURSE_NAME")
    public String getCourse_name() {
        return this.course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @Override
    public String toString() {
        return "Course {" +
                "courseid=" + this.courseid +
                ", course_name='" + this.course_name + '\'' +
                '}';
    }
}