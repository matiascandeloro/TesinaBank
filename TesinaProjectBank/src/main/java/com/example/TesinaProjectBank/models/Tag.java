package com.example.TesinaProjectBank.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Tags")
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int idtags;
	private String tagname;
	
	/*@ManyToMany(mappedBy = "proyectdoc",fetch = FetchType.EAGER)
    private List<Proyect> proyect;
	*/
	public int getIdtags() {
		return idtags;
	}
	public void setIdtags(int tagid) {
		this.idtags = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	/*
	public List<Proyect> getProyect() {
		return proyect;
	}
	public void setProyect(List<Proyect> proyect) {
		this.proyect = proyect;
	}*/
	
}
