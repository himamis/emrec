package edu.ubbcluj.recogniser.listener;

import java.util.List;
import java.util.Set;

import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.experiment.evaluation.classification.ClassificationResult;
import org.openimaj.experiment.validation.cross.CrossValidator;
import org.openimaj.experiment.validation.cross.GroupedLeaveOneOut;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

import edu.ubbcluj.emotion.dataset.AbstractDataset;
import edu.ubbcluj.emotion.dataset.FacialFeature;
import edu.ubbcluj.emotion.dataset.ck.CKESDataset;
import edu.ubbcluj.emotion.engine.EmotionRecogniser;
import edu.ubbcluj.emotion.engine.EmotionRecogniserProvider;
import edu.ubbcluj.emotion.engine.pca.PCAEmotionRecogniserProvider;
import edu.ubbcluj.emotion.model.Emotion;

public class VideoListener implements VideoDisplayListener<MBFImage> {
	
	private EmotionRecogniser recogniser;
	
	public VideoListener() {
		AbstractDataset<Emotion> dataset = new CKESDataset();
		EmotionRecogniserProvider engine = new PCAEmotionRecogniserProvider(50, dataset);
		//this.recogniser = engine.create(dataset);
	}

	private FKEFaceDetector		faceDetector	= new FKEFaceDetector();
	private AffineAligner		aligner			= new AffineAligner(50, 60, 0.400f);

	@Override
	public void afterUpdate(VideoDisplay<MBFImage> arg0) {
	}

	@Override
	public void beforeUpdate(MBFImage frame) {
		List<KEDetectedFace> faces = faceDetector.detectFaces(frame.flatten());
		if (faces.size() > 0) {
			KEDetectedFace face = faces.get(0);
			FImage alignedImage = aligner.align(face);
			alignedImage.normalise();
			DisplayUtilities.createNamedWindow("aligned");
			DisplayUtilities.displayName(alignedImage, "aligned");
			ClassificationResult<Emotion> result = recogniser.classify(alignedImage);
			Set<Emotion> prClasses = result.getPredictedClasses();
			System.out.println("CLASSIFICATION RES");
			for (Emotion em : prClasses) {
				System.out.println(em + " " + result.getConfidence(em));
			}
		}
	}

}
