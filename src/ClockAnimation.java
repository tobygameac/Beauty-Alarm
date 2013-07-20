import java.applet.AudioClip;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ClockAnimation extends JPanel {
	private final double rad = Math.PI / 180D;
	private double r;// Width

	private long hour;
	private long minute;
	private long second;
	private String showTime;// Time in text

	// Container of needles
	private ArrayList<Needle> needle = new ArrayList<Needle>();

	// Container of background images
	private ArrayList<BufferedImage> image = new ArrayList<BufferedImage>();
	private int imageType = 0;// Type of background image

	private Alarm alarm = new Alarm();
	private AudioClip alarmAudio = java.applet.Applet.newAudioClip(getClass()
			.getResource("alarm.wav"));// Alarm music
	public Thread music = new Thread(new Runnable() {
		public void run() {
			alarmAudio.loop();// Replay loop
		}
	});

	private Graphics2D g2d;// Graphics of panel
	private GradientPaint gradientPaint;// Gradient

	public ClockAnimation(Clock frame) {
		setSize(frame.getSize());// Set size by frame
		setOpaque(false);
		setVisible(true);
		r = getWidth();

		// 3 needles of the clock
		needle.add(new Hour(this));
		needle.add(new Minute(this));
		needle.add(new Second(this));

		// 5 images of background
		for (int i = 0; i < 5; i++) {
			try {
				image.add(ImageIO.read(getClass().getResource(
						"Beauty" + i + ".png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void paintComponent(Graphics g) {
		// Without g.create() cannot do g.dispose()
		g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);// AA

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);// AA of text
		
		// Background
		g2d.drawImage(image.get(imageType), 0, 0, this);

		// Background of second (60 empty stars)
		g2d.setColor(new Color(255, 255, 128, 100));
		for (int i = 1; i <= 60; i++) {
			needle.get(2).whirl(i * 6D * rad);
			needle.get(2).draw(g2d);
		}

		// Clock border
		gradientPaint = new GradientPaint(0, 0, new Color(255, 255, 0, 200),
				(float) r, (float) r, new Color(255, 0, 0, 175));
		g2d.setPaint(gradientPaint);
		g2d.fill(new BasicStroke(10).createStrokedShape(new Ellipse2D.Double(0,
				0, r, r)));// Width 10

		// Circle of minute needle
		gradientPaint = new GradientPaint(0, 0, new Color(255, 128, 255, 100),
				(float) r, (float) r, new Color(0, 255, 0, 75));
		g2d.setPaint(gradientPaint);
		g2d.fill(new BasicStroke(5).createStrokedShape(new Ellipse2D.Double(
				r / 12D, r / 12D, r - r / 6D, r - r / 6D)));// Width 5

		// Circle of hour needle
		gradientPaint = new GradientPaint(0, 0, new Color(255, 255, 255, 100),
				(float) r, (float) r, new Color(255, 255, 255, 75));
		g2d.setPaint(gradientPaint);
		g2d.fill(new BasicStroke(3).createStrokedShape(new Ellipse2D.Double(
				r / 6D, r / 6D, r - r / 3D, r - r / 3D)));// Width 3

		// Show time in text
		hour = (System.currentTimeMillis() / (1000 * 60 * 60) + 8) % 24;
		minute = System.currentTimeMillis() / (1000 * 60) % 60;
		second = System.currentTimeMillis() / 1000 % 60;

		showTime = Long.toString(hour) + " : " + Long.toString(minute) + " : "
				+ Long.toString(second) + " (GMT + 8)";

		// Alarm on
		if (alarm.getOpen() && alarm.getHour() < 24 && alarm.getHour() >= 0
				&& alarm.getMinute() >= 0 && alarm.getMinute() < 60)
			showTime += (" Alarm : " + Long.toString(alarm.getHour()) + " : " + Long
					.toString(alarm.getMinute()));
		else
			showTime += " Alarm Off ";

		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.setFont(new Font("SketchFlow Print", Font.BOLD, 14));
		g2d.drawString(showTime, 30, (float) r / 1.5f);
		// Rotation & draw

		// Hour needle
		needle.get(0).whirl(hour * 30D * rad + minute / 60D * 30D * rad);
		needle.get(0).draw(g2d);

		// Minute needle
		needle.get(1).whirl(minute * 6D * rad + second / 60D * 6D * rad);
		needle.get(1).draw(g2d);

		// Second needle
		gradientPaint = new GradientPaint(0, 0, new Color(0, 0, 0, 150), 400,
				400, new Color(0, 0, 255, 150));
		g2d.setPaint(gradientPaint);
		for (int i = 1; i <= (second == 0 ? 60 : second); i++) {
			needle.get(2).whirl(i * 6D * rad);
			needle.get(2).draw(g2d);
		}

		// Destroy
		g2d.dispose();

		// Alarm check
		if ((alarm.getOpen() && hour == alarm.getHour() && minute == alarm
				.getMinute())) {
			music.run();
			alarm.setOpen(false);
		}

		// Keep painting every 50ms
		new Timer().schedule(new TimerTask() {
			public void run() {
				repaint();
			}
		}, 50);
	}

	public void setImageType(int type) {
		this.imageType = type;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(boolean on, long hour, long minute) {
		alarm.setOpen(on);
		alarm.setHour(hour);
		alarm.setMinute(minute);
	}

	public void closeAlarmMusic() {
		alarmAudio.stop();
	}

}
