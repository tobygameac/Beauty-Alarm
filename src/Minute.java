import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Minute extends Needle {

	Minute(JPanel backPanel) {
		s = backPanel.getWidth();
		top = s / 12;
		center = s / 2;
	}

	@Override
	void whirl(double rad) {
		ms = (double) (System.currentTimeMillis() / 10 % 100);
		
		x[0] = center;
		y[0] = center;

		x[1] = center + 2;

		// Move every millisecond
		if (ms <= 50)
			y[1] = ms / 100D * center + top;
		else
			y[1] = (double) (100 - ms) / 100D * center + top;

		x[2] = center;
		y[2] = top;

		x[3] = center - 2;

		// Move every millisecond
		if (ms <= 50)
			y[3] = ms / 100D * center + top;
		else
			y[3] = (double) (100 - ms) / 100D * center + top;

		for (int i = 0; i < 4; i++) {
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
		for (int i = 1; i < 5; i++)
			needle.lineTo(x[i % 4], y[i % 4]);

		gradientPaint = new GradientPaint((float) x[0], (float) y[0],
				new Color(255, 255, 0, 200), (float) x[2], (float) y[2],
				new Color(0, 0, 255, 100));
		g2d.setPaint(gradientPaint);

		g2d.fill(needle);
	}

}
