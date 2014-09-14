package edu.ubbcluj.emotion.database.file.loader;

import java.util.List;

import org.openimaj.image.FImage;

public interface ResourceLoader {

	public List<FImage> getImages(ImageFilter filter);
}
