import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Second extends Needle {

	// Star
	private double xPoints[] = { 9.16666667, 11.16666667, 18.16666667, 12.16666667,
			13.83333333, 9.16666667, 4.5, 6.16666667, 0.16666667, 7.16666667 };
	private double yPoints[] = { 0, 6, 6, 9, 16, 12, 16, 9, 6, 6 };

	Second(JPanel backPanel) {
		s = backPanel.getWidth();
		center = s / 2;
	}

	@Override
	void whirl(double rad) {
		for (int i = 0; i < 10; i++) {
			x[i] = xPoints[i] + center - xPoints[0];
			y[i] = yPoints[i];
			double tempX = x[i] - center;
			double tempY = y[i] - center;
			x[i] = Math.cos(rad) * tempX - Math.sin(rad) * tempY + center;
			y[i] = Math.sin(rad) * tempX + Math.cos(rad) * tempY + center;
		}
	}

	@Override
	void draw(Graphics2D g2d) {
		needle.reset();// Initial

		// Trace every point
		needle.moveTo(x[0], y[0]);
		for (int i = 1; i < 11; i++)
			needle.lineTo(x[i % 10], y[i % 10]);

		g2d.fill(needle);
	}

}
