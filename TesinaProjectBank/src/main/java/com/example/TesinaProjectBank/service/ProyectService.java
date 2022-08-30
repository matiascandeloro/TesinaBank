package com.example.TesinaProjectBank.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.Proyect;
import com.example.TesinaProjectBank.models.ProyectDoc;
import com.example.TesinaProjectBank.models.ProyectTags;
import com.example.TesinaProjectBank.models.Tag;
import com.example.TesinaProjectBank.repository.ProyectDocRepository;
import com.example.TesinaProjectBank.repository.ProyectRepository;
import com.example.TesinaProjectBank.repository.ProyectTagsRepository;
import com.example.TesinaProjectBank.util.OCRUtils;

@Service
public class ProyectService {
	@Autowired
	private ProyectRepository proyectRepository;
	@Autowired
	private ApplicantStudentService applicantStudentService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ProyectDocRepository proyectDocRepository;
	@Autowired
	private ProyectTagsRepository proyectTagsRepository;

	
	public List<Proyect> getProyects(){
		return proyectRepository.findAll();
	}
	
	public void deleteById(int proyectId) {
		Proyect p= proyectRepository.getById(proyectId);
		if (p.getProyectdoc()!=null) {
			for (ProyectDoc proydoc: p.getProyectdoc()) {
				proyectDocRepository.delete(proydoc);
				Path fileToDeletePath = Paths.get(System.getProperty("user.home") + File.separator + "spring_file" + File.separator +proydoc.getProyectid() +"_"+proydoc.getFilename());
			    try {
					Files.delete(fileToDeletePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			    
			}
		}
		applicantStudentService.deplyAllStudents(p.getId());
		

		p.setProyecttags(null);
		proyectRepository.save(p);
		
		Proyect proy= proyectRepository.findById(p.getId());
	    proyectRepository.deleteById(proy.getId());
	}
		
	public List<Proyect> getCoincidence(String value, String tags[]){
		List<Proyect> retList= new ArrayList<Proyect>();	
		List<Proyect> listToSearch= getProyects();	
		OCRUtils pdfUtils=new OCRUtils();
		value=value.toLowerCase();
		boolean found=false;
		for (Proyect p: listToSearch) {
			found=false;
			if (value!="" && value.length()>0) {
				if (p.getTitle().toLowerCase().contains(value) || p.getDescription().toLowerCase().contains(value) ||
						p.getContact_mail().toLowerCase().contains(value)|| p.getLastname().toLowerCase().contains(value)|| p.getName().toLowerCase().contains(value)) {
					found=true;
				}

				for (ProyectDoc proydoc: p.getProyectdoc()) {
					Path fileToDeletePath = Paths.get(System.getProperty("user.home") + File.separator + "spring_file" + File.separator +proydoc.getProyectid() +"_"+proydoc.getFilename());
					if (proydoc.getFilename().endsWith(".pdf")) {
						if (pdfUtils.searchInPDF(value, fileToDeletePath.toString())){
							found=true;
						}
					}
					if (proydoc.getFilename().endsWith(".docx") || proydoc.getFilename().endsWith(".doc")) {
						if (pdfUtils.searchInDoc(value, fileToDeletePath.toString())){
							found=true;
						}
					}
					if (proydoc.getFilename().endsWith(".jpg") || proydoc.getFilename().endsWith(".png")) {
						if (pdfUtils.searchInIMG(value, fileToDeletePath.toString())){
							found=true;
						}
					}
				}
			}
			if (tags!=null) {
				for (Tag pTag:p.getProyecttags()) {
					for(int i=0;i<tags.length;i++) {
						if (pTag.getIdtags()==Integer.parseInt(tags[i])) {
							found=true;
						}
					}
				}
			}
			if (found) {
				retList.add(p);
			}
		}
		
		return retList;
	}
	
	public Proyect addTagToProyect(String tag, int proyectid) {
		ProyectTags proyTag= null;
		Tag tagToAssing=tagService.getTag(tag);
		if (tagToAssing==null) {
			Tag tagToSave=new Tag();
			tagToSave.setTagname(tag);
			tagToAssing=tagService.saveTag(tagToSave);
		}
		proyTag=getProyectTagId(proyectid,tagToAssing.getTagname());
		if (proyTag==null) {
			proyTag= new ProyectTags();
			proyTag.setProyectid(proyectid);
			proyTag.setTags(tagToAssing);
			proyectTagsRepository.save(proyTag);
		}
		
		return proyectRepository.findById(proyectid);
	}
	
	public Proyect deleteTagFromProyect(String tagname, int proyectid) {
		Proyect proy= proyectRepository.findById(proyectid);
		Tag tag=tagService.getTag(tagname);
		if (proy==null) {
			return null;	
		}
		
		for (int i=0;i<proy.getProyecttags().size();i++) {
			if (proy.getProyecttags().get(i).getIdtags()==tag.getIdtags()) {
				proy.getProyecttags().remove(i);
			}
		}
		proy= proyectRepository.save(proy);
		
		return proy;
	}
	
	public ProyectTags getProyectTagId(int proyectid,String tagname) {
		ProyectTags ret=null;
		for (ProyectTags p:proyectTagsRepository.findAll()) {
			if (p.getProyectid()==proyectid && p.getTags().getTagname().compareTo(tagname)==0) {
				ret=p;
			}
		}
		return ret;
	}
}
