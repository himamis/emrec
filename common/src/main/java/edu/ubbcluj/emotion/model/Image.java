package edu.ubbcluj.emotion.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

@Entity
@Table(name = "image")
public class Image implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private Long				id;

	@Column(name = "image_data")
	private int[]				image;

	@Column(name = "width")
	private int					width;

	@Column(name = "height")
	private int					height;

	@Transient
	private BufferedImage		bufferedImage;

	public Image() {
	}

	public Image(BufferedImage image) {
		this.bufferedImage = image;
		this.image = ImageUtilities.createMBFImage(image, true).toPackedARGBPixels();
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public BufferedImage getBufferedImage() {
		return ImageUtilities.createBufferedImage(new MBFImage(image, width, height, true));
	}
	
	public MBFImage getMBFImage() {
		return new MBFImage(image, width, height, true);
	}
	
	public FImage getFImage() {
		return getMBFImage().flatten();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int[] getImage() {
		return image;
	}

	public void setImage(int[] image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBufferedImage(BufferedImage image) {
		this.bufferedImage = image;
		this.image = ImageUtilities.createMBFImage(image, true).toPackedARGBPixels();
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public void setFImage(FImage image) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.image = image.toPackedARGBPixels();
	}

}
