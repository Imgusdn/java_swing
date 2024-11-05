package java_swing.ex10;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TenLabelsFrame extends JFrame {
	private JLabel [] gameLabels = new JLabel[10];
	private int nextPressed = 0;
	
	public TenLabelsFrame() {
		super("Ten 레이블 클릭");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		for(int i=0; i<gameLabels.length; i++) {
			Container c = getContentPane();
			gameLabels[i] = new JLabel(Integer.toString(i));
			gameLabels[i].setForeground(Color.magenta);
			c.add(gameLabels[i]);
			
			gameLabels[i].addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					JLabel la = (JLabel)e.getSource();
					if(Integer.parseInt(la.getText())==nextPressed) {
						nextPressed++;
						if(nextPressed == 10) {
							nextPressed = 0;
							
							configure();
						}
						else
							la.setVisible(true);
					}
					
				}
				

			});
		}
		setSize(350,350);
		setVisible(true);
		configure();
	}
	private void configure() {
		Container c = getContentPane();
		for(int i=0; i<gameLabels.length; i++) {
			int xBound = c.getWidth() - gameLabels[i].getWidth();
			int yBound = c.getHeight() - gameLabels[i].getHeight();
			int x = (int)(Math.random()*xBound);
			int y = (int)(Math.random()*yBound);
		       gameLabels[i].setLocation(x,y);
		         gameLabels[i].setSize(15,15);
		         gameLabels[i].setVisible(true);

		}
	}
	public static void main(String[] args) {
		new TenLabelsFrame();
	}
}
