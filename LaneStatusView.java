/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;

	private JLabel curBowler, foul, pinsDown;
	private ButtonCommon viewLane;
	private ButtonCommon viewPinSetter, maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		viewLane = new ButtonCommon("View Lane");
		// JPanel viewLanePanel = new JPanel();
		// viewLanePanel.setLayout(new FlowLayout());
		// viewLane.addActionListener(this);
		// viewLanePanel.add(viewLane);

		viewPinSetter = new ButtonCommon("Pinsetter");
		// JPanel viewPinSetterPanel = new JPanel();
		// viewPinSetterPanel.setLayout(new FlowLayout());
		// viewPinSetter.addActionListener(this);
		// viewPinSetterPanel.add(viewPinSetter);

		maintenance = new ButtonCommon("     ");
		maintenance.button.setBackground( Color.GREEN );
		// JPanel maintenancePanel = new JPanel();
		// maintenancePanel.setLayout(new FlowLayout());
		// maintenance.addActionListener(this);
		// maintenancePanel.add(maintenance);

		viewLane.button.setEnabled( false );
		viewPinSetter.button.setEnabled( false );
		viewLane.Button_Panel(this, buttonPanel);
		viewPinSetter.Button_Panel(this, buttonPanel);
		maintenance.Button_Panel(this, buttonPanel);
		// buttonPanel.add(viewLanePanel);
		// buttonPanel.add(viewPinSetterPanel);
		// buttonPanel.add(maintenancePanel);

		jp.add( cLabel );
		jp.add( curBowler );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}

	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) { 
			if (e.getSource().equals(viewPinSetter.button)) {
				if ( psShowing == false ) {
					psv.show();
					psShowing=true;
				} else{
					psv.hide();
					psShowing=false;
				}
			}
			else if (e.getSource().equals(viewLane.button)) {
				if ( laneShowing == false ) {
					lv.show();
					laneShowing=true;
				} else {
					lv.hide();
					laneShowing=false;
				}
			}
			else if (e.getSource().equals(maintenance.button)) {
				lane.unPauseGame();
				maintenance.button.setBackground( Color.GREEN );
			}
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.button.setBackground( Color.RED );
		}	
		if ( lane.isPartyAssigned() == false ) {
			viewLane.button.setEnabled( false );
			viewPinSetter.button.setEnabled( false );
		} else {
			viewLane.button.setEnabled( true );
			viewPinSetter.button.setEnabled( true );
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}
