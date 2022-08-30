package com.example.TesinaProjectBank.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="Users")
public class User {
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String mail;
	private boolean active;
	private String roles;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public UserData getUserdata() {
		UserData userData=new UserData();
		userData.setId(this.id);
		userData.setName(this.name);
		userData.setLastname(this.lastname);
		userData.setMail(this.mail);
		return userData;
	}
	public boolean isEmpty() {
		return this.name.compareTo("")==0 || this.lastname.compareTo("")==0 || this.mail.compareTo("")==0 ||
					this.password.compareTo("")==0 || this.username.compareTo("")==0;
	}
}
