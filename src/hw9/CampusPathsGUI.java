package hw9;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import hw8.CampusPathsModel;

public class CampusPathsGUI {
	private static final String TITLE = "Campus Paths Finder";
	private CampusPathsModel model;
	private JFrame frame;
	
	/**
	 * Constructs a campus paths GUI.
	 * 
	 * @param model model of the CampusPathsModel
	 * @requires model != null
	 */
	public CampusPathsGUI(CampusPathsModel model) {
		this.model = model;
		
		// initialize the frame
		frame = new JFrame(TITLE);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// initialize view and controller
		CampusPathsView view = new CampusPathsView(model);
		JScrollPane scrollPane = new JScrollPane(view);
		CampusPathsController controller = new CampusPathsController(model, view);
		controller.setPreferredSize(new Dimension(1024, 170));
		
		// set layout along y axis
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));		
		// add view and controller
		frame.add(scrollPane);
		frame.add(controller);
		
		// display
		frame.pack();
		frame.setVisible(true);
		
	}
	

}
