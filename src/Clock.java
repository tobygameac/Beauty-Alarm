import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

@SuppressWarnings("serial")
public class Clock extends JFrame {
	// Clock version
	private final double version = 1.12429;

	// Font use in clock
	private final Font font = new Font("SketchFlow Print", Font.BOLD, 12);

	// Container
	private JPanel contentPane;

	// Clock animation & voice
	private JPanel clockAnimation;

	// Location setting
	private Point p1;
	private Point p2;
	private Point center;
	private boolean lock = false; // Movable

	// System tray & icon
	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private PopupMenu popupMenu;// Pop menu of tray icon

	// Choose girl
	private JComboBox<String> girlBox;

	// Alarm
	private JButton alarmSetButton;
	private JButton closeAlarm;
	private AlarmSetFrame alramSet = new AlarmSetFrame(this);

	// Close frame
	private JLabel close;// Close button
	private Closed closed = new Closed();// Close frame

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clock frame = new Clock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Clock() {
		setFocusableWindowState(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setBounds(
				java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200,
				java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 200,
				400, 400);// Center of the screen
		setShape(new Ellipse2D.Double(0, 0, 400, 400));// Circle windows

		// Create the system tray & icon
		try {
			systemTray = SystemTray.getSystemTray();// Create a system tray

			trayIcon = new TrayIcon(ImageIO.read(getClass().getResource(
					"Icon.png")));
			trayIcon.setToolTip("Highhand Clock " + version);

			popupMenu = new PopupMenu();
			popupMenu.setFont(font);

			popupMenu.add("Lock / Unlock");
			popupMenu.add("Exit");

			// Add event on lock
			popupMenu.getItem(0).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lock = !lock;
				}
			});

			// Add event on exit
			popupMenu.getItem(1).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Show the close frame
					closed.setBounds(getLocationOnScreen().x + 200 - 75,
							getLocationOnScreen().y + 200 - 50, 150, 100);
					// Center of frame
					closed.setVisible(true);
				}
			});

			// Add pop menu on this icon
			trayIcon.setPopupMenu(popupMenu);

			// Add event on this icon
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					switch (e.getButton()) {
					case 1: // Left click control frame
						setVisible(!isVisible());
						break;
					case 2:// Right click control pop menu
						popupMenu.show(null, e.getX(), e.getY());
						break;
					}
				}
			});
			systemTray.add(trayIcon); // Add icon on this system tray
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
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
				if (!lock) {
					p2 = e.getPoint();
					center.x += (p2.x - p1.x);
					center.y += (p2.y - p1.y);
					setLocation(center);
				}
			}
		});

		// Container
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Clock
		clockAnimation = new ClockAnimation(this);
		clockAnimation.setLayout(null);
		contentPane.add(clockAnimation);

		// Choose girl
		girlBox = new JComboBox<String>();
		girlBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				((ClockAnimation) clockAnimation).setImageType(girlBox
						.getSelectedIndex());
			}
		});
		girlBox.setModel(new DefaultComboBoxModel<String>(new String[] { "³Â­Ü¼~",
				"·R¥Ð¥Ñ", "»a¤«ªÅ", "§Æ§Ó·R³¥", "´Â¬üÁJ­»" }));
		girlBox.setBounds(160, 190, 80, 20);
		girlBox.setBackground(new Color(255, 255, 200));
		girlBox.setFont(new Font("Meiryo UI", Font.BOLD, 12));
		girlBox.setFocusable(false);
		clockAnimation.add(girlBox);

		// Alarm button
		alarmSetButton = new JButton("Alarm");
		alarmSetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				alramSet.setBounds(getLocationOnScreen().x + 200 - 125,
						getLocationOnScreen().y + 200 - 125, 250, 250);
				// Center of frame
				alramSet.setVisible(true);
			}
		});
		alarmSetButton.setBackground(new Color(128, 255, 255, 0));
		alarmSetButton.setBounds(60, 320, 90, 20);
		alarmSetButton.setFont(font);
		alarmSetButton.setBorder(null);
		alarmSetButton.setFocusable(false);
		alarmSetButton.setOpaque(false);
		clockAnimation.add(alarmSetButton);

		// Alarm voice stop button
		closeAlarm = new JButton("Stop!!");
		closeAlarm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				((ClockAnimation) clockAnimation).closeAlarmMusic();
			}
		});
		closeAlarm.setBackground(new Color(128, 255, 255, 0));
		closeAlarm.setBounds(250, 320, 90, 20);
		closeAlarm.setFont(font);
		closeAlarm.setBorder(null);
		closeAlarm.setFocusable(false);
		closeAlarm.setOpaque(false);
		clockAnimation.add(closeAlarm);

		// Close button
		close = new JLabel("X");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Show the close frame
				closed.setBounds(getLocationOnScreen().x + 200 - 75,
						getLocationOnScreen().y + 200 - 50, 150, 100);
				// Center of frame
				closed.setVisible(true);
			}
		});
		close.setForeground(Color.RED);
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setBounds(180, 370, 40, 10);
		close.setFont(font);
		clockAnimation.add(close);
	}

	public ClockAnimation getClockAnimation() {
		return (ClockAnimation) this.clockAnimation;
	}
}
