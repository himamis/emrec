package edu.ubbcluj.emotion.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sequence")
public class Sequence implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "sequence_id")
	private Long				id;

	@Column(name = "sequence")
	private String				sequence;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "imagesequence_id")
	private ImageSequence		imageSequence;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "landmarkssequence_id")
	private LandmarksSequence	landmarksSequence;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "actionunitlist_id")
	private ActionUnitList		actionUnitList;

	@Enumerated(EnumType.STRING)
	private Emotion				emotion;

	public Sequence() {
	}

	public Sequence(final String sequence) {
		this.sequence = sequence;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public ImageSequence getImageSequence() {
		return imageSequence;
	}

	public void setImageSequence(ImageSequence imageSequence) {
		this.imageSequence = imageSequence;
	}

	public LandmarksSequence getLandmarksSequence() {
		return landmarksSequence;
	}

	public void setLandmarksSequence(LandmarksSequence landmarksSequence) {
		this.landmarksSequence = landmarksSequence;
	}

	public ActionUnitList getActionUnitList() {
		return actionUnitList;
	}

	public void setActionUnitList(ActionUnitList actionUnitList) {
		this.actionUnitList = actionUnitList;
	}

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

}
