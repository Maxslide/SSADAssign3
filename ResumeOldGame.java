
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class ResumeOldGame implements ActionListener, ListSelectionListener {
    private int maxSize;

    private JFrame win;
    private ButtonCommon check, low;
    private JList partyList, ResumableNames, scoreList;
    private Vector party, score, date, scoreFinal;
    private Integer lock;

    private ControlDesk controlDesk;

    private String selectedNick, selectedMember, selectedScore;

    public ResumeOldGame(ControlDesk controlDesk) {
        this.controlDesk = controlDesk;
        win = new JFrame("Resume Previous Game");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 1));

        // Party Panel

        // Score Panel

        // Bowler Database
        JPanel Resumable = new JPanel();
        Resumable.setLayout(new FlowLayout());
        Resumable.setBorder(new TitledBorder("Saved Games"));
        ArrayList<Object> received = new ArrayList<Object>();
        Vector<String> ResumeNames = new Vector<String>();
        try {
            FileInputStream filein = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(filein);
            received = (ArrayList<Object>) in.readObject();
            in.close();
            filein.close();
            for (int i = 0; i < received.size(); i++) {
                ArrayList<Object> temp = (ArrayList<Object>) received.get(i);
                Vector bowler = (Vector) temp.get(0);
                Iterator value = bowler.iterator();
                String s = "";
                while (value.hasNext()) {
                    Bowler temp1 = (Bowler) value.next();
                    s += temp1.getNick() + "_";
                }
                ResumeNames.add(s);
            }
            System.out.println("FIle input success");

        } catch (Exception e) {
            System.err.println("File Error");
            // bowlerdb = new Vector();
        }
        ResumableNames = new JList(ResumeNames);
        ResumableNames.setVisibleRowCount(6);
        ResumableNames.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(ResumableNames);
        bowlerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ResumableNames.addListSelectionListener(this);
        Resumable.add(bowlerPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);
        // ButtonCommon addbutton = new ButtonCommon();
        check = new ButtonCommon("Resume");
        check.Button_Panel(this, buttonPanel);

        // Clean up main panel
        // colPanel.add(partyPanel);
        // colPanel.add(scorePanel);
        colPanel.add(Resumable);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(check.button)) {
            if (selectedNick != null) {
                System.out.println(selectedNick);
                ArrayList<Object> received = new ArrayList<Object>();
                try {
                    FileInputStream filein = new FileInputStream("data.ser");
                    ObjectInputStream in = new ObjectInputStream(filein);
                    received = (ArrayList<Object>) in.readObject();
                    in.close();
                    filein.close();
                    System.out.println("FIle input success");

                } catch (Exception e2) {
                    System.out.println("FIle input error");
                    System.out.println(e2);
                    // TODO: handle exception
                }
                int removeindex = 0;
                for(int i = 0; i<received.size();i++)
                {
                    ArrayList<Object> temp = (ArrayList<Object>) received.get(i);
                    String checkn = (String) temp.get(1);
                    if(checkn.equals(selectedNick))
                    {
                        controlDesk.addPartyQueueOldGame(temp);
                        System.out.println(i);
                        removeindex = i;
                        break;
                    }
                }
                received.remove(removeindex);
                try {
                    FileOutputStream fileout = new FileOutputStream("data.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileout);
                    out.writeObject(received);
                    out.close();
                    fileout.close();
                    System.out.println("FIle ouput success");
        
                } catch (Exception e1) {
                    System.out.println("File output error");
                    System.out.println(e1);
                    // TODO: handle exception
                }
            }
        }
    }
    //

    /**
     * Handler for List actions
     * 
     * @param e the ListActionEvent that triggered the handler
     */

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(ResumableNames)) {
            selectedNick = ((String) ((JList) e.getSource()).getSelectedValue());
        }
    }
}