package com.sanbhu.tesseract.ocr.utility;

import java.awt.Color;
import java.awt.Graphics2D;
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
		final AffineTransform transform = getExifTransformation(imageInformation);
		final AffineTransformOp transformOP = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		final BufferedImage inputBufferedImage = ImageIO.read(inputImageFile);
		BufferedImage destinationImage = transformOP.createCompatibleDestImage(inputBufferedImage,
				(inputBufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY) ? inputBufferedImage.getColorModel()
						: null);
		Graphics2D graphics2d = destinationImage.createGraphics();
		graphics2d.setBackground(Color.WHITE);
		graphics2d.clearRect(0, 0, destinationImage.getWidth(), destinationImage.getHeight());
		destinationImage = transformOP.filter(inputBufferedImage, destinationImage);
		outputFile = new File(inputImageFile.getParentFile() + File.separator + "output_rotate.jpg");
		ImageIO.write(destinationImage, "jpg", outputFile);
		return outputFile;
	}

	private AffineTransform getExifTransformation(ImageInformation imageInformation) {
		final AffineTransform transform = new AffineTransform();
		switch (imageInformation.orientation) {
		case 1:
			break;
		case 2: // Flip X
			transform.scale(-1.0, 1.0);
			transform.translate(-imageInformation.width, 0);
			break;
		case 3: // PI rotation
			transform.translate(imageInformation.width, imageInformation.height);
			transform.rotate(Math.PI);
			break;
		case 4: // Flip Y
			transform.scale(1.0, -1.0);
			transform.translate(0, -imageInformation.height);
			break;
		case 5: // - PI/2 and Flip X
			transform.rotate(-Math.PI / 2);
			transform.scale(-1.0, 1.0);
			break;
		case 6: // -PI/2 and -width
			transform.translate(imageInformation.height, 0);
			transform.rotate(Math.PI / 2);
			break;
		case 7: // PI/2 and Flip
			transform.scale(-1.0, 1.0);
			transform.translate(-imageInformation.height, 0);
			transform.translate(0, imageInformation.width);
			transform.rotate(3 * Math.PI / 2);
			break;
		case 8: // PI / 2
			transform.translate(0, imageInformation.width);
			transform.rotate(3 * Math.PI / 2);
			break;
		}
		return transform;
	}
}
