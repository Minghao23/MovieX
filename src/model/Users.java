package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Users {
    protected String UserName;
    protected String FirstName;
    protected String LastName;
    protected String Email;
    protected String Password;

    //constructor

    public Users(String userName) {
        UserName = userName;
    }

    public Users(String userName, String firstName, String lastName, String email, String password) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
    }

    //getter and setter

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
