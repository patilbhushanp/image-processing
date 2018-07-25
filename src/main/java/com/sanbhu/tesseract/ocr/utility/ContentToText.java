package com.sanbhu.tesseract.ocr.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

public class ContentToText implements Runnable{

	private static final File DATA_FILE_PATH = new File("F:\\Bhushan\\Java\\Implementation\\tesseract-ocr\\tesseract-ocr\\tessdata");
	
	private final Tesseract tesseractInstance = new Tesseract();
	
	private String originalFilePath;
	
	public ContentToText(final String originalFilePath) {		
		tesseractInstance.setDatapath(DATA_FILE_PATH.getAbsolutePath());
		//tesseractInstance.setLanguage("mar+eng");
		this.originalFilePath = originalFilePath;
	}
	@Override
	public void run() {
		try {
		String tesseractResult = tesseractInstance.doOCR(new File(originalFilePath));
		originalFilePath = originalFilePath + ".txt";
		createTextFile(new File(originalFilePath), tesseractResult);
		}catch(Exception exception) {
			System.err.println("Failed to convert file : " + exception.getLocalizedMessage());
		}
	}

	public boolean createTextFile(File textFile, String textString) throws IOException {
		boolean result = true;
		try (FileOutputStream fileOutputStream = new FileOutputStream(textFile);
				PrintWriter printWriter = new PrintWriter(fileOutputStream);) {
			printWriter.print(textString);
			printWriter.flush();
			printWriter.close();
		}
		return result;
	}
}
