package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private long id;
    private String email;
    private String userName;
    private String password;

    public User(long id, String email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = hashPassword(password);
    }

    public User() {
    }

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = hashPassword(password);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNoHashPassword(String password) {
        this.password = hashPassword(password);
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
