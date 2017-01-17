package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;


public class database_display extends JFrame implements ActionListener{
	
	
	
	public static void main(String[] args){
		new database_display();
	}
	
	public database_display(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new database_menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	


	public void actionPerformed(ActionEvent e){
		System.out.println("hello button");
	}
}



