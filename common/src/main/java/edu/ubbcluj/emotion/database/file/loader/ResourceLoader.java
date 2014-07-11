package edu.ubbcluj.emotion.database.file.loader;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ResourceLoader {

	public List<BufferedImage> getImages(ImageFilter filter);
}
