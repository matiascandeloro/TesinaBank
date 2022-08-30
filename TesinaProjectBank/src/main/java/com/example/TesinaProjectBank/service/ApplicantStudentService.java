package com.example.TesinaProjectBank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.ApplicantStudent;
import com.example.TesinaProjectBank.models.Proyect;
import com.example.TesinaProjectBank.models.UserData;
import com.example.TesinaProjectBank.repository.ApplicantStudentRepository;
import com.example.TesinaProjectBank.util.MailUtils;

@Service
public class ApplicantStudentService {
	@Autowired
	private ApplicantStudentRepository applicantStudentRepository;
	@Autowired
	private ConfigService configService;
	
	public List<ApplicantStudent> getListApplication(UserData user){
		ArrayList<ApplicantStudent> lista=new ArrayList<ApplicantStudent>();
		for (ApplicantStudent s:getAll()) {
			if (s.getUserdata().getId()==user.getId()) {
				lista.add(s);
			}
		}
		return lista;
	}
	
	public List<ApplicantStudent> getAll(){
		return applicantStudentRepository.findAll();
	}
	
	public String applyStudentToProyect(UserData user,Proyect proyect) {
		String ret="Se ha registrado su peticion";
		int qtyApply=0;
		ApplicantStudent applicantStudent= new ApplicantStudent();
		String asunto="Hay un estudiante que desea aplicar a un proyecto";
		String cuerpo="El estudiante "+user.getName()+" "+user.getLastname()+" desea aplicar al proyecto: "+
				proyect.getTitle()+", la direccion de contacto es "+user.getMail()+" , saludos.";
		
		// applicar estudiante a proyecto
		qtyApply=countUserApply(user);
		//verifico que no exceda la cantidad de proyectos configurados
		if (qtyApply>=configService.getConfig().getMaxAmoungApplicants()) {
			return "El estudiante ya aplico al maximo de materias.";
		}
		// verifico que no haya aplicado ya al proyecto
		if (isAppliedProyect(user,proyect)) {
			return "El estudiante ya aplico a este proyecto.";
		}
		
		//applico al estudiante 
		applicantStudent.setProyect(proyect);
		applicantStudent.setUserdata(user);
		applicantStudentRepository.save(applicantStudent);
		//envio mail
		MailUtils.enviarConGMail(user.getMail(), asunto, cuerpo);
		return ret;
	}
	
	private int countUserApply(UserData user) {
		int count=0;
		
		for (ApplicantStudent as:getAll()) {
			if (user.getId()==as.getUserdata().getId()) {
				count++;
			}
		}
		
		return count;
	}
	
	private boolean isAppliedProyect(UserData user, Proyect proyect) {
		boolean ret=false;
		for (ApplicantStudent as:getAll()) {
			if (user.getId()==as.getUserdata().getId() && proyect.getId()==as.getProyect().getId()) {
				ret=true;
			}
		}
		
		return ret;
	}
	
	public void deplyAllStudents(int proyectid) {
		for (ApplicantStudent as:getAll()) {
			if (as.getProyect().getId()==proyectid) {
				applicantStudentRepository.deleteById(as.getId());
			}
		}
	}
}
