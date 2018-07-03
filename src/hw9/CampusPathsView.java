package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hw8.CampusPathsModel;
import hw8.Point;

/**
 * <b>CampusPathsView</b> is a GUI view for displaying the campus map and path
 * between two buildings.
 * 
 * @author Yanmeng Kong(Anny)
 *
 */
public class CampusPathsView extends JPanel {
	private static final long serialVersionUID = 1L;
	// image
	private static final String MAP_IMG = "src/hw8/data/campus_map.jpg";
	private static final double MAX_SCALE = 1.5;
	private static final double MIN_SCALE = 0.3;
	private static final double DEFAULT_SCALE = 0.5;
	private static final double ZOOM_PERCENT = 0.05;

	// model
	private CampusPathsModel model;
	// shortest path
	private Map<Point, Double> shortestPath;
	// points for the path
	private List<Point> points;
	// map image
	private BufferedImage map;
	// width and height of display
	private int width;
	private int height;
	// scale
	private double scale;

	/**
	 * Constructs view GUI.
	 * 
	 * @param model
	 *            model of the CampusPathsModel
	 * @requires model != null
	 * @effects creates a view to hold campus map
	 */
	public CampusPathsView(CampusPathsModel model) {
		this.model = model;

		// initialize shortest paths and points
		shortestPath = null;
		points = null;
		loadMap();
		// initialize width and height
		width = 1024;
		height = 768;
		this.setPreferredSize(new Dimension(width, height));
		scale = DEFAULT_SCALE;
	}

	/**
	 * Load the map image.
	 */
	private void loadMap() {
		// load image
		try {
			map = ImageIO.read(new File(MAP_IMG));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the shortest path from one building to another.
	 * 
	 * @param origin
	 *            name of the origin building
	 * @param dest
	 *            name of the destination building
	 * @return the shortest path from one building to another.
	 */
	public Double getShortestPath(String origin, String dest, boolean isShort) {
		// full name needs to be converted to short names
		if (!isShort) {
			origin = model.getShortNameOf(origin);
			dest = model.getShortNameOf(dest);
		}
		Point originPoint = model.getBuildingOf(origin);
		Point destPoint = model.getBuildingOf(dest);

		shortestPath = model.findShortestPath(originPoint, destPoint);
		points = new ArrayList<Point>(shortestPath.keySet());
		Double distance = shortestPath.get(points.get(points.size() - 1));
		repaint();
		return distance;
	}

	/**
	 * Resets the view.
	 */
	public void reset() {
		shortestPath = null;
		repaint();
	}

	/**
	 * Set the scale
	 * 
	 * @param s the value scale set to if in a reasonable range
	 */
	public void setScale(double s) {
		scale = s > MAX_SCALE ? MAX_SCALE : (s < MIN_SCALE ? MIN_SCALE : s);
		revalidate();
		repaint();
	}

	/**
	 * Zoom in the map
	 */
	public void zoomIn() {
		if (scale < MAX_SCALE) {
			scale += ZOOM_PERCENT;
		} else {
			scale = MAX_SCALE;
		}
		// update the scroll pane
		revalidate();
		repaint();
	}

	/**
	 * Zoom out the map
	 */
	public void zoomOut() {
		if (scale > MIN_SCALE) {
			scale -= ZOOM_PERCENT;
		} else {
			scale = MIN_SCALE;
		}
		// update the scroll pane
		revalidate();
		repaint();
	}

	/**
	 * Gets the preferred size for scroll pane
	 * @return the dimension with new width and height
	 */
	@Override
	public Dimension getPreferredSize() {
		int w = (int) (scale * map.getWidth());
		int h = (int) (scale * map.getHeight());
		return new Dimension(w, h);
	}

	/**
	 * Paints the shortest path.
	 * 
	 * @param g2 the Graphics context
	 * @requires g != null
	 * @modifies this
	 * @effects repaints this with updates.
	 */
	private void paintPath(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		width = getWidth();
		height = getHeight();
		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();

		// draw campus
		double x = (width - scale * mapWidth) / 2;
		double y = (height - scale * mapHeight) / 2;
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.scale(scale, scale);
		g2.drawRenderedImage(map, at);

		if (shortestPath != null) {
			points = new ArrayList<Point>(shortestPath.keySet());

			// set up origin x and y
			Point originPoint = points.get(0);
			int originX = (int) Math.round(originPoint.getX() * scale);
			int originY = (int) Math.round(originPoint.getY() * scale);
			// initialize previous x and y
			int prevX = originX;
			int prevY = originY;
			// draw the shortest path
			g2.setColor(Color.YELLOW);
			for (Point point : points) {
				int destX = (int) Math.round(point.getX() * scale);
				int destY = (int) Math.round(point.getY() * scale);
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(prevX, prevY, destX, destY);
				// update
				prevX = destX;
				prevY = destY;
			}

			// mark the origin building and destination building
			g2.setColor(Color.RED);
			g2.fillOval(originX - 5, originY - 5, 10, 10);
			g2.fillOval(prevX - 5, prevY - 5, 10, 10);
			g2.setColor(Color.YELLOW);
			g2.drawString("★", prevX - 2, prevY);

		}
	}

	/**
	 * Repaint the simulation by requesting each item to repaint itself.
	 * 
	 * @param g the graphics context
	 * @requires g != null
	 * @modifies this
	 * @effects repaints with updated shortest path
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		paintPath(g2);
	}

	// /**
	// * Resizes the scroll pane
	// *
	// * @return coordinates of origin and destination points
	// */
	// public double[] getResize() {
	//// points = new ArrayList<Point>(shortestPath.keySet());
	// width = getWidth();
	// height = getHeight();
	// Point originPoint = points.get(0);
	// Point destPoint = points.get(points.size() - 1);
	// int originX = (int) Math.round(originPoint.getX());
	// int originY = (int) Math.round(originPoint.getY());
	// int destX = (int) Math.round(destPoint.getX());
	// int destY = (int) Math.round(destPoint.getY());
	// int deltaX = Math.abs(destX - originX);
	// int deltaY = Math.abs(destY - originY);
	//
	// int ulX1 = originX;
	// int ulY1 = originY;
	// int ulX2 = destX;
	// int ulY2 = destY;
	//
	// if (deltaX > deltaY) {
	// setScale(width * 1.0 / deltaX - zoomPercent);
	// } else {
	// setScale(height * 1.0 / deltaY - zoomPercent);
	// }
	//
	//// double [] result = new double[] {Math.min(Math.abs(ulX1),
	// Math.abs(ulX2)) * scale,
	//// Math.min(Math.abs(ulY1), Math.abs(ulY2)) * scale};
	// System.out.println(map.getWidth() * scale);
	// double [] result = new double[] {scale * map.getWidth(), scale *
	// map.getHeight()};
	// return result;
	// }
}
