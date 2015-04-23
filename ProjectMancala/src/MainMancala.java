import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


//jahjsjhdhjks
//yghkjhjjjjjjjj
//this is a new comment
//this is daniel rica

public class MainMancala {

	public static void main(String[] args) {

		JFrame frame = new JFrame("WARRIORS MANCALA");
		
		frame.setSize(800, 400);
		
		JButton newGame = new JButton("New Game!");
		JButton reset = new JButton("Reset");
		JRadioButton start3 = new JRadioButton("3");
		JRadioButton start4 = new JRadioButton("4");
		ButtonGroup radio = new ButtonGroup();
		radio.add(start3);
		radio.add(start4);
		JButton board1 = new JButton("Board 1");
		JButton board2 = new JButton("Board 2");

		JPanel menu = new JPanel();
		menu.setLayout(new FlowLayout());
		menu.add(newGame);
		menu.add(reset);
		menu.add(start3);
		menu.add(start4);
		menu.add(board1);
		menu.add(board2);
		
		frame.add(menu, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
