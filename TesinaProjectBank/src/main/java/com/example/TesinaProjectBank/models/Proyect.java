package com.example.TesinaProjectBank.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="Proyects")
public class Proyect {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	private String title;
	
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private UserData userdata;
	private String description;
	private String name;
	private String lastname;
	private String contact_mail;
	private Date date_generation;
	private boolean overdue_proposal;
	private boolean deleted;
	private Date last_date_actualization;
	
	@OneToMany(cascade= CascadeType.MERGE,orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name ="proyectid")
	private List<ProyectDoc> proyectdoc;

	//@OneToMany(cascade= CascadeType.MERGE)
	//@JoinColumn(name ="proyectid")
	
    @JoinTable(
            name = "proyecttags",
            joinColumns = @JoinColumn(name = "proyectid", nullable = false),
            inverseJoinColumns = @JoinColumn(name="idtags", nullable = false)
        )
    @ManyToMany(cascade = CascadeType.ALL)
	private List<Tag> proyecttags;

	public List<Tag> getProyecttags() {
		return proyecttags;
	}
	public void setProyecttags(List<Tag> proyecttags) {
		this.proyecttags = proyecttags;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public UserData getUserdata() {
		return userdata;
	}
	public void setUserdata(UserData userId) {
		this.userdata = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getContact_mail() {
		return contact_mail;
	}
	public void setContact_mail(String contact_mail) {
		this.contact_mail = contact_mail;
	}
	public Date getDate_generation() {
		return date_generation;
	}
	public void setDate_generation(Date date_generation) {
		this.date_generation = date_generation;
	}
	public boolean isOverdue_proposal() {
		return overdue_proposal;
	}
	public void setOverdue_proposal(boolean overdue_proposal) {
		this.overdue_proposal = overdue_proposal;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public List<ProyectDoc> getProyectdoc() {
		return proyectdoc;
	}
	public void setProyectdoc(List<ProyectDoc> proyectdoc) {
		this.proyectdoc = proyectdoc;
	}
	public Date getLast_date_actualization() {
		return last_date_actualization;
	}
	public void setLast_date_actualization(Date last_date_actualization) {
		this.last_date_actualization = last_date_actualization;
	}
	
	
}
