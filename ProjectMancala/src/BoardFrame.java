import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class serves as the frame for the board.
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */

public class BoardFrame extends JFrame
{

    private final BoardModel model;
    private BoardComponent BoardComponent;
    
     /**
      * The constructor of BoardFrame to make a frame out of a BoardModel and ShapeStyle
      * @param mod is the BoardModel that is used to get the data for the frame
      * @param formatter is the ShapeStyle that is used to set the style of the frame
      */
    public BoardFrame(BoardModel mod,ShapeStyle formatter)
    {
        model = mod;
        BoardComponent = new BoardComponent(model);
        BoardComponent.setFormatter(formatter);
        JPanel top = new JPanel(); // North Panel
        top.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(); // Main Panel
        mainPanel.setLayout(new BorderLayout());
        
        //Red Rounded Board
        JButton b1 = new JButton("Red Rounded");
		b1.setFocusPainted(false);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new BoardFrame(new BoardModel(), new RedRoundedBoard());
				dispose();
			}
		});
		
		//PacMan Board
		JButton b2 = new JButton("PacMan");
		b2.setFocusPainted(false);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new BoardFrame(new BoardModel(), new PacManBoard());
				dispose();
			}
		});

        //Undo
        JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.undo();
            }
        });

        JPanel topPanel = new JPanel();
        JPanel errorPanel = new JPanel();

		JLabel label = new JLabel("Number of marbles per pit: ");
		final JTextField input = new JTextField(1);
		final JLabel newGameLabel = new JLabel("New Game:");
		final JLabel error = new JLabel("ERROR! Please enter 3 or 4.");
		error.setVisible(false);
		final JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int check = Integer.parseInt(input.getText());
				if (check < 3 || check > 4)
					error.setVisible(true);
				else {
					if(model.getCurrentStatus() == BoardModel.GameStatus.ENDED){
						BoardModel resetModel = new BoardModel();
						new BoardFrame(resetModel, new RedRoundedBoard());
						resetModel.setNumMarbles(check);
						resetModel.setCurrentStatus("RUNNING");
						dispose();
					}
					else{
						model.setNumMarbles(check);
						model.setCurrentStatus("RUNNING");
						error.setVisible(false);
						ok.setEnabled(false);
					}
					
				}
			}
		});
		topPanel.add(newGameLabel);
		topPanel.add(b1);
        topPanel.add(b2);
		topPanel.add(label);
		topPanel.add(input);
		topPanel.add(ok);
        topPanel.add(undo);
        errorPanel.add(error);
        
        top.add(topPanel, BorderLayout.NORTH);
        top.add(errorPanel, BorderLayout.SOUTH);
        mainPanel.add(top, BorderLayout.NORTH);
    
        mainPanel.add(BoardComponent, BorderLayout.CENTER);
        add(mainPanel,BorderLayout.WEST);
        mainPanel.setBackground(Color.BLACK);
        
        setSize(985, 700);
        setTitle("Warrior's Mancala");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }

}