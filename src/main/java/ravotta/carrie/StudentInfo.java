package ravotta.carrie;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.flow.FlowScoped;
import java.io.Serializable;

/**
 * Student information
 */
@ManagedBean(name="StudentInfo")
@SessionScoped
//@ManagedBean
@FlowScoped("signup")
public class StudentInfo implements Serializable {
    private String first_name;
    private String last_name;
    private String ssn;
    private String email;
    private String userid;
    private String password;
    private String address;

    // store address sub-attributes separately to reference in form later
    private String street;
    private String city;
    private String state;
    private String zip;

    /**
     * Delete student from session
     */
    public void clearStudent() {
        this.first_name = null;
        this.last_name = null;
        this.ssn = null;
        this.email = null;
        this.userid = null;
        this.password = null;
        this.address = null;
        this.street = null;
        this.city = null;
        this.state = null;
        this.zip = null;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Add student to database
     *
     * @return next page to return to
     */
    public String addStudent() {
        // database commands
        Database database = new Database();
        String address = this.street;
        address += " " + this.city;
        address += ", " + this.state;
        address += " " + this.zip;

        // concat all address fields together and trim to 40 characters (DB limit)
        this.setAddress(address.substring(0, Math.min(address.length(), 40)));

        // add new student to the database
        database.addStudent(this);

        return "return";
    }

    /**
     * Try to log user in
     *
     * @return is user logged in
     */
    public boolean loginStudent() {
        // database commands
        Database database = new Database();

        // verify exist in database
        StudentInfo student = database.selectStudent(userid, password);

        // set database information to user object
        if (student != null) {
            setFirst_name(student.getFirst_name());
            setLast_name(student.getLast_name());
            setSsn(student.getSsn());
            setAddress(student.getAddress());
            setEmail(student.getEmail());

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", ssn='" + ssn + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
