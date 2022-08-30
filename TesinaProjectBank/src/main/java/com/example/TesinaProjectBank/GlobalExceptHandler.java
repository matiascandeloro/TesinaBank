package com.example.TesinaProjectBank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.TesinaProjectBank.models.Proyect;

import io.jsonwebtoken.SignatureException;

@ControllerAdvice
public class GlobalExceptHandler {

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<?> handleMultipart(MultipartException e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("message", e.getCause().getMessage());
		System.out.println( e.getCause().getMessage());
		
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/*@ExceptionHandler(SignatureException.class)
	public String handleBadRequest(SignatureException e,RedirectAttributes attributes) {
		attributes.addFlashAttribute("message", e.getCause().getMessage());
		
		return "redirect:/status";
	}*/
}
