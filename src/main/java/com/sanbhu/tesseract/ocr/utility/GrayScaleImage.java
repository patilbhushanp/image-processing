package com.sanbhu.tesseract.ocr.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayScaleImage {
	public final static GrayScaleImage grayScaleImage = new GrayScaleImage();

	private GrayScaleImage() {
	}

	public static GrayScaleImage getInstance() {
		return grayScaleImage;
	}

	public File convertImage(File inputFile) throws IOException {
		File outputFile = null;
		BufferedImage bufferedImage = ImageIO.read(inputFile);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = bufferedImage.getRGB(x, y);
				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;
				int avg = (r + g + b) / 3;
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;
				bufferedImage.setRGB(x, y, p);
			}
		}

		outputFile = new File(inputFile.getParentFile() + File.separator + "output.jpg");
		ImageIO.write(bufferedImage, "jpg", outputFile);
		return outputFile;
	}
}
