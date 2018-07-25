package com.sanbhu.tesseract.ocr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import com.sanbhu.tesseract.ocr.bo.ImageInformation;
import com.sanbhu.tesseract.ocr.utility.GetImageInformation;
import com.sanbhu.tesseract.ocr.utility.RotateImage;

public class TesseactApp {
	public static void main(String[] args) throws Exception {
		final File inputFile = new File("F:\\Bhushan\\OCRTesseract\\img1.jpg");
		final String mimeType = new MimetypesFileTypeMap().getContentType(inputFile);
		final List<Thread> threadList = new ArrayList<Thread>();
		System.out.println("mimeType : " + mimeType);
		if(mimeType.contains("image")) {
			
			//rotate image
			final ImageInformation imageInformation = GetImageInformation.getInstance().getImageInformation(inputFile);
			RotateImage.getInstance().rotateImage(inputFile, imageInformation);
			
			//Convert Image into GrayScale
			//final File outputFile = GrayScaleImage.getInstance().convertImage(inputFile);

			//Convert Image into Black and White 
			//final File outputFile = BlackAndWhiteImage.getInstance().convertImage(inputFile);
			
			//Image Reading through tesseract
			/*final Thread contentToTextThread = new Thread(new ContentToText(inputFile.getAbsolutePath()));
			contentToTextThread.start();
			threadList.add(contentToTextThread);*/
		} else {
			// Split file into multiple PDF single page per file.
			//final List<String> splitFileList = PdfSpliter.getInstance().splitPdf(inputFile, new File("./tmp/"));
			
			//Convert PDF file into readable text through tesseract
			/*for (final String splitFile : splitFileList) {
				final Thread contentToTextThread = new Thread(new ContentToText(splitFile));
				contentToTextThread.start();
				threadList.add(contentToTextThread);
			}*/	
		}		
		for(Thread contentToTextThread: threadList) {
			contentToTextThread.join();
		}
	}
}
