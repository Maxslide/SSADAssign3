/*
 * PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

/**
 *  constructs a prototype PinSetter GUI
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;


public class PinSetterView implements PinsetterObserver {


    private Vector pinVect = new Vector ( );
    private JPanel firstRoll;
    private JPanel secondRoll;

    /**
     * Constructs a Pin Setter GUI displaying which roll it is with
     * yellow boxes along the top (1 box for first roll, 2 boxes for second)
     * and displays the pins as numbers in this format:
     *
     *                7   8   9   10
     *                  4   5   6
     *                    2   3
     *                      1
     *
     */


	private JFrame frame;

    public PinSetterView ( int laneNum ) {

	frame = new JFrame ( "Lane " + laneNum + ":" );

	Container cpanel = frame.getContentPane ( );

	JPanel pins = new JPanel ( );

	pins.setLayout ( new GridLayout ( 4, 7 ) );

	//********************Top of GUI indicates first or second roll

	JPanel top = new JPanel ( );

	firstRoll = new JPanel ( );
	firstRoll.setBackground( Color.yellow );

	secondRoll = new JPanel ( );
	secondRoll.setBackground ( Color.black );

	top.add ( firstRoll, BorderLayout.WEST );

	top.add ( secondRoll, BorderLayout.EAST );

	//******************************************************************

	//**********************Grid of the pins**************************

	//fourth row
	int j = 0;
	for(int i=7;i<=13;i++) {

		if(i%2==0){
			j+=1;
			pins.add(new JPanel());
		}
		else{
			JPanel piece = new JPanel();
			JLabel label = new JLabel(Integer.toString(i-j));
			piece.add(label);
			pinVect.add(label);
			pins.add(piece);
		}
	}
	//third row
	j=-1;
	for(int i=3;i<=8;i++){
		if(i%2==1) {
			j+=1;
			pins.add(new JPanel());
		}
		else{
			JPanel piece = new JPanel();
			JLabel label = new JLabel(Integer.toString(i-j));
			piece.add(label);
			pinVect.add(label);
			pins.add(piece);
		}
	}
	//second row
	pins.add(new JPanel());
	pins.add(new JPanel());
	for(int i=2;i<=3;i++){
		JPanel piece = new JPanel();
		JLabel label = new JLabel(Integer.toString(i));
		piece.add(label);
		pinVect.add(label);
		pins.add(new JPanel());
		pins.add(piece);
	}
	pins.add(new JPanel());
	pins.add(new JPanel());
	//first row
	int i = 1;
	pins.add(new JPanel());
	pins.add(new JPanel());
	pins.add(new JPanel());
	JPanel piece = new JPanel();
	JLabel label = new JLabel(Integer.toString(i));
	piece.add(label);
	pinVect.add(label);
	pins.add(piece);
	pins.add(new JPanel());
	pins.add(new JPanel());
	pins.add(new JPanel());

	top.setBackground ( Color.black );

	cpanel.add ( top, BorderLayout.NORTH );

	pins.setBackground ( Color.black );
	pins.setForeground ( Color.yellow );

	cpanel.add ( pins, BorderLayout.CENTER );

	frame.pack();

    }

    /**
     * This method receives a pinsetter event.  The event is the current
     * state of the PinSetter and the method changes how the GUI looks
     * accordingly.  When pins are "knocked down" the corresponding label
     * is grayed out.  When it is the second roll, it is indicated by the
     * appearance of a second yellow box at the top.
     *
     * @param e    The state of the pinsetter is sent in this event.
     */


    public void receivePinsetterEvent(PinsetterEvent pe){
	if ( !(pe.isFoulCommited()) ) {
	    	JLabel tempPin = new JLabel ( );
	    	for ( int c = 0; c < 10; c++ ) {
				boolean pin = pe.pinKnockedDown ( c );
				tempPin = (JLabel)pinVect.get ( c );
				if ( pin ) {
		    		tempPin.setForeground ( Color.lightGray );
				}
	    	}
    	}
		if ( pe.getThrowNumber() == 1 ) {
	   		 secondRoll.setBackground ( Color.yellow );
		}
	if ( pe.pinsDownOnThisThrow() == -1) {
		for ( int i = 0; i != 10; i++){
			((JLabel)pinVect.get(i)).setForeground(Color.black);
		}
		secondRoll.setBackground( Color.black);
	}
    }

    public void show() {
    	frame.show();
    }

    public void hide() {
    	frame.hide();
    }

    public static void main ( String args [ ] ) {
		PinSetterView pg = new PinSetterView ( 1 );
    }

}
