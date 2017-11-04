package edu.swarthmore.cs.cs71.shelved;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String username;
    private String password;
    private String name;
    private String bio;
    private String location;
    private String salt;

    public User(String username, String password, String name, String bio, String location, String salt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.location = location;
        this.salt = salt;

    }

    public void changePassword(String oldPassword, String newPassword) {
        // if hash(oldPassword) = this.password {
        //      this.password = hash(newPassword)
        // } else {
        //      notify user to try again
        // }
    }

    // getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getSalt() {
        return salt;
    }

    //setters
    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
