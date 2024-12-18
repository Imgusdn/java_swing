package ThreadEx;

import java.awt.Font;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DigitalClockFrame extends JFrame {
	public DigitalClockFrame() {
		setTitle("디지털 시계 만들기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().add(new DigitalClockLabel());
		setSize(250,200);
		setVisible(true);
	}
	class DigitalClockLabel extends JLabel implements Runnable{
		public DigitalClockLabel() {
			setText(makeClockText());
			setFont(new Font("TimesRoman", Font.ITALIC, 50));
			setHorizontalAlignment(JLabel.CENTER);
			new Thread(this).start();
		}
		public String makeClockText() {
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int min = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			
			String clockText = Integer.toString(hour);
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(min));
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(second));
			
			return clockText;
			
		}
		public void run() {
			while(true) {
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {return;}
				setText(makeClockText());
			}
		}
	}
	public static void main(String[] args) {
		new DigitalClockFrame();
		}
	}