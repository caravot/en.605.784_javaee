package ravotta.carrie;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the COURSE database table.
 *
 */
@Entity
@Table(name = "COURSES")
public class Courses implements Serializable {
    private int courseid;
    private String course_name;

    private Registrar registrar;

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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(updatable = false, insertable = false, name = "courseid", referencedColumnName = "courseid")

    public Registrar getRegistrar() {
        return this.registrar;
    }

    public void setRegistrar(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public String toString() {
        return "Course {" +
                "courseid=" + this.courseid +
                ", course_name='" + this.course_name +
                ", registrar='NOT SET'" +
                '}';
    }
}