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
@Table(name = "imagesequence")
public class ImageSequence implements Iterable<Image>, Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "imagesequence_id")
	private Long				id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "imagesequence_images", joinColumns = @JoinColumn(name = "imagesequence_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	private List<Image>			images;

	public ImageSequence() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImageSequence(List<Image> images) {
		this.images = images;
	}

	@Transient
	public Image get(int index) {
		return images.get(index);
	}

	public void set(int index, Image element) {
		images.set(index, element);
	}

	public int size() {
		return images.size();
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public Iterator<Image> iterator() {
		return images.iterator();
	}

}
