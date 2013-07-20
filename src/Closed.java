import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Closed extends JFrame {
	// Font use in frame
	private final Font font = new Font("SketchFlow Print", Font.BOLD, 12);
	
	// Container
	private JPanel contentPane;

	private final JLabel closedLabel = new JLabel("Closed?");
	private final JButton okButton = new JButton("OK");
	private final JButton cancelButton = new JButton("Cancel");

	// Location setting
	private Point p1;
	private Point p2;
	private Point center;

	public Closed() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setVisible(false);
		setTitle("Bye!");
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
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 27:// Escape
					setVisible(false);
					break;
				}
			}
		});
		setBackground(new Color(255, 255, 255, 50));
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Text
		closedLabel.setBackground(Color.red);
		closedLabel.setBounds(60, 30, 60, 20);
		closedLabel.setFont(font);
		closedLabel.setBorder(null);
		closedLabel.setFocusable(false);
		contentPane.add(closedLabel);

		// OK button
		okButton.setBackground(Color.red);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		okButton.setBounds(0, 70, 60, 20);
		okButton.setFont(font);
		okButton.setBorder(null);
		okButton.setFocusable(false);
		contentPane.add(okButton);

		// Cancel button
		cancelButton.setBackground(Color.red);
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		cancelButton.setBounds(90, 70, 60, 20);
		cancelButton.setFont(font);
		cancelButton.setBorder(null);
		cancelButton.setFocusable(false);
		contentPane.add(cancelButton);
	}
}
