package com.example.TesinaProjectBank.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="proyecttags")
public class ProyectTags {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idproyecttags;
	private int proyectid;
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name ="idtags")
	private Tag tags;
	
	public ProyectTags() {
		super();
	}

	public ProyectTags(int proyecttagsid, int proyectid, Tag tag) {
		super();
		this.idproyecttags = proyecttagsid;
		this.proyectid = proyectid;
		this.tags = tag;
	}

	public int getIdproyecttags() {
		return idproyecttags;
	}

	public void setIdproyecttags(int idproyecttags) {
		this.idproyecttags = idproyecttags;
	}

	public int getProyectid() {
		return proyectid;
	}
	public void setProyectid(int proyectid) {
		this.proyectid = proyectid;
	}
	public Tag getTags() {
		return tags;
	}
	public void setTags(Tag tag) {
		this.tags = tag;
	}
	
	
}
