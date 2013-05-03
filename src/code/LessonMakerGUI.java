package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class LessonMakerGUI extends JFrame implements ActionListener, ItemListener {

	//Parameters
	int MAX_IMAGES = 4;
	
	//MimetypesFileTypeMap allows checking of type of file
	MimetypesFileTypeMap mtftp;
	
	//Components of GUI
	JRadioButtonMenuItem[] radioButtons = new JRadioButtonMenuItem[MAX_IMAGES]; 
	JTextField[] phidgetOutputValues = new JTextField[MAX_IMAGES];
	JButton[] imageChooserButtons = new JButton[MAX_IMAGES];
	JButton startLessonButton = new JButton("Start Lesson");
	ButtonGroup numberOfImages = new ButtonGroup();
	
	// These panels help the gui look better
	JPanel[] chooserPanels = new JPanel[MAX_IMAGES];
	JPanel checkBoxPanel = new JPanel();
	
	// These are not implemented yet but shall be shown grayed out
	JButton saveLessonButton = new JButton("Save Lesson");
	JButton loadLessonButton = new JButton("Load Lesson");
	JButton loadResultsButton = new JButton("Load Previous Results");
	JButton addSensorButton = new JButton("Add sensor");
	
	// The images
	BufferedImage[] images = new BufferedImage[MAX_IMAGES];
	
	// The file chooser
	JFileChooser imageSelect = new JFileChooser();
	
	public LessonMakerGUI() {
		// Set up a file type map so that only images may be loaded into the program
		mtftp = new MimetypesFileTypeMap();
		mtftp.addMimeTypes("image jpg tif jpeg bmp gif png JPG TIF JPEG BMP GIF PNG");
		
		// Set title of window, default close operation, layout
		setTitle("Disco Lesson Maker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		// Set layout of checkBox Jpanel
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
		
		// Tell the file chooser to only allow selection of files
		imageSelect.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		// Initiate array values for checkboxes, buttons, and output phidget value fields
		// Also adds action listeners
		// Also adds components to jpanels and sets chooser panels layouts
		for (int i = 0; i < MAX_IMAGES; i++) {
			radioButtons[i] = new JRadioButtonMenuItem(Integer.toString(i + 1));
			radioButtons[i].addItemListener(this);
			checkBoxPanel.add(radioButtons[i]);
			numberOfImages.add(radioButtons[i]);
			
			phidgetOutputValues[i] = new JTextField("None");
			
			imageChooserButtons[i] = new JButton("Choose image " + Integer.toString(i + 1));
			imageChooserButtons[i].addActionListener(this);
			
			chooserPanels[i] = new JPanel();
			chooserPanels[i].setLayout(new BoxLayout(chooserPanels[i], BoxLayout.X_AXIS));
			chooserPanels[i].add(imageChooserButtons[i]);
			chooserPanels[i].add(phidgetOutputValues[i]);
		}
		
		radioButtons[MAX_IMAGES - 1].setSelected(true);
		
		// Add panels to content pane
		getContentPane().add(checkBoxPanel);
		
		for (int i = 0; i < MAX_IMAGES; i++) {
			getContentPane().add(chooserPanels[i]);
		}
		
		// Add implemented buttons
		startLessonButton.addActionListener(this);
		getContentPane().add(startLessonButton);
		
		// Add unimplemented methods, and set them so they are not enabled
		getContentPane().add(addSensorButton);
		addSensorButton.setEnabled(false);
		getContentPane().add(saveLessonButton);
		saveLessonButton.setEnabled(false);
		getContentPane().add(loadLessonButton);
		loadLessonButton.setEnabled(false);
		getContentPane().add(loadResultsButton);
		loadResultsButton.setEnabled(false);
		
		pack();
		setVisible(true);
	}
	
	
	// Have to make sure all buttons are accounted for or will get index out of
	// bounds in last else
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startLessonButton) {
			System.out.println("Starting lesson");
			int i = 0;
			while (!radioButtons[i].isSelected()) {
				i++;
			}
			int[] outputValues = new int[i + 1];
			for (int a = 0; a <= i; a++) {
				try {
					int val = Integer.parseInt(phidgetOutputValues[a].getText());
					if (val < 9 && val > 0) {
						outputValues[a] = val;
					}
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			ParticipantGUI pGUI = new ParticipantGUI(images, outputValues);
			pGUI.setVisible(true);
			
		} else if (e.getSource() == addSensorButton) {
			
		} else if (e.getSource() == saveLessonButton) {
			
		} else if (e.getSource() == loadLessonButton) {
			
		} else if (e.getSource() == loadResultsButton) {
			
		} else {
			int i = 0;
			while (e.getSource() != imageChooserButtons[i]) {
				i++;
			}
			
			int returnVal = imageSelect.showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File imageFile = imageSelect.getSelectedFile();
				String mimeType = mtftp.getContentType(imageFile);
				
				if (mimeType.substring(0, 5).equalsIgnoreCase("image")) {
					try {
						BufferedImage image = ImageIO.read(imageFile);
						images[i] = image;
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
			
		}
		
	}

	
	// This listens for changes to the radio buttons and disables and enables buttons and 
	// fields accordingly
	@Override
	public void itemStateChanged(ItemEvent e) {
		int i = 0;
		while (!(radioButtons[i].equals(e.getSource()))) {
			i++;
		}
		for (int a = 0; a < MAX_IMAGES; a++) {
			if (a <= i) {
				phidgetOutputValues[a].setEditable(true);
				imageChooserButtons[a].setEnabled(true);
			} else {
				phidgetOutputValues[a].setEditable(false);
				imageChooserButtons[a].setEnabled(false);
			}
		}
		
	}

}
