package com.sanbhu.tesseract.ocr.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PdfSpliter {

	public final static PdfSpliter pdfSpliter = new PdfSpliter();

	private PdfSpliter() {
	}

	public static PdfSpliter getInstance() {
		return pdfSpliter;
	}

	public java.util.List<String> splitPdf(File pdfFile) {
		return splitPdf(pdfFile, new File("."));
	}
	
	public java.util.List<String> splitPdf(File pdfFile, File directoryPath) {
		java.util.List<String> splitPdfFileList = new ArrayList<String>();
		try {
			if(!directoryPath.isDirectory()) {
				directoryPath.mkdir();
			}
			PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
			System.out.println("Is Encrypted : " + reader.isEncrypted());
			int n = reader.getNumberOfPages();
			System.out.println("Number of pages : " + n);
			int i = 0;
			while (i < n) {
				String fileName = pdfFile.getName();
				String outputFile = directoryPath.getAbsolutePath() + File.separator + fileName.substring(0, fileName.indexOf(".pdf")) + "-" + String.format("%03d", i + 1)
						+ ".pdf";
				System.out.println("Writing into " + new File(outputFile).getAbsolutePath());
				splitPdfFileList.add(new File(outputFile).getAbsolutePath());
				Document document = new Document(reader.getPageSizeWithRotation(1));
				PdfCopy writer = new PdfCopy(document, new FileOutputStream(outputFile));
				document.open();
				PdfImportedPage page = writer.getImportedPage(reader, ++i);
				writer.addPage(page);
				document.close();
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return splitPdfFileList;
	}
}
