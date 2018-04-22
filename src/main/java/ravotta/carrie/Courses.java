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

    // links to registrar
    private Registrar registrar;

    /**
     * no arg-constructor
     */
    public Courses() {
    }

    /**
     * Get course ID
     *
     * @return course id
     */
    @Column(name="COURSEID")
    @Id
    public int getCourseid() {
        return this.courseid;
    }

    /**
     * Set course ID
     *
     */
    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    /**
     * Get course name
     *
     * @return course name
     */
    @Column(name="COURSE_NAME")
    public String getCourse_name() {
        return this.course_name;
    }

    /**
     * Set course name
     *
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    // setup relationship of COURSES to REGISTRAR
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(updatable = false, insertable = false, name = "courseid", referencedColumnName = "courseid")


    /**
     * Get the registrar bean entity
     *
     * @return registrar bean
     */
    public Registrar getRegistrar() {
        return this.registrar;
    }

    /**
     * Set the registrar bean entity
     *
     * @param registrar bean
     */
    public void setRegistrar(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public String toString() {
        return "Course {" +
                "courseid=" + this.courseid +
                ", course_name='" + this.course_name +
                '}';
    }
}