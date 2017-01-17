package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class edit_database_entry extends JPanel {
	
	public edit_database_entry(){
		 setLayout(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.insets = new Insets(2, 2, 2, 2);

	        add(new JLabel("Label 1"), gbc);
	        gbc.gridx++;
	        add(new JLabel("Label 2"), gbc);

	        gbc.gridx = 0;
	        gbc.gridy++;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        add(new JTextField(20), gbc);
	        gbc.gridx++;
	        add(new JTextField(10), gbc);

	        gbc.gridx = 0;
	        gbc.gridy++;
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.gridwidth = 2;
	        add(new JButton("Click"), gbc);
	}

}
