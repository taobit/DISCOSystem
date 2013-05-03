package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ParticipantGUI extends JFrame implements MouseListener {

	BufferedImage[] images;
	int[] outputs;
	JLabel[] labels;
	
	PhidgetInterface phidget = new PhidgetInterface();
	
	public ParticipantGUI(BufferedImage[] ims, int[] outs) {
		images = ims;
		outputs = outs;
		labels = new JLabel[outputs.length];
		
		this.addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent we) {
				for (int i = 0; i < 8; i++) {
					phidget.turnOffPort(i);
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel rowOne = new JPanel();
		rowOne.setLayout(new BoxLayout(rowOne, BoxLayout.X_AXIS));
		
		JPanel rowTwo = new JPanel();
		rowTwo.setLayout(new BoxLayout(rowTwo, BoxLayout.X_AXIS));
		System.out.println("" + outputs.length);
		for (int i = 0; i < outputs.length; i++) {
			try {
					ImageIcon imageIcon = new ImageIcon(images[i].getScaledInstance(350, 350, BufferedImage.SCALE_DEFAULT));
					
					labels[i] = new JLabel(imageIcon);
					labels[i].addMouseListener(this);
					
					if (i < 2) {
						rowOne.add(labels[i]);
					} else {
						rowTwo.add(labels[i]);
					}
			} catch (NullPointerException e) {
				
			}
		}
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(rowOne);
		getContentPane().add(rowTwo);
		
		pack();
		setVisible(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int i = 0;
		while(!e.getSource().equals(labels[i])) {
			i++;
		}
		
		try {
			phidget.turnOnPort(outputs[i]);
		} catch (NullPointerException npe) {
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
