package com.amazonlite.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.JComboBox;

public class UpdateGUI extends JPanel {

	private final JPanel panel;
	private final GroupLayout groupLayout;
	
	private final JPanel buttonPanel;
	private final JLabel recordIdLabel;
	private final JLabel attributeLabel;
	private final JLabel newValueLabel;
	private final JTextField recordIdTextField;
	private final JTextField newValueTextField;
	private final JComboBox<String> attributeComboBox;
	
	private final JButton search;
	private final JButton update;
	private final JButton cancel;
	
	public UpdateGUI() {
		
		// set main panel layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create buttons
		search = new JButton("Search");
		search.setPreferredSize(new Dimension(75, 26));
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				View.getInstance().getTabbedPane().setSelectedIndex(2);
			}
		});
		
		/**
		 * Update button updates a record
		 */
		update = new JButton("Update");
		update.setPreferredSize(new Dimension(75, 26));
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String recordId;
				String attribute;
				String newValue;
				boolean allFieldsCompleted = true;
				
				// Basic checks if text fields contain text. Input validity is not verified.
				if (getRecordIdTextField().getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateGUI.this, "Rcord ID is required field", 
							"Required Field Missing", JOptionPane.ERROR_MESSAGE);
					allFieldsCompleted = false;
				}
				else if (getNewValueTextField().getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateGUI.this, "New value is required field", 
							"Required Field Missing", JOptionPane.ERROR_MESSAGE);
					allFieldsCompleted = false;
				}
				
				// If all text fields contain text an attempt to update record is performed.
				// Message is displayed to the user based on the output of the update (true - success or false - failure)
				if (allFieldsCompleted) {
					recordId = getRecordIdTextField().getText();
					attribute = attributeComboBox.getSelectedItem().toString();
					newValue = getNewValueTextField().getText();
					
					if (View.getInstance().getController().updateRecord(recordId, attribute, newValue)) {
						JOptionPane.showMessageDialog(UpdateGUI.this, "Record Updated", "Record Update", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(UpdateGUI.this, "Record Not Update", "Record Update", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		/**
		 * Cancel button clears text fields
		 */
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(75, 26));
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clearing all text fields
				recordIdTextField.setText("");
				newValueTextField.setText("");
				attributeComboBox.setSelectedIndex(0);
				
			}
		});
		
		// add buttons to view
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(search);
		buttonPanel.add(update);
		buttonPanel.add(cancel);
		
		// Create labels
		recordIdLabel = new JLabel("Record Id");
		attributeLabel = new JLabel("Attribute");
		newValueLabel =  new JLabel("New value");

		// Create text fields
		recordIdTextField = new JTextField(20);
		newValueTextField = new JTextField(20);
		
		// create empty combo box
		attributeComboBox = new JComboBox<String>();
		
		// setup main panel
		panel = new JPanel();

		// Set up layout
		groupLayout = new GroupLayout(panel);
		panel.setLayout(groupLayout);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		
		// Create horizontal group
		GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
		hGroup.addGroup(groupLayout.createParallelGroup()
				.addComponent(recordIdLabel)
				.addComponent(attributeLabel)
				.addComponent(newValueLabel));
		hGroup.addGroup(groupLayout.createParallelGroup()
				.addComponent(recordIdTextField)
				.addComponent(attributeComboBox)
				.addComponent(newValueTextField));
		groupLayout.setHorizontalGroup(hGroup);
		
		// Create vertical group
		GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
		vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(recordIdLabel)
				.addComponent(recordIdTextField));
		vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(attributeLabel)
				.addComponent(attributeComboBox));
		vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(newValueLabel)
				.addComponent(newValueTextField));
		groupLayout.setVerticalGroup(vGroup);
		
		add(panel);
		add(buttonPanel);
	}
	
	// Text Field getters
	private JTextField getRecordIdTextField() {
		return recordIdTextField;
	}

	protected void addSpecialAttributeToComboBox(String specialAttirbute) {
		
		// create attributes combo box and add list of items
		//String specialAttirbute = View.getInstance().getModel().getItemSpecialField(View.getInstance().getItem())[0];
		String[] attributesList = {"Title", "Author", "Length", "ReleaseDate", specialAttirbute};
		
		addComboBoxElements(attributesList);
	}
	
	private void addComboBoxElements(String[] comboBoxItemList) {
		attributeComboBox.removeAllItems();
		for (String element : comboBoxItemList) {
			attributeComboBox.addItem(element);
		}
	}

	private JTextField getNewValueTextField() {
		return newValueTextField;
	}
}
