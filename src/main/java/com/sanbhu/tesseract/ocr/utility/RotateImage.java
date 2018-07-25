package com.sanbhu.tesseract.ocr.utility;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sanbhu.tesseract.ocr.bo.ImageInformation;

public class RotateImage {
	private static RotateImage rotateImage;

	private RotateImage() {
	}

	public static RotateImage getInstance() {
		if (rotateImage == null) {
			rotateImage = new RotateImage();
		}
		return rotateImage;
	}

	public File rotateImage(final File inputImageFile, final ImageInformation imageInformation) throws IOException {
		File outputFile = null;
		final BufferedImage inputBufferedImage = ImageIO.read(inputImageFile);
		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(90), inputBufferedImage.getWidth(),
				inputBufferedImage.getHeight());
		final AffineTransformOp transformOP = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		final BufferedImage destinationImage = transformOP.filter(inputBufferedImage, null);
		outputFile = new File(inputImageFile.getParentFile() + File.separator + "output_rotate.jpg");
		ImageIO.write(destinationImage, "jpg", outputFile);
		return outputFile;
	}
}
