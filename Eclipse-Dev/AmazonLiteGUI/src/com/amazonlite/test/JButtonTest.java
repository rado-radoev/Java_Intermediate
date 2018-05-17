package com.amazonlite.test;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class JButtonTest extends JFrame {
	JButtonTest(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JRadioButton rb1 = new JRadioButton("Male");
    rb1.setMnemonic(KeyEvent.VK_M);
    rb1.setActionCommand("Male");
    rb1.setSelected(true);

    JRadioButton rb2 = new JRadioButton("Female");
    rb2.setMnemonic(KeyEvent.VK_F);
    rb2.setActionCommand("Female");
    rb2.setSelected(true);
    ButtonGroup bg = new ButtonGroup();
    bg.add(rb1);
    bg.add(rb2);

    JPanel jp = new JPanel();
    jp.add(rb1);
    jp.add(rb2);

    getContentPane().add(jp);

    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new JButtonTest("RadioButton Demo");
  }
}