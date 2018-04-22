package ravotta.carrie;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.ALL;

/**
 * The persistent class for the REGISTRAR database table.
 *
 */
@Entity
@Table(name = "REGISTRAR")
public class Registrar implements Serializable {
    private int courseid;
    private int number_students_registered;

    // no arg-constructor
    public Registrar() {
    }

    @Column(name="COURSEID")
    @Id
    public int getCourseid() {
        return this.courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    @Column(name="number_students_registered")
    public int getNumber_students_registered() {
        return this.number_students_registered;
    }

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