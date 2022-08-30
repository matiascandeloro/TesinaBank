package com.example.TesinaProjectBank.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="proyectdoc")
public class ProyectDoc {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int proyectid;
	private String path;
	private String filename;
	
	public ProyectDoc() {
		super();
	}
	public ProyectDoc(int proyectid, String path, String fileName) {
		super();
		this.proyectid = proyectid;
		this.path = path;
		this.filename=fileName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getProyectid() {
		return proyectid;
	}
	public void setProyectid(int proyectid) {
		this.proyectid = proyectid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String fileName) {
		this.filename = fileName;
	}
	
}
