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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
import java.text.*;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 *
 */

public class Individual implements ActionListener, ListSelectionListener {

    private int maxSize;

    private JFrame win;
    private ButtonCommon check;
    private JList partyList, allBowlers;
    private Vector party, bowlerdb;
    private Integer lock;

    private ControlDeskView controlDesk;

    private String selectedNick, selectedMember;

    public Individual(ControlDeskView controlDesk, int max) {

        this.controlDesk = controlDesk;
        maxSize = max;

        win = new JFrame("Individual Best");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 3));

        // Party Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Best Score"));

        party = new Vector();
        Vector empty = new Vector();
        empty.add("(Empty)");

        partyList = new JList(empty);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(3);
        partyList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(partyList);
        //        partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        // Bowler Database
        JPanel bowlerPanel = new JPanel();
        bowlerPanel.setLayout(new FlowLayout());
        bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

        try {
            bowlerdb = new Vector(BowlerFile.getBowlers());
        } catch (Exception e) {
            System.err.println("File Error");
            bowlerdb = new Vector();
        }
        allBowlers = new JList(bowlerdb);
        allBowlers.setVisibleRowCount(3);
        allBowlers.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(allBowlers);
        bowlerPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allBowlers.addListSelectionListener(this);
        bowlerPanel.add(bowlerPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);
        // ButtonCommon addbutton = new ButtonCommon();
        check = new ButtonCommon("Check");
        check.Button_Panel(this, buttonPanel);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(bowlerPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(check.button)) {
            if (selectedNick != null) {
                int top = 0;
                try {
//                    this.name = "NOT FOUND";
                    File var5 = new File("SCOREHISTORY.DAT");
                    Scanner var6 = new Scanner(var5);

                    while(var6.hasNextLine()) {
                        String var7 = var6.nextLine();
                        String[] var8 = var7.split("\t");
                        if (Integer.parseInt(var8[2]) > top && selectedNick.equals(var8[0])) {
                            top = Integer.parseInt(var8[2]);
                        }
                    }

                    var6.close();
                    party.clear();
                    party.add(top);
                    partyList.setListData(party);

                } catch (FileNotFoundException var9) {
                    System.out.println("An error occured");
                    party.add("ERROR 404");
                    partyList.setListData(party);

                }

            }
        }

    }

    /**
     * Handler for List actions
     * @param e the ListActionEvent that triggered the handler
     */

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(allBowlers)) {
            selectedNick =
                    ((String) ((JList) e.getSource()).getSelectedValue());
        }
        if (e.getSource().equals(partyList)) {
            selectedMember =
                    ((String) ((JList) e.getSource()).getSelectedValue());
        }
    }

}
