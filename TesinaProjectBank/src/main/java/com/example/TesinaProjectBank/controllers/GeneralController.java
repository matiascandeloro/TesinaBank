package com.example.TesinaProjectBank.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.metrics.StartupStep.Tags;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.TesinaProjectBank.models.ApplicantStudent;
import com.example.TesinaProjectBank.models.AuthenticationRequest;
import com.example.TesinaProjectBank.models.AuthenticationResponse;
import com.example.TesinaProjectBank.models.Config;
import com.example.TesinaProjectBank.models.Proyect;
import com.example.TesinaProjectBank.models.ProyectDoc;
import com.example.TesinaProjectBank.models.Tag;
import com.example.TesinaProjectBank.models.User;
import com.example.TesinaProjectBank.models.UserData;
import com.example.TesinaProjectBank.repository.ConfigRepository;
import com.example.TesinaProjectBank.repository.MailsRepository;
import com.example.TesinaProjectBank.repository.ProyectDocRepository;
import com.example.TesinaProjectBank.repository.ProyectRepository;
import com.example.TesinaProjectBank.repository.ProyectTagsRepository;
import com.example.TesinaProjectBank.repository.UserRepository;
import com.example.TesinaProjectBank.service.ApplicantStudentService;
import com.example.TesinaProjectBank.service.ConfigService;
import com.example.TesinaProjectBank.service.MyUserDetailsService;
import com.example.TesinaProjectBank.service.ProyectService;
import com.example.TesinaProjectBank.service.TagService;
import com.example.TesinaProjectBank.service.UserService;
import com.example.TesinaProjectBank.util.FileHelper;
import com.example.TesinaProjectBank.util.JwtTokenUtil;

import io.jsonwebtoken.Jwts;

@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "http://192.168.100.5:4200")
@RestController
public class GeneralController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ProyectRepository proyectRepository;
	@Autowired
	private ProyectDocRepository proyectDocRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private MailsRepository mailsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConfigService configService;
	@Autowired
	private ApplicantStudentService applicantStudentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProyectService proyectService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ProyectTagsRepository proyectTagsRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/authenticate", method = RequestMethod.POST)
	public ResponseEntity createAuthenticationToke(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		boolean retOk=false;
		ResponseEntity resp=new ResponseEntity(HttpStatus.OK);
		try {
			
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);	
			retOk=true;
			final UserDetails userDetails= userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			resp=new ResponseEntity(new AuthenticationResponse(jwt), HttpStatus.OK);
		} catch (BadCredentialsException e) {
			throw new Exception("Nombre de usuario o contraseña incorrecta", e);
		} catch (AuthenticationException e) {
			System.out.println(e);
		}
		
		//return ResponseEntity.ok(new AuthenticationResponse(jwt));
		return  resp;
	}
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.POST)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		String username=null;
		String jwt=null;
		java.util.Date date=null;
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			date= jwtTokenUtil.extractExpiration(jwt);
			UserDetails userDetails= userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(jwt));
			User user=userRepository.findByUsername(userDetails.getUsername());
			Map<String, Object> claims = new HashMap<>();
			claims.put("authorities", userDetails.getAuthorities());
			claims.put("userdataid", user.getId());
			if (Jwts.parser().setSigningKey(jwtTokenUtil.getKey().getBytes()).parseClaimsJws(jwt)!=null) {
				return ResponseEntity.ok(jwtTokenUtil.refreshToken(claims,jwtTokenUtil.extractUsername(jwt)));
			}
		}

		return ResponseEntity.ok("");
	}
	/*
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (java.util.Map.Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	*/
	@RequestMapping({"/proyects"})
	public ResponseEntity<?> proyects() {
		List<Proyect> lista=proyectRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping({"/saveProyect"})
	public ResponseEntity<?> saveProyect(@RequestBody Proyect proyect) {
		List<Tag> tagsTemp=null;
		proyect.setUserdata(userRepository.findById(proyect.getUserdata().getId()).getUserdata());
		if (proyect.getDate_generation()==null)
			proyect.setDate_generation(new Date(System.currentTimeMillis()));
		proyect.setLast_date_actualization(new Date(System.currentTimeMillis()));
		tagsTemp=proyect.getProyecttags();
		proyect.setProyecttags(new ArrayList<Tag>());
		proyectRepository.save(proyect);
		for (Tag p:tagsTemp) {
			proyect.getProyecttags().add(p);
			//p.setProyectid(proyRet.getId());
			//proyectTagsRepository.save(p);
		}
		Proyect proyRet=proyectRepository.save(proyect);
		 
		return ResponseEntity.ok(proyRet);
	}
	
	@PostMapping({"/deleteProyect"})
	public ResponseEntity<Proyect> deleteProyect(@RequestBody Proyect proyect) throws IOException{
		//TODO: validar si el proyecto le pertenece
		
		
		for (ProyectDoc proydoc: proyect.getProyectdoc()) {
			proyectDocRepository.delete(proydoc);
			Path fileToDeletePath = Paths.get(System.getProperty("user.home") + File.separator + "spring_file" + File.separator +proydoc.getProyectid() +"_"+proydoc.getFilename());
		    Files.delete(fileToDeletePath);
		}
		/*List<ProyectTags> proyectTagList=proyect.getProyecttags();
		proyect.setProyecttags(null);
		for (ProyectTags proytag:proyectTagList) {
			proyectTagsRepository.delete(proytag);
		}*/
		
		Proyect proy= proyectRepository.findById(proyect.getId());
	    proyectService.deleteById(proy.getId());
				
		return new ResponseEntity<Proyect>( HttpStatus.OK);	
	}
	
	
	@RequestMapping({"/config"})
	public ResponseEntity<?> config() {
		return ResponseEntity.ok(configRepository.findById(1));
	}
	
	@PostMapping({"/saveConfig"})
	public ResponseEntity<Config> saveConfig(@RequestBody Config config) {
		Config configRet=configRepository.save(config); 
		
		return new ResponseEntity<Config>(configRet, HttpStatus.OK);
	}
	
	@RequestMapping({"/mails"})
	public ResponseEntity<?> mails() {
		return ResponseEntity.ok(mailsRepository.findAll());
	}
	
	@RequestMapping({"/applicants"})
	public ResponseEntity<?> applicants() {
		return ResponseEntity.ok(applicantStudentService.getAll());
	}
	
	@PostMapping("/uploadFile")
	public ResponseEntity<?>  uploadFile(@RequestParam("files") MultipartFile[] files,@RequestParam("id") int proyectid) throws IOException {
		//TODO: validar si el proyecto le pertenece
		FileHelper fileHLP= new FileHelper();
		
		if (files==null ||files.length==0) {
			return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Proyect proy= proyectRepository.findById(proyectid);
		
		Arrays.asList(files).stream().forEach(file -> {
			fileHLP.saveFile(file, proyectid);
			if (!proy.getProyectdoc().contains(fileHLP.getNameFile(file, proyectid))){
				ProyectDoc proyDoc=new ProyectDoc(proyectid,fileHLP.getNameFile(file, proyectid),file.getOriginalFilename());
				proyectDocRepository.save(proyDoc);
			}
		});
		
		return new ResponseEntity( HttpStatus.OK);
	}
	
	@GetMapping("{filename:.+}")
    @ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		
		
		if (filename==null) {
			System.out.println("param is null");
			return ResponseEntity.ok(null);
		}
		
		File file = new File(System.getProperty("user.home") + File.separator + "spring_file" + File.separator + filename );

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
	
	@PostMapping("/deleteFile")
	public ResponseEntity<Resource> deleteFile(@RequestBody ProyectDoc proyectDoc) throws IOException {
		//TODO: validar si el proyecto le pertenece 
		if (proyectDoc==null) {
			System.out.println("Doc is null");
			return ResponseEntity.ok(null);
		}

		proyectDocRepository.delete(proyectDoc);
		
		Path fileToDeletePath = Paths.get(System.getProperty("user.home") + File.separator + "spring_file" + File.separator +proyectDoc.getProyectid() +"_"+proyectDoc.getFilename());
	    Files.delete(fileToDeletePath);
	    
		
        return ResponseEntity.ok(null);
                
	}
	@PostMapping({"/applicantProyect"})
	public ResponseEntity<?> applyProyect(@RequestParam("proyectid") int proyectid,@RequestParam("userid") int userid) {
		System.out.println(proyectid+" "+userid);
		Proyect proyect= proyectRepository.findById(proyectid);
		UserData user= userService.userToUserData(userRepository.getById(userid));
		
		String ret="";
		if (proyect.getId()==0 || proyect==null || user.getId()== 0 || user==null) {
			System.out.println("Proyect or user is null");
			return ResponseEntity.ok("Proyect or user is null");
		}
		
		ret=applicantStudentService.applyStudentToProyect(user,proyect);
		
		return ResponseEntity.ok(ret);
	}
	
	@PostMapping({"/proyectApplied"})
	public ResponseEntity<?> proyectAplied(@RequestParam("userid") int userid) {
		UserData user= userService.userToUserData(userRepository.getById(userid));
		ArrayList<Proyect> listaP= new ArrayList<Proyect>();
		if (user.getId()== 0 || user==null) {
			System.out.println("user is null");
			return ResponseEntity.ok("User is null");
		}
		List<ApplicantStudent> list=applicantStudentService.getListApplication(user);
		for (ApplicantStudent s:list) {
			listaP.add(proyectRepository.findById(s.getProyect().getId()));
		}
		
		if (listaP.size()>0) {
			return ResponseEntity.ok(listaP);
		}
		return ResponseEntity.ok("Todavia no aplico a ningun proyecto.");
	}
	
	@RequestMapping({"/proyectsSearch"})
	public ResponseEntity<?> proyects(@RequestParam("valueToSearch") String valueToSearch,@RequestParam("tagToSearch") String tags) {

		
		String listTag[]=null;
				if (tags.length() >0) {
					listTag=tags.split(",");
				}
		List<Proyect> lista=proyectService.getCoincidence(valueToSearch, listTag);
		
		if (lista.size()>0) {
			return ResponseEntity.ok(lista);
		}
		return ResponseEntity.ok("No se encontraron resultados");
	}
	@RequestMapping({"/users"})
	public ResponseEntity<?> users() {
		List<User> lista=userService.getAll();
		if (lista.size()>0) {
			return ResponseEntity.ok(lista);
		}
		return ResponseEntity.ok("No se encontraron resultados");
	}
	
	@RequestMapping({"/saveUser"})
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		if (user!=null) {
			if (user.getId()==0) {
				User us=userService.getUserByName(user);
				if (us!=null) {
					return ResponseEntity.ok("El usuario ya existe");
				}
			}
			if (!user.isEmpty()) {
				return ResponseEntity.ok(userService.saveUser(user));
			}
		}
		return ResponseEntity.ok("Hubo un problema");
	}
	@RequestMapping({"/deleteUser"})
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		if (user!=null) {
			userService.deleteUser(user);
			return ResponseEntity.ok("Usuario eliminado.");
		}
		return ResponseEntity.ok("Hubo un problema");
	}
	@RequestMapping({"/existsUser"})
	public ResponseEntity<?> userExists(@RequestBody User user) {
		if (user!=null) {
			return ResponseEntity.ok(userService.getUserByName(user));
		}
		return ResponseEntity.ok("Username no encontrado");
	}
	
	@RequestMapping({"/tags"})
	public ResponseEntity<?> getTags() {
		List<Tag> tags= tagService.getAllTags();
		if (tags!=null) {
			return ResponseEntity.ok(tags);
		}
		return ResponseEntity.ok("tags no encontrados");
	}
	
	@RequestMapping({"/addTag"})
	public ResponseEntity<?> tagToSave(@RequestParam("tagname") String tag ) {
		Tag tagToCreate=null;
		if ( tag.compareTo("")!=0) {
			tagToCreate=new Tag();
			tagToCreate.setTagname(tag);
			return ResponseEntity.ok(tagService.saveTag(tagToCreate));
		}
		return ResponseEntity.ok("Hubo un error al asignar el Tag");
	}
	
	@RequestMapping({"/addTagToSave"})
	public ResponseEntity<?> tagToSave(@RequestParam("proyectid") int proyectid,@RequestParam("tagname") String tag ) {
		
		if (proyectid!=0 && tag.compareTo("")!=0) {
			
			return ResponseEntity.ok(proyectService.addTagToProyect(tag, proyectid));
		}
		return ResponseEntity.ok("Hubo un error al asignar el Tag");
	}
	
	@RequestMapping({"/tagToDelete"})
	public ResponseEntity<?> tagToDelete(@RequestParam("proyectid") int proyectid,@RequestParam("tagname") String tag ) {
		
		
		if (proyectid!=0 && tag.compareTo("")!=0) {
			
			return ResponseEntity.ok(proyectService.deleteTagFromProyect(tag, proyectid));
		}
		return ResponseEntity.ok("Hubo un error al asignar el Tag");
	}
	
	@RequestMapping({"/test"})
	public ResponseEntity<?> test( ) {
		
		System.out.println(System.getProperty("user.home"));		
		
		return ResponseEntity.ok("Test");
	}
	
}
