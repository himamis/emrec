package edu.ubbcluj.emotion.ck.file.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.davidsoergel.conja.Function;
import com.davidsoergel.conja.Parallel;

import edu.ubbcluj.emotion.ck.file.info.FileResourceInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.database.file.manager.ImageSequenceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerException;
import edu.ubbcluj.emotion.model.Image;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.StringUtil;

public class FileImageSequenceManager implements ImageSequenceManager {

	private final ResourceInformationService	resInfo	= new FileResourceInformationService();

	@Override
	public ImageSequence loadImageSequence(final String folder, final String subject, final String sequence) {
		final String baseFolder = StringUtil.buildImagesFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] files = resInfo.getImages(folder, subject, sequence);

		final Image[] images = new Image[files.length];

		Parallel.forEach(files.length, new Function<Integer, Void>() {

			@Override
			public Void apply(final Integer arg0) {
				final File f = new File(baseFolder.concat(files[arg0]));
				try {
					images[arg0] = new Image(ImageIO.read(f));
				} catch (final IOException e) {
					throw new ResourceManagerException("Error loading image file " + f.getName(), e);
				}
				return null;
			}
		});
		final List<Image> imageList = new ArrayList<>(Arrays.asList(images));
		return new ImageSequence(imageList);
	}

	@Override
	public void saveImageSequence(final String folder, final ImageSequence imageSequence, final String subject, final String sequence) {
		final String dir = StringUtil.buildImagesFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String name = dir.concat(StringUtil.buildBaseFileName(subject, sequence));
		// ensure that the required directories exist
		new File(dir).mkdirs();

		Parallel.forEach(imageSequence.size(), new Function<Integer, Void>() {

			@Override
			public Void apply(final Integer arg0) {
				final Image image = imageSequence.get(arg0);
				final File f = new File(name.concat(String.format("%08d", arg0 + 1)).concat(Constants.SEQUENCE_FILE));
				try {
					ImageIO.write(image.getBufferedImage(), "png", f);
				} catch (final Exception e) {
					throw new ResourceManagerException("Error saving image file " + f.getName(), e);
				}
				return null;
			}
		});
	}

}
