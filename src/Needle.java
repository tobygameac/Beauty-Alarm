import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public abstract class Needle {
	protected double[] x = new double[10];
	protected double[] y = new double[10];// All points of needle

	protected double top;// the smallest y of needle
	protected double s;// Width of panel
	protected double center;// Center of panel
	
	protected double ms;// Millisecond(use on move)

	protected Path2D needle = new Path2D.Double();// Linked every point
	protected GradientPaint gradientPaint;// Gradient

	abstract void whirl(double rad);

	abstract void draw(Graphics2D g2d);
}
