package edu.ubbcluj.emotion.ck.file.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.davidsoergel.conja.Function;
import com.davidsoergel.conja.Parallel;

import edu.ubbcluj.emotion.ck.file.info.FileResourceInformationService;
import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.database.file.manager.LandmarksManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerException;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.MyPoint2D;
import edu.ubbcluj.emotion.util.Constants;
import edu.ubbcluj.emotion.util.StringUtil;

public class FileLandmarksManager implements LandmarksManager {

	private final ResourceInformationService	resInfo	= new FileResourceInformationService();

	@Override
	public LandmarksSequence loadLandmarks(final String folder, final String subject, final String sequence) {
		final String baseFolder = StringUtil.buildLandmarksFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String[] filess = resInfo.getLandmarks(folder, subject, sequence);
		final String[] files = (filess == null) ? new String[0] : filess;

		// final Vector<List<Point2D.Float>> landmarks = new Vector<>();

		final Landmarks[] landmarks = new Landmarks[files.length];

		Parallel.forEach(files.length, new Function<Integer, Void>() {

			@Override
			public Void apply(final Integer arg0) {
				final File f = new File(baseFolder.concat(files[arg0]));
				try (Scanner scanner = new Scanner(f)) {
					final List<MyPoint2D> list = new LinkedList<>();
					while (scanner.hasNextFloat()) {
						list.add(new MyPoint2D(scanner.nextFloat(), scanner.nextFloat()));
					}
					landmarks[arg0] = new Landmarks(list);
				} catch (final IOException e) {
					throw new ResourceManagerException("Error loading landmark index " + (arg0 + 1), e);
				}
				return null;
			}
		});

		final List<Landmarks> landmarksList = new ArrayList<>(Arrays.asList(landmarks));
		return new LandmarksSequence(landmarksList);
	}

	@Override
	public void saveLandmarks(final String folder, final LandmarksSequence landmarks, final String subject, final String sequence) {
		final String dir = StringUtil.buildLandmarksFolderName(folder).concat(StringUtil.buildSubjectFolder(subject))
				.concat(StringUtil.buildSequenceFolder(sequence));
		final String name = dir.concat(StringUtil.buildBaseFileName(subject, sequence));
		new File(dir).mkdirs();

		Parallel.forEach(landmarks.size(), new Function<Integer, Void>() {

			@Override
			public Void apply(final Integer arg0) {
				final File f = new File(name.concat(String.format("%08d", arg0 + 1)).concat(Constants.LANDMARKS_FILE));
				final Landmarks landmarksList = landmarks.get(arg0);
				try {
					final BufferedWriter writer = new BufferedWriter(new FileWriter(f));
					for (final MyPoint2D lm : landmarksList) {
						writer.write(String.valueOf(lm.getX()));
						writer.write(" ");
						writer.write(String.valueOf(lm.getY()));
						writer.write("\n");
					}
					writer.close();
				} catch (final IOException e) {
					throw new ResourceManagerException("Error saving landmark index " + (arg0 + 1), e);
				}
				return null;
			}
		});

	}

}
