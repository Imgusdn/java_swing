package java_swing.ex11;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PaintJPanelEx extends JFrame{
	public PaintJPanelEx() {
		setTitle("JPanel의 PaintComponent() 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new MyPanel());
		setSize(300,300);
		setVisible(true);
		
	}
	class MyPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.red);
			g.setFont(new Font("Arial",Font.ITALIC,30));
			g.drawString("Java Study",30,30);
			g.setColor(Color.BLUE);
			g.drawRect(10, 10, 200, 30);
			g.setColor((Color.GREEN));
			g.fillRoundRect(10, 50, 50, 50, 20, 20);
			g.setColor(Color.MAGENTA);
			g.fillArc(10, 100, 50, 50, 0, 270);
			g.setColor(Color.ORANGE);
			int []x= {30,10,30,60};
			int []y= {150,175,200,175};
			g.fillPolygon(x, y, 4);
			g.setColor(Color.red);
			g.fillArc(10, 200, 50, 50, 90, 120);
			g.setColor(Color.BLUE);
			g.fillArc(10, 200, 50, 50, 210, 120);
			g.setColor(Color.YELLOW);
			g.fillArc(10, 200, 50, 50, 330, 120);

		}
	}
	public static void main(String[] args) {
		new PaintJPanelEx();
	}

}
