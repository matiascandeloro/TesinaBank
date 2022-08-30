package com.example.TesinaProjectBank.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Mails")
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Proyect proyect;
	private String to;
	private String from;
	private String cc;
	private String header;
	private String text;
	private String attach;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Proyect getProyect() {
		return proyect;
	}
	public void setProyect(Proyect proyect) {
		this.proyect = proyect;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	
}
