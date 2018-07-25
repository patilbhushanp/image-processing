package com.sanbhu.tesseract.ocr.utility;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.sanbhu.tesseract.ocr.bo.ImageInformation;

public class GetImageInformation {
	private static GetImageInformation getImageInformation;

	private GetImageInformation() {
	}

	public static GetImageInformation getInstance() {
		if (getImageInformation == null) {
			getImageInformation = new GetImageInformation();
		}
		return getImageInformation;
	}

	public ImageInformation getImageInformation(final File inputImageFile)
			throws ImageProcessingException, IOException, MetadataException {
		ImageInformation imageInformation = null;
		Metadata metadata = ImageMetadataReader.readMetadata(inputImageFile);
		Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
		JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);

		int orientation = 1;
		try {
			orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
		} catch (MetadataException me) {
			System.err.println("Could not get orientation");
		}
		int width = jpegDirectory.getImageWidth();
		int height = jpegDirectory.getImageHeight();
		imageInformation = new ImageInformation(orientation, width, height);
		return imageInformation;
	}
}
