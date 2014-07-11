package edu.ubbcluj.emotion.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.ubbcluj.emotion.model.ImageSequence;
import edu.ubbcluj.emotion.model.Landmarks;
import edu.ubbcluj.emotion.model.LandmarksSequence;
import edu.ubbcluj.emotion.model.Point2D;
import edu.ubbcluj.emotion.model.Sequence;

public class Canvas extends JPanel {

	private static final long	serialVersionUID	= 1L;

	private static int			WIDTH				= 640;
	private static int			HEIGHT				= 490;

	private ImageSequence		images;
	private LandmarksSequence	landmarks;
	private int					i;

	private boolean				showOnly			= false;
	private int					showIndex			= 0;

	private final JCheckBox		checkBox			= new JCheckBox("Show only");
	private final SpinnerModel	model				= new SpinnerNumberModel(0, 0, 67, 1);
	private final JSpinner		number				= new JSpinner(model);

	public Canvas() {
		super();
		setSize(WIDTH, HEIGHT);
		JFrame j = new JFrame("Set landmarks");
		j.getContentPane().setLayout(new FlowLayout());
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showOnly = checkBox.isSelected();
			}
		});
		number.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				showIndex = (int) number.getValue();
			}
		});
		j.getContentPane().add(checkBox);
		j.getContentPane().add(number);
		j.pack();
		j.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		if (images != null) {
			g.drawImage(images.get(i).getBufferedImage(), 0, 0, null);
			if (landmarks != null) {
				g.setColor(Color.RED);
				Landmarks l = landmarks.get(i);
				if (showOnly) {
					Point2D fl = l.get(showIndex);
					Integer x = ((Float) fl.getX()).intValue();
					Integer y = ((Float) fl.getY()).intValue();
					g.fillOval(x, y, 2, 2);
				} else {
					for (int i = 0; i < l.size(); i++) {
						Point2D fl = l.get(i);
						Integer x = ((Float) fl.getX()).intValue();
						Integer y = ((Float) fl.getY()).intValue();
						g.fillOval(x, y, 2, 2);
					}
				}
			}
		}
	}

	public void setAnimation(Sequence animation) {
		this.images = animation.getImageSequence();
		this.landmarks = animation.getLandmarksSequence();
		i = 0;
	}

	public void setImages(ImageSequence images) {
		this.images = images;
		i = 0;
	}

	public void setLandmarks(LandmarksSequence landmarks) {
		this.landmarks = landmarks;
	}

	public void nextImage() {
		if (images != null) {
			if (i < images.size() - 1)
				i++;
			else
				i = 0;
		} else
			i = 0;
	}

}
