package edu.ubbcluj.emotion.database.file.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ubbcluj.emotion.database.file.info.ResourceInformationService;
import edu.ubbcluj.emotion.model.ActionUnitSet;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;

public class ResourceManager {

	public static boolean							USE_CACHE			= true;

	public static final int							LANDMARKS			= 1;
	public static final int							EMOTION				= 2;
	public static final int							ACTION_UNITS		= 4;
	public static final int							IMAGE_SEQUENCE		= 8;
	public static final int							ALL					= 15;

	private final String							folder;

	private final ActionUnitListManager				actionUnitListManager;
	private final EmotionManager					emotionManager;
	private final LandmarksManager					landmarksManager;
	private final ImageSequenceManager				imageSequenceManager;

	private final ResourceInformationService		resInfoService;

	private final Map<String, ImageSequence>		imageSequenceCache	= new HashMap<>();
	private final Map<String, Emotion>				emotionCache		= new HashMap<>();
	private final Map<String, LandmarksSequence>	landmarksCache		= new HashMap<>();
	private final Map<String, ActionUnitSet>		actionUnitListCache	= new HashMap<>();
	private final Map<String, Sequence>				sequenceCache		= new HashMap<>();
	private final Map<String, Subject>				subjectCache		= new HashMap<>();

	public ResourceManager(final String folder, final ActionUnitListManager actionUnitListManager, final EmotionManager emotionManager,
			final LandmarksManager landmarksManager, final ImageSequenceManager imageSequenceManager, final ResourceInformationService resInfoService) {
		this.folder = folder;
		this.actionUnitListManager = actionUnitListManager;
		this.emotionManager = emotionManager;
		this.landmarksManager = landmarksManager;
		this.imageSequenceManager = imageSequenceManager;
		this.resInfoService = resInfoService;
	}

	public Subject loadSubject(final String subject) {
		return loadSubject(subject, ALL);
	}

	public Subject loadSubject(final String subject, final int flags) {
		Subject subj = null;
		if (USE_CACHE) {
			if ((subj = subjectCache.get(subject)) == null) {
				subj = new Subject(subject);
				subjectCache.put(subject, subj);
			}
		} else {
			subj = new Subject(subject);
		}
		final String[] sequences = resInfoService.getSequences(folder, subject);
		final List<Sequence> sequenceList = new ArrayList<>();
		for (final String sequence : sequences) {
			final Sequence seq = loadSequence(subject, sequence, flags);
			sequenceList.add(seq);
		}
		subj.setSequences(sequenceList);
		return subj;
	}

	public Sequence loadSequence(final String subject, final String sequence) {
		return loadSequence(subject, sequence, ALL);
	}

	public Sequence loadSequence(final String subject, final String sequence, final int flags) {
		final String key = createKey(subject, sequence);
		Sequence seq = null;

		if (USE_CACHE) {
			if ((seq = sequenceCache.get(key)) == null) {
				seq = new Sequence(sequence);
				sequenceCache.put(key, seq);
			}
		} else {
			seq = new Sequence(sequence);
		}
		if ((flags & LANDMARKS) != 0 && seq.getLandmarksSequence() == null) {
			final LandmarksSequence landmarks = loadLandmarks(subject, sequence);
			seq.setLandmarksSequence(landmarks);
		}
		if ((flags & EMOTION) != 0 && seq.getEmotion() == null) {
			final Emotion emotion = loadEmotion(subject, sequence);
			seq.setEmotion(emotion);
		}
		if ((flags & ACTION_UNITS) != 0 && seq.getActionUnitList() == null) {
			final ActionUnitSet actionUnitList = loadActionUnitList(subject, sequence);
			seq.setActionUnitList(actionUnitList);
		}
		if ((flags & IMAGE_SEQUENCE) != 0 && seq.getImageSequence() == null) {
			final ImageSequence imageSequence = loadImageSequence(subject, sequence);
			seq.setImageSequence(imageSequence);
		}

		return seq;
	}

	public ImageSequence loadImageSequence(final String subject, final String sequence) {
		final String key = createKey(subject, sequence);
		ImageSequence imageSequence = null;
		if (USE_CACHE) {
			if ((imageSequence = imageSequenceCache.get(key)) == null) {
				imageSequence = imageSequenceManager.loadImageSequence(folder, subject, sequence);
				imageSequenceCache.put(key, imageSequence);
			}
		} else {
			imageSequence = imageSequenceManager.loadImageSequence(folder, subject, sequence);
		}
		return imageSequence;
	}

	public Emotion loadEmotion(final String subject, final String sequence) {
		final String key = createKey(subject, sequence);
		Emotion emotion = null;
		if (USE_CACHE) {
			if ((emotion = emotionCache.get(key)) == null) {
				emotion = emotionManager.loadEmotion(folder, subject, sequence);
				emotionCache.put(key, emotion);
			}
		} else {
			emotion = emotionManager.loadEmotion(folder, subject, sequence);
		}
		return emotion;
	}

	public LandmarksSequence loadLandmarks(final String subject, final String sequence) {
		final String key = createKey(subject, sequence);
		LandmarksSequence landmarks = null;
		if (USE_CACHE) {
			if ((landmarks = landmarksCache.get(key)) == null) {
				landmarks = landmarksManager.loadLandmarks(folder, subject, sequence);
				landmarksCache.put(key, landmarks);
			}
		} else {
			landmarks = landmarksManager.loadLandmarks(folder, subject, sequence);
		}
		return landmarks;
	}

	public ActionUnitSet loadActionUnitList(final String subject, final String sequence) {
		final String key = createKey(subject, sequence);
		ActionUnitSet actionUnitList = null;
		if (USE_CACHE) {
			if ((actionUnitList = actionUnitListCache.get(key)) == null) {
				actionUnitList = actionUnitListManager.loadActionUnitList(folder, subject, sequence);
				actionUnitListCache.put(key, actionUnitList);
			}
		} else {
			actionUnitList = actionUnitListManager.loadActionUnitList(folder, subject, sequence);
		}
		return actionUnitList;
	}

	private String createKey(final String subject, final String sequence) {
		return subject.concat(sequence);
	}

}
