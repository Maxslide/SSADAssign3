/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class for representation of the control desk
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

	private ButtonCommon addParty, finished,topscorer,lowscorer,individual,latest, ResumeGame;
	private JButton assign;
	private JFrame win;
	private JList partyList;
	
	/** The maximum  number of members in a party */
	private int maxMembers;
	
	private ControlDesk controlDesk;

	/**
	 * Displays a GUI representation of the ControlDesk
	 *
	 */

	public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

		this.controlDesk = controlDesk;
		this.maxMembers = maxMembers;
		int numLanes = controlDesk.getNumLanes();

		win = new JFrame("Control Desk");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Controls Panel
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(3, 1));
		controlsPanel.setBorder(new TitledBorder("Controls"));

		addParty = new ButtonCommon("Add Party");
		// JPanel addPartyPanel = new JPanel();
		// addPartyPanel.setLayout(new FlowLayout());
		// addParty.addActionListener(this);
		// addPartyPanel.add(addParty);
		// controlsPanel.add(addPartyPanel);
		addParty.Button_Panel(this, controlsPanel);

		assign = new JButton("Assign Lanes");
		JPanel assignPanel = new JPanel();
		assignPanel.setLayout(new FlowLayout());
		assign.addActionListener(this);
		assignPanel.add(assign);
//		controlsPanel.add(assignPanel);

		latest = new ButtonCommon("Latest 5");
		latest.Button_Panel(this,controlsPanel);
		topscorer = new ButtonCommon("Top Scorer");
		topscorer.Button_Panel(this, controlsPanel);
		individual = new ButtonCommon("Individual Best");
		individual.Button_Panel(this, controlsPanel);
		lowscorer = new ButtonCommon("Low Scorer");
		lowscorer.Button_Panel(this, controlsPanel);
		finished = new ButtonCommon("Finished");
		finished.Button_Panel(this, controlsPanel);
		ResumeGame = new ButtonCommon("Resume Game");
		ResumeGame.Button_Panel(this, controlsPanel);
		

		// Lane Status Panel
		JPanel laneStatusPanel = new JPanel();
		laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
		laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

		HashSet lanes=controlDesk.getLanes();
		Iterator it = lanes.iterator();
		int laneCount=0;
		while (it.hasNext()) {
			Lane curLane = (Lane) it.next();
			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));
			curLane.subscribe(laneStat);
			((Pinsetter)curLane.getPinsetter()).subscribe(laneStat);
			JPanel lanePanel = laneStat.showLane();
			lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount ));
			laneStatusPanel.add(lanePanel);
		}

		// Party Queue Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector empty = new Vector();
		empty.add("(Empty)");

		partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(10);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);
		//		partyPanel.add(partyList);

		// Clean up main panel
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		/* Close program when this window closes */
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}

	/**
	 * Handler for actionEvents
	 *
	 * @param e	the ActionEvent that triggered the handler
	 *
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addParty.button)) {
			AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
		}
		else if (e.getSource().equals(assign)) {
			controlDesk.assignLane();
		}
		else if(e.getSource().equals(ResumeGame.button))
		{
			ResumeOldGame gameresume = new ResumeOldGame(controlDesk);		
		}
		else if (e.getSource().equals(finished.button)) {
			win.hide();
			System.exit(0);
		}
		else if (e.getSource().equals(topscorer.button)){
//			AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
			TopScorer topScorer = new TopScorer();
		}
		else if(e.getSource().equals(lowscorer.button)){
			LowScorer lowScorer = new LowScorer();
		}
		else if(e.getSource().equals(individual.button)){
			Individual individual = new Individual(this, maxMembers);
		}
		else if(e.getSource().equals(latest.button)){
			Latest latest = new Latest(this, maxMembers);
		}
	}

	/**
	 * Receive a new party from andPartyView.
	 *
	 * @param addPartyView	the AddPartyView that is providing a new party
	 *
	 */

	public void updateAddParty(AddPartyView addPartyView) {
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 *
	 * @param ce	the ControlDeskEvent that triggered the handler
	 *
	 */

	public void receiveControlDeskEvent(ControlDeskEvent ce) {
		partyList.setListData(((Vector) ce.getPartyQueue()));
	}
}
