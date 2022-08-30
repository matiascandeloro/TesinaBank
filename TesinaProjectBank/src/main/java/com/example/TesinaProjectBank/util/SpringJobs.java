package com.example.TesinaProjectBank.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.TesinaProjectBank.models.Config;
import com.example.TesinaProjectBank.models.Proyect;
import com.example.TesinaProjectBank.models.ProyectDoc;
import com.example.TesinaProjectBank.repository.ProyectDocRepository;
import com.example.TesinaProjectBank.repository.ProyectRepository;
import com.example.TesinaProjectBank.service.ApplicantStudentService;
import com.example.TesinaProjectBank.service.ConfigService;
import com.example.TesinaProjectBank.service.ProyectService;

@Configuration
@EnableScheduling
public class SpringJobs{

    private static final Logger logger = LogManager.getLogger(SpringJobs.class);
   
	@Autowired
	private ProyectRepository proyectRepository;
	@Autowired
	private ProyectService proyectService;
    @Autowired
	private ProyectDocRepository proyectDocRepository;
    @Autowired
	private ConfigService configService;
    @Autowired
	private ApplicantStudentService applicantStudentService;

	//@Scheduled(fixedRate = 1000)  // tarea cada 1 s
    //@Scheduled(cron = "0 0/1 * 1/1 * ?") //cada minuto
    @Scheduled(cron = "0 5 7 1/1 * ?") // todos los dias a las 7:05
    public void scheduleJobWithFixedRate() {
        logger.info("SpringJob: scheduleJobWithFixedRate");
        Job_config();
    }
    
    public void Job_config() {
    	Config config=configService.getConfig();
    	int DeleteTime=config.getDeleteTimeProposal();
    	int ViewTime=config.getViewTimeProposal();
    	Date now= new Date();
    	String asuntoAviso="Aviso de renovacion de Proyecto de Tesina";
    	String asuntoBorrado="Aviso de eliminacion de Proyecto de Tesina";
    	String cuerpoAviso="Estimado, por el presente se solicita la actualizacion / Modificacion del Proyecto: ";
    	String cuerpoBorrado="Estimado, por el presente se le comunica que por no haber generado actualizacion / Modificacion del Proyecto, en el plazo establecido, su proyecto fue eliminado: ";
    	
    	List<Proyect> listaDeProjectos=proyectRepository.findAll();
    	
    	for (Proyect p:listaDeProjectos) {

    		if (p.getLast_date_actualization().compareTo(getTimeLeft(ViewTime))<=0) {
    			if (p.getLast_date_actualization().compareTo(getTimeLeft(DeleteTime))<0) {
    				// Envio aviso
    				MailUtils.enviarConGMail(p.getContact_mail(), asuntoBorrado, cuerpoBorrado + p.getTitle() +", Saludos.");
    				//Borra archivos
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
    				
    				Proyect proy= proyectRepository.findById(p.getId());
    			    proyectRepository.deleteById(proy.getId());
    			}else {
    				if (!p.isOverdue_proposal()) {
	    				// Enviar aviso
	    				MailUtils.enviarConGMail(p.getContact_mail(), asuntoAviso, cuerpoAviso+ p.getTitle() +", Saludos.");
	    				// Marcar como avisado
	    				p.setOverdue_proposal(true);
	    				proyectRepository.save(p);
    				}
    			}
    		}
    	}
    }
    
    private Date getTimeLeft(int days) {
    	
    	Calendar c= Calendar.getInstance();
    	c.add(Calendar.DATE, -days);	
    	
    	return c.getTime();
    }   
    
}
