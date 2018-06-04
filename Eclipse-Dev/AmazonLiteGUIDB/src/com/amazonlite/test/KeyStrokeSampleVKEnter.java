package com.amazonlite.test;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
public class KeyStrokeSampleVKEnter {
  public static void main(String[] a) {
    String ACTION_KEY = "theAction";
    JFrame frame = new JFrame("KeyStroke Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JButton buttonA = new JButton("<html><center>FOCUS/RELEASE<br>VK_ENTER");
    Action actionListener = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        JButton source = (JButton) actionEvent.getSource();
        System.out.println(source.getText());
      }
    };
    
    
    
    
    KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
    InputMap inputMap = buttonA.getInputMap();
    inputMap.put(enter, ACTION_KEY);
    ActionMap actionMap = buttonA.getActionMap();
    actionMap.put(ACTION_KEY, actionListener);
    buttonA.setActionMap(actionMap);
    frame.add(buttonA);
    frame.setSize(400, 200);
    frame.setVisible(true);
    
    
  }
}