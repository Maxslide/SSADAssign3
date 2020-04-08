/* AddPartyView.java
 *
 *  Version:
 * 		 $Id$
 *
 *  Revisions:
 * 		$Log: AddPartyView.java,v $
 * 		Revision 1.7  2003/02/20 02:05:53  ???
 * 		Fixed addPatron so that duplicates won't be created.
 *
 * 		Revision 1.6  2003/02/09 20:52:46  ???
 * 		Added comments.
 *
 * 		Revision 1.5  2003/02/02 17:42:09  ???
 * 		Made updates to migrate to observer model.
 *
 * 		Revision 1.4  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 *
 *
 */

/**
 * Class for GUI components need to add a party
 *
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.text.*;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 *
 */

public class LowScorer{

    private int maxSize;

    private JFrame win;
    private JButton addPatron, newPatron, remPatron, finished;
    private JList partyList, allBowlers;
    private Vector party, bowlerdb;
    private Integer top;

    private ControlDeskView controlDesk;

    private String name;

    public LowScorer() {

        this.controlDesk = controlDesk;
//        maxSize = max;

        win = new JFrame("Lowest Scorer");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Name Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Name"));

        Vector names = new Vector();
        Vector score = new Vector();

        try {
            top=100000;
            name="NOT FOUND";
            File myFile = new File("SCOREHISTORY.DAT");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] scoredata = data.split("\t");
                if(Integer.parseInt(scoredata[2])<top) {
                    top = Integer.parseInt(scoredata[2]);
                    score.clear();
                    score.add(top);
                    names.clear();
                    names.add(scoredata[0]);
                }
                else if(Integer.parseInt(scoredata[2])==top){
                    score.add(top);
                    names.add(scoredata[0]);
                }
            }
            myReader.close();
        }catch (FileNotFoundException e){
            System.out.println("An error occured");
//            e.printStackTrace();
            score.add("ERROR 404");
            names.add("ERROR 404");
        }

        partyList = new JList(names);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(2);
        JScrollPane partyPane = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        // Score Panel
        JPanel bowlerPanel = new JPanel();
        bowlerPanel.setLayout(new FlowLayout());
        bowlerPanel.setBorder(new TitledBorder("Score"));


        allBowlers = new JList(score);
        allBowlers.setVisibleRowCount(2);
        allBowlers.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(allBowlers);
        bowlerPanel.add(bowlerPane);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(bowlerPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }
}
