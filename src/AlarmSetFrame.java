import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class AlarmSetFrame extends JFrame {
	// Font use in frame
	private final Font font = new Font("SketchFlow Print", Font.BOLD, 12);
	
	// Container
	private JPanel contentPane;
	
	//Input field
	private JLabel hourLabel = new JLabel("Hour   :");
	private JLabel minuteLabel = new JLabel("Minute :");
	private JTextField hourField;
	private JTextField minuteField;
	private JButton okButton = new JButton("OK");

	// Alarm setting
	private JButton alarmOn = new JButton("Off");
	private boolean on = false;
	private long hour = 7;
	private long minute = 30;

	// Location setting
	private Point p1;
	private Point p2;
	private Point center;

	public AlarmSetFrame(final Clock clock) {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setVisible(false);
		setTitle("Setting");
		try {
			setIconImage(ImageIO.read(getClass().getResource("Icon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Record the points (use on move)
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				p1 = e.getPoint();
				center = getLocation();
			}
		});

		// Move by drag
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				p2 = e.getPoint();
				center.x += (p2.x - p1.x);
				center.y += (p2.y - p1.y);
				setLocation(center);
			}
		});
		setBackground(new Color(255, 255, 128, 50));
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Alarm setting",
				TitledBorder.LEFT, TitledBorder.TOP, font, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Alarm open button
		alarmOn.setBackground(Color.red);
		alarmOn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				on = !on;// Change color every click
				if (on) {
					alarmOn.setBackground(Color.green);
					alarmOn.setText("On");
				} else {
					alarmOn.setBackground(Color.red);
					alarmOn.setText("Off");
				}
				clock.getClockAnimation().setAlarm(on, hour, minute);
			}
		});
		alarmOn.setBounds(5, 20, 100, 25);
		alarmOn.setFont(font);
		alarmOn.setBorder(null);
		alarmOn.setFocusable(false);
		contentPane.add(alarmOn);

		// Hour label
		hourLabel.setBounds(60, 145, 60, 20);
		hourLabel.setFont(font);
		contentPane.add(hourLabel);

		// Minute label
		minuteLabel.setBounds(60, 175, 60, 20);
		minuteLabel.setFont(font);
		contentPane.add(minuteLabel);

		// Hour field
		hourField = new JTextField("7");
		hourField.setBounds(120, 145, 20, 20);
		hourField.setColumns(10);
		hourField.setFont(font);
		contentPane.add(hourField);

		// Minute field
		minuteField = new JTextField("30");
		minuteField.setBounds(120, 175, 20, 20);
		minuteField.setColumns(10);
		minuteField.setFont(font);
		contentPane.add(minuteField);

		// OK button
		okButton.setBackground(Color.red);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hour = Long.valueOf(hourField.getText());
				minute = Long.valueOf(minuteField.getText());
				clock.getClockAnimation().setAlarm(on, hour, minute);
				setVisible(false);
			}
		});
		okButton.setBounds(150, 175, 60, 20);
		okButton.setFont(font);
		okButton.setBorder(null);
		okButton.setFocusable(false);
		contentPane.add(okButton);

	}
}
