package com.example.TesinaProjectBank.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileHelper {
	private final Path fileStorageLocation = null;
	
	public boolean saveFile(MultipartFile arch, long id) {
		boolean ret= false;
		
		try {
			byte[] fileBytes= arch.getBytes();
			Path path=Paths.get(getNameFile(arch,id));
			Files.write(path,fileBytes);
			ret= true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public String getNameFile(MultipartFile arch, long id) {
		StringBuilder builder= new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("spring_file");
		builder.append(File.separator);
		builder.append(id+"_"+arch.getOriginalFilename());
		
		return builder.toString();
	}
}
