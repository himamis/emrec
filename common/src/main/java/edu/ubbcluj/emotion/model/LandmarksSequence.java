package edu.ubbcluj.emotion.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "landmarkssequence")
public class LandmarksSequence implements Iterable<Landmarks>, Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "landmarkssequence_id")
	private Long				id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "landmarkssequence_landmarks", joinColumns = @JoinColumn(name = "landmarkssequence_id"), inverseJoinColumns = @JoinColumn(name = "landmarks_id"))
	private List<Landmarks>		landmarksList;

	public LandmarksSequence() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public LandmarksSequence(List<Landmarks> landmarks) {
		this.landmarksList = landmarks;
	}

	@Transient
	public Landmarks get(int frameNumber) {
		return landmarksList.get(frameNumber);
	}

	public int size() {
		return landmarksList.size();
	}

	public List<Landmarks> getLandmarks() {
		return landmarksList;
	}

	public void setLandmarks(List<Landmarks> landmarks) {
		this.landmarksList = landmarks;
	}

	@Override
	public Iterator<Landmarks> iterator() {
		return landmarksList.iterator();
	}
}
