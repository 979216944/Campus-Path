package hw9;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import hw8.CampusPathsModel;

/**
 * <b>CampusPathsController</b> is a GUI controller for displaying
 * drop-down menus, buttons and labels, and handles events.
 * 
 * @author Yanmeng Kong(Anny)
 *
 */
public class CampusPathsController extends JPanel {
	private static final long serialVersionUID = 1L;

	// model
//	private CampusPathsModel model;
	// GUI view
	private CampusPathsView view;
	// short to full names of all buildings
	private Map<String, String> shortToFull;
	private Set<String> full;
	// label of the origin building and destination building
	private JLabel originBuilding, destBuilding;
	// drop-down menu for select method
	private JComboBox<String> originSelections;
	private JComboBox<String> destSelections;
	// distance of the shortest path
	private JLabel distance;
	// for type method
	JTextField startName, destName;
	JRadioButton select, type;
	ButtonGroup group;
	
	/**
	 * Constructs a GUI controller.
	 * 
	 * @param model model of the CampusPathsModel
	 * @param view view of the CampusPathsView
	 * @requires model != null && view != null
	 * @effects create a controller to hold all interactive
	 * 	operations
	 */
	public CampusPathsController(CampusPathsModel model, CampusPathsView view) {
//		this.model = model;
		this.view = view;
		
		// initialize short to full names of all buildings
		shortToFull = new HashMap<String, String>(model.getShortToFull());
		full = new TreeSet<String>(model.getShortToFull().values());
		
		// set up panels
		JPanel methodPanel = new JPanel(new GridLayout(2, 1));
		JPanel selectionPanel = new JPanel(new GridBagLayout());
		JPanel typePanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		GridBagConstraints c = new GridBagConstraints();
		JPanel distancePanel = new JPanel(new GridLayout(1, 2));
		
		// set up labels and selections
		// for origins and destinations
		originBuilding = new JLabel("Origin building(red dot): ");
		destBuilding = new JLabel("Destination building(red dot & yellow star): ");
		// initialize to default
		originSelections = new JComboBox<String> (full.toArray(new String[0]));
		destSelections = new JComboBox<String> (full.toArray(new String[0]));		
		// add labels and selections
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 4;
		selectionPanel.add(originBuilding, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 4;
		selectionPanel.add(destBuilding, c);
		c.gridx = 5;
		c.gridy = 1;
		c.gridwidth = 5;
		selectionPanel.add(originSelections, c);
		c.gridx = 5;
		c.gridy = 2;
		c.gridwidth = 5;
		selectionPanel.add(destSelections, c);
		
		// set up method button
		select = new JRadioButton("Select building");
		type = new JRadioButton("Type building");
		group = new ButtonGroup();
		group.add(select);
		group.add(type);
		select.setSelected(true);
		select.setActionCommand("select");
		type.setActionCommand("type");
		SelectActionListener methodListener = new SelectActionListener();
		select.addActionListener(methodListener);
		type.addActionListener(methodListener);
		
		methodPanel.add(select);
		methodPanel.add(type);
		
		// set up type panel
		JLabel from = new JLabel("From:");
		startName = new JTextField(5);
		startName.setEnabled(false);
		startName.setActionCommand("setFrom");
		JLabel to = new JLabel("to:");
		destName = new JTextField(5);
		destName.setEnabled(false);
		destName.setActionCommand("setTo");
		SelectActionListener typeListener = new SelectActionListener();
		startName.addActionListener(typeListener);
		destName.addActionListener(typeListener);
		
		typePanel.add(from);
		typePanel.add(startName);
		typePanel.add(to);
		typePanel.add(destName);
		
		// set up buttons and listener
		// for find and reset
		JButton find = new JButton("Find path");
		JButton reset = new JButton("Clear");
		find.setActionCommand("find");
		reset.setActionCommand("reset");
		SelectActionListener buttonListener = new SelectActionListener();
		find.addActionListener(buttonListener);
		reset.addActionListener(buttonListener);
		
		JButton zoomIn = new JButton("+");
		JButton zoomOut = new JButton("-");
		zoomIn.setActionCommand("zoom in");
		zoomOut.setActionCommand("zoom out");
		zoomIn.addActionListener(buttonListener);
		zoomOut.addActionListener(buttonListener);
		
		// add buttons
		buttonPanel.add(find);
		buttonPanel.add(reset);
		buttonPanel.add(zoomIn);
		buttonPanel.add(zoomOut);

		// set up distance labels
		JLabel distanceLabel = new JLabel("Total distance: ");
		distance = new JLabel();
		distancePanel.add(distanceLabel);
		distancePanel.add(distance);
		
		// add panels to controller panel
		this.add(methodPanel);
		this.add(selectionPanel);
		this.add(typePanel);
		this.add(buttonPanel);
		this.add(distancePanel);
	}

	/**
	 * Handles events associated with button clicks.
	 * 
	 * @author Yanmeng Kong(Anny)
	 *
	 */
	private class SelectActionListener implements ActionListener {
		/**
		 * handles action commands:
		 * - find path
		 * - reset
		 * 
		 * @param e the event created by the button click
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equals("find")) {
				// find shorteset path
				Boolean isShort;
				String originBuilding, destBuilding;
				if (select.isSelected()) {
					originBuilding = (String) originSelections.getSelectedItem();
					destBuilding = destSelections.getSelectedItem().toString();
					isShort = false;
				} else {
					originBuilding = startName.getText();
					destBuilding = destName.getText();
					isShort = true;
					if (!shortToFull.containsKey(originBuilding)) {
						startName.setText("Invalid");
					}
					if (!shortToFull.containsKey(destBuilding)) {
						destName.setText("Invalid");
					}
				}
				if (!isShort || (shortToFull.containsKey(originBuilding) 
						&& shortToFull.containsKey(destBuilding))) {
					Double d = view.getShortestPath(originBuilding, destBuilding, isShort);
					distance.setText(String.format(" %.0f feet", d));
				}
			} else if (action.equals("reset")){
				// reset
				originSelections.setSelectedIndex(0);
				destSelections.setSelectedIndex(0);
				startName.setText("");
				destName.setText("");
				distance.setText("");
				view.reset();
			} else if (action.equals("zoom in")) {
				view.zoomIn();
			} else if (action.equals("zoom out")) {
				view.zoomOut();
			} else if (action.equals("select")) {
				// method select chosen
				originSelections.setEnabled(true);
				destSelections.setEnabled(true);
				startName.setEnabled(false);
				destName.setEnabled(false);
			} else if (action.equals("type")) {
				// method type chosen
				originSelections.setEnabled(false);
				destSelections.setEnabled(false);
				startName.setEnabled(true);
				destName.setEnabled(true);
			}
		}
	}
}
