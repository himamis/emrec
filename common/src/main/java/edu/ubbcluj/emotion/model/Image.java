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
	private double[]			imageData;

	@Column(name = "width")
	private int					width;

	@Column(name = "height")
	private int					height;

	@Transient
	private FImage				image;

	@Transient
	private BufferedImage		bufferedImage;

	public Image() {
	}

	public Image(FImage image) {
		setImage(image);
	}

	public MBFImage getMBFImage() {
		return new MBFImage(image);
	}

	public FImage getImage() {
		return image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double[] getImageData() {
		return imageData;
	}

	public void setImage(FImage image) {
		this.image = image;
		this.bufferedImage = null;
		this.imageData = image.getDoublePixelVector();
		this.width = image.getWidth();
		this.height = image.getHeight();
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

	public BufferedImage getBufferedImage() {
		if (bufferedImage == null) {
			this.bufferedImage = ImageUtilities.createBufferedImage(image);
		}
		return bufferedImage;
	}
	
	public void fromBufferedImage(BufferedImage img) {
		FImage fromImg = ImageUtilities.createFImage(img);
		setImage(fromImg);
	}

}
