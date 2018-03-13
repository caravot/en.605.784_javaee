package ravotta.carrie;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Student information
 */
@Named
@SessionScoped
//@ManagedBean
//@FlowScoped("signup")
public class StudentInfo implements Serializable {
    private String first_name = "Carrie";
    private String last_name = "Ravotta";
    private String ssn = "555555555";
    private String email = "carrie@gmail.com";
    private String userid = "weblogid";
    private String password = "asdfghjk";
    private String address;

    // store address sub-attributes seperately to reference in form later
    private String street = "2334 Chapel Hill";
    private String city = "Laurel";
    private String state = "MD";
    private String zip = "55555";

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

//    public StudentInfo loginStudent() {
//        // database commands
//        Database database = new Database();
//
//        // verify if the userid exists in the database
//        boolean userIdExists = database.validUserid(this.userid);
//
//        // valid input; verify exist in database
//        if (userIdExists) {
//            StudentInfo si = database.selectStudent(userid, password);
//
//        } else {
//
//        }
//
//        return this;
//    }

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
