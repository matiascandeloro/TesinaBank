package com.example.TesinaProjectBank.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Config")
public class Config {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int viewTimeProposal;
	private int deleteTimeProposal;
	private int maxAmoungApplicants;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getViewTimeProposal() {
		return viewTimeProposal;
	}
	public void setViewTimeProposal(int viewTimeProposal) {
		this.viewTimeProposal = viewTimeProposal;
	}
	public int getDeleteTimeProposal() {
		return deleteTimeProposal;
	}
	public void setDeleteTimeProposal(int deleteTimeProposal) {
		this.deleteTimeProposal = deleteTimeProposal;
	}
	public int getMaxAmoungApplicants() {
		return maxAmoungApplicants;
	}
	public void setMaxAmoungApplicants(int maxAmoungApplicants) {
		this.maxAmoungApplicants = maxAmoungApplicants;
	}
	
}
