package edu.ubbcluj.emotion.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import edu.ubbcluj.emotion.model.MyPoint2D;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.util.Constants;

public class Canvas extends JPanel {

	private static final long		serialVersionUID	= 1L;

	private static int				WIDTH				= 640;
	private static int				HEIGHT				= 490;

	private ImageSequence			images;
	private LandmarksSequence		landmarks;
	private int						i;

	private boolean					showOnly			= false;
	private int						showIndex			= 0;

	private boolean					showMean			= false;
	private int[]					indices				= Constants.CEYELEFTCENTER;

	private final JCheckBox			checkBox			= new JCheckBox("Activate");
	private final SpinnerModel		model				= new SpinnerNumberModel(0, 0, 67, 1);
	private final JSpinner			number				= new JSpinner(model);

	private final JCheckBox			checkBox2			= new JCheckBox("Activate");
	private final JComboBox<String>	combobox			= new JComboBox<String>();

	public Canvas() {
		super();
		setSize(WIDTH, HEIGHT);

		// create show frame, for setting landmarks view
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

		// create another jframe for testing mean values
		j = new JFrame("Mean landmarks");
		for (String str : getMeanStrings()) {
			combobox.addItem(str);
		}
		j.getContentPane().setLayout(new FlowLayout());
		checkBox2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showMean = checkBox2.isSelected();
			}
		});
		combobox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String mean = (String) combobox.getSelectedItem();
				indices = getMeanValuesFromString(mean);

			}
		});
		j.getContentPane().add(checkBox2);
		j.getContentPane().add(combobox);
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
					MyPoint2D fl = l.get(showIndex);
					Integer x = ((Float) fl.getX()).intValue();
					Integer y = ((Float) fl.getY()).intValue();
					g.fillOval(x, y, 2, 2);
				} else if (showMean) {
					float[] mc = getMeanCoords(indices, l);
					g.fillOval((int) mc[0], (int) mc[1], 2, 2);
				} else {
					for (int i = 0; i < l.size(); i++) {
						MyPoint2D fl = l.get(i);
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

	private static float[] getMeanCoords(int[] indices, Landmarks landmarks) {
		float x = 0;
		float y = 0;
		for (int i = 0; i < indices.length; i++) {
			x += landmarks.get(indices[i]).getX();
			y += landmarks.get(indices[i]).getY();
		}
		x /= indices.length;
		y /= indices.length;
		return new float[] { x, y };
	}

	private int[] getMeanValuesFromString(String string) {
		switch (string) {
		case "CNOSEBRIDGE":
			return Constants.CNOSEBRIDGE;
		case "CNOSEMIDDLE":
			return Constants.CNOSEMIDDLE;
		case "CNOSERIGHT":
			return Constants.CNOSERIGHT;
		case "CNOSELEFT":
			return Constants.CNOSELEFT;
		case "MOUTHRIGHT":
			return Constants.MOUTHRIGHT;
		case "MOUTHLEFT":
			return Constants.MOUTHLEFT;
		case "MOUTHCENTER":
			return Constants.MOUTHCENTER;
		case "CEYELEFTCENTER":
			return Constants.CEYELEFTCENTER;
		case "CEYERIGHTCENTER":
			return Constants.CEYERIGHTCENTER;
		case "CEYELEFTLEFT":
			return Constants.CEYELEFTLEFT;
		case "CEYELEFTRIGHT":
			return Constants.CEYELEFTRIGHT;
		case "CEYERIGHTRIGHT":
			return Constants.CEYERIGHTRIGHT;
		case "CEYERIGHTLEFT":
			return Constants.CEYERIGHTLEFT;
		}
		return null;
	}

	private String[] getMeanStrings() {
		return new String[] { "CNOSEBRIDGE", "CNOSEMIDDLE", "CNOSERIGHT", "CNOSELEFT", "MOUTHRIGHT", "MOUTHLEFT", "MOUTHCENTER", "CEYELEFTCENTER",
				"CEYERIGHTCENTER", "CEYELEFTLEFT", "CEYELEFTRIGHT", "CEYERIGHTRIGHT", "CEYERIGHTLEFT" };
	}

}
