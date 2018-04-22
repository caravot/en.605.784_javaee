package ravotta.carrie;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the REGISTRAR database table.
 *
 */
@Entity
@Table(name = "REGISTRAR")
public class Registrar implements Serializable {
    private int courseid;
    private int number_students_registered;

    /**
     * no arg-constructor
     */
    public Registrar() {
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
     * Set course id
     *
     * @param courseid Course id
     */
    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    /**
     * Get the number of students registered
     *
     * @return number of students registered
     */
    @Column(name="number_students_registered")
    public int getNumber_students_registered() {
        return this.number_students_registered;
    }

    /**
     * Set the number of students registered
     *
     */
    public void setNumber_students_registered(int number_students_registered) {
        this.number_students_registered = number_students_registered;
    }

    @Override
    public String toString() {
        return "Course {" +
                "courseid=" + this.courseid +
                ", number_students_registered='" + this.number_students_registered + '\'' +
                '}';
    }
}