package com.example.TesinaProjectBank.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRUtils {
	
	public boolean searchInPDF(String value, String pathWithFile) {
		
		return getPDF(pathWithFile).toLowerCase().contains(value);
	}
	
	public String getPDF(String pathWithFile) {
		PDDocument document2;
		File file = new File(pathWithFile);
		String text ="";
		try {
			document2=PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document2);
			document2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public boolean searchInIMG(String value, String pathWithFile) {
		
		return getIMG(pathWithFile).toLowerCase().contains(value);
	}
	
	public String getIMG(String pathWithFile) {
		String text="";
		Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath(System.getProperty("user.home") + File.separator + "spring_file" + File.separator +"tessdata");
        	tesseract.setLanguage("eng");
        	text = tesseract.doOCR(new File(pathWithFile));
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }
		return text;
	}
	
	public boolean searchInDoc(String value, String pathWithFile) {
		
		return getDoc(pathWithFile).toLowerCase().contains(value);
	}
	
	public String getDoc(String pathWithFile) {
		String docText ="";
		try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(pathWithFile)))) {

            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
            docText = xwpfWordExtractor.getText();
		 }catch (IOException e) {
	        	e.printStackTrace();
        }
		return docText;
	}
}
