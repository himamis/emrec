package edu.ubbcluj.emotion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "point2d")
public class Point2D implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue
	@Column(name = "point2d_id")
	private Long				id;

	@Column(name = "x")
	private float				x;

	@Column(name = "y")
	private float				y;

	public Point2D() {
	}

	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void fromPoint2D(java.awt.geom.Point2D.Float p) {
		setX(p.x);
		setY(p.y);
	}
	
	public java.awt.geom.Point2D.Float toPoint2D() {
		return new java.awt.geom.Point2D.Float(x, y);
	}

}
