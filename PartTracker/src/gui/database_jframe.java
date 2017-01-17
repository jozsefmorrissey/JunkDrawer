package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class database_jframe extends JFrame implements ActionListener{
	
	JFrame data_jf= new JFrame();
	JPanel curr_panel = new database_menu();

	public static void main(String[] args){
		new database_jframe();
	}
	
	public database_jframe(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        data_jf = new JFrame("Testing");
        data_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        data_jf.setLayout(new BorderLayout());
        data_jf.add(curr_panel);
        data_jf.pack();
        data_jf.setLocationRelativeTo(null);
        data_jf.setVisible(true);
	}
	


	public void actionPerformed(ActionEvent e){
		System.out.println("hello button");
	}
}
