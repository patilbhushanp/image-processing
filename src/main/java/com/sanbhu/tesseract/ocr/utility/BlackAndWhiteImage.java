package com.sanbhu.tesseract.ocr.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlackAndWhiteImage {
	public final static BlackAndWhiteImage blackAndWhiteImage = new BlackAndWhiteImage();

	private BlackAndWhiteImage() {
	}

	public static BlackAndWhiteImage getInstance() {
		return blackAndWhiteImage;
	}

	public File convertImage(File inputFile) throws IOException {
		File outputFile = null;
		final BufferedImage masterImage = ImageIO.read(inputFile);
		final BufferedImage blackWhite = new BufferedImage(masterImage.getWidth(), masterImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		
		Graphics2D graphics2d = blackWhite.createGraphics();
		graphics2d.drawImage(masterImage, 0, 0, null);
		
		outputFile = new File(inputFile.getParentFile() + File.separator + "output_bw.jpg");
		ImageIO.write(blackWhite, "jpg", outputFile);
		return outputFile;
	}
}
