package com.suusoft.elistening.social.googleplus;

import com.suusoft.elistening.model.User;

/**
 * Created by phamv on 7/25/2016.
 */
public class GUser {
    int id;

    String  fullname, email, gender,avatar;

    public GUser() {
    }

    public GUser(int id, String name, String email, String gender, String avatar) {
        this.id = id;
        this.fullname = name;
        this.email = email;
        this.gender = gender;
        this.avatar = avatar;
    }

    public User toUser(){
        User user = new User();
        user.setAvatar(avatar);
        user.setName(fullname);
        user.setEmail(email);
        user.setId(id);
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
