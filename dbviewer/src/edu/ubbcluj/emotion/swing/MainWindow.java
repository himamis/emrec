package edu.ubbcluj.emotion.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationService;
import edu.ubbcluj.emotion.database.file.manager.ResourceManager;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.kernel.DrawingThread;
import edu.ubbcluj.emotion.model.Sequence;
import edu.ubbcluj.emotion.model.Subject;

public class MainWindow extends JFrame {

	private static final long					serialVersionUID	= 1L;
	private static final String					NAE					= "-- NO EMOTION PROTOTYPE --";

	private Canvas								canvas				= new Canvas();
	private DrawingThread						th;

	private JPanel								rightPanel			= new JPanel();

	private JButton								openSubjectBtn		= new JButton("Open subject");
	private DefaultListModel<String>			animationListModel	= new DefaultListModel<>();
	private ListSelectionModel					selectionModel		= new DefaultListSelectionModel();
	private JList<String>						sequenceList		= new JList<>(animationListModel);

	private FacsTableModel						facsInfoTableModel	= new FacsTableModel();
	private JTable								facsInfoTable		= new JTable(facsInfoTableModel);

	private JLabel								emotionLabel		= new JLabel(NAE);

	private String[]							subjects;

	private List<Sequence>						animations			= new ArrayList<>();

	private String								selectedSubject;

	private String								folder;

	private final DatabaseInformationService	databaseInformationService;
	private final ResourceManager				resourceManager;

	public MainWindow(final DatabaseInformationFactory dif, final ResourceManagerFactory rem) {
		String[] databases = dif.listDatabases();
		Icon icon = UIManager.getIcon("OptionPane.questionIcon");
		folder = (String) JOptionPane.showInputDialog(this, "Please select the database you want to use: ", "DB", JOptionPane.PLAIN_MESSAGE, icon,
				databases, "ck+");
		if (folder == null || folder.length() == 0) {
			// close program if not database has been selected
			System.exit(0);
		}
		this.databaseInformationService = dif.getDatabaseInformationService(folder);
		this.resourceManager = rem.getResourceManager(folder);
		setUp();
		addActions();
	}

	private void setUp() {
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(openSubjectBtn, BorderLayout.NORTH);
		rightPanel.add(sequenceList, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

		add(emotionLabel, BorderLayout.NORTH);

		facsInfoTable.setPreferredScrollableViewportSize(new Dimension(700, 50));
		facsInfoTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(facsInfoTable);

		add(scrollPane, BorderLayout.SOUTH);
	}

	private void addActions() {
		openSubjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSubject = (String) JOptionPane.showInputDialog(MainWindow.this, "Select a subject", "Subject selection",
						JOptionPane.QUESTION_MESSAGE, null, getSubjects(), (Integer) 5);
				loadAnimations(selectedSubject);

			}
		});
		sequenceList.setSelectionModel(selectionModel);
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = sequenceList.getSelectedIndex();
				if (!e.getValueIsAdjusting() && index >= 0) {
					if (th != null) {
						th.interrupt();
						try {
							th.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					Sequence an = animations.get(index);
					canvas.setAnimation(an);
					th = new DrawingThread(canvas);
					th.start();
					if (an.getEmotion() != null) {
						emotionLabel.setText(an.getEmotion().toString());
					} else {
						emotionLabel.setText(NAE);
					}
					facsInfoTableModel.setAuList(an.getActionUnitList());
				}
			}
		});
	}

	private String[] getSubjects() {
		if (subjects == null) {
			subjects = databaseInformationService.getSubjects();
		}
		return subjects;
	}

	private void loadAnimations(String subject) {
		Subject sub = resourceManager.loadSubject(subject);

		List<String> list = new ArrayList<>();
		for (Sequence s : sub) {
			list.add(s.getSequence());
		}

		animations.clear();
		animationListModel.clear();
		for (String str : list) {
			animationListModel.addElement(str);
		}
		animations.addAll(sub.getSequences());
	}
}
