package com.suusoft.elistening.social.facebook;


import com.suusoft.elistening.model.User;

public class FbUser {
	int id;
	String  name, email, gender,avatar;

	public FbUser(int id, String name, String email, String gender, String avatar) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.avatar = avatar;
	}

	public User toUser(){
		User user = new User();
		user.setAvatar(avatar);
		user.setName(name);
		user.setEmail(email);
		user.setId(id);
		return user;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
