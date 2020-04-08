import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.text.*;

public class ButtonCommon {

    public JButton button;
    public ButtonCommon(String text)
    {
        button = new JButton(text);
    }

    public void Button_Panel(ActionListener x, JPanel panel)
    {
        JPanel NewPanel = new JPanel();
		NewPanel.setLayout(new FlowLayout());
		button.addActionListener(x);
        NewPanel.add(button);
        panel.add(NewPanel);
    }
}