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
@Table(name = "landmarks")
public class Landmarks implements Iterable<MyPoint2D>, Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "landmarks_id")
	private Long				id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "landmarks_point2ds", joinColumns = @JoinColumn(name = "landmarks_id"), inverseJoinColumns = @JoinColumn(name = "point2d_id"))
	private List<MyPoint2D>		landmarks;

	public Landmarks() {
	}

	public Landmarks(List<MyPoint2D> landmarks) {
		this.landmarks = landmarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<MyPoint2D> getLandmarks() {
		return landmarks;
	}

	public void setLandmarks(List<MyPoint2D> landmarks) {
		this.landmarks = landmarks;
	}

	@Override
	public Iterator<MyPoint2D> iterator() {
		return landmarks.iterator();
	}

	public int size() {
		return landmarks.size();
	}

	@Transient
	public MyPoint2D get(int index) {
		return landmarks.get(index);
	}

}
