import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class is the component that paints shapes, buttons, and board.
 * This serves as both the View and the Controller in the MVC Pattern.
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */

public class BoardComponent extends JPanel implements ChangeListener
{
    private ArrayList<PitStyle> pits;
    private BoardModel model;
    private ShapeStyle formatter;
    private int[] marbles;
    private final int pWidth = 100;
    private final int pHeight = 150;
    private final int TOP_Y = 100;
    private final int BOTTOM_Y = 100;
    
    /**
     * Creates a new component board.
     * @param mod is the selected board model.
     */
    public BoardComponent(BoardModel mod)
    {
        model = mod;
        marbles = new int[14];
        model.attach(this);
        setPreferredSize(new Dimension(985,700));
        setBackground(Color.BLACK);
        setLayout(null);
    }
    
    /**
     * Creates the view of the board.
     */
    private void computeBoard()
    {
        pits = new ArrayList<PitStyle>();
        Color color = formatter.formatPitColor();
        
         addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                for(int i = 0; i < pits.size(); i++)
                {
                    if(pits.get(i).contains(e.getPoint()) && model.canMove(i))
                    {
                        model.move(i);
                    }
   
                }
            }
        });

        //Each of these creates the pits and sets the shape of the pits according to the chosen style.\\
         
        //Top row
        PitStyle p0 = new PitStyle(pWidth + pWidth/4, TOP_Y, pWidth, pHeight, color);
        p0.setShape(formatter.formatPitStyle(p0));
        p0.setMarbles(marbles[0]);
       
        PitStyle p1 = new PitStyle(2*pWidth + pWidth/2, TOP_Y, pWidth, pHeight, color); 
        p1.setShape(formatter.formatPitStyle(p1));
        p1.setMarbles(marbles[1]);
  
        PitStyle p2 = new PitStyle(3*pWidth + 3*pWidth/4, TOP_Y, pWidth, pHeight,color );
        p2.setShape(formatter.formatPitStyle(p2));
        p2.setMarbles(marbles[2]);
      
        PitStyle p3 = new PitStyle(5*pWidth, TOP_Y, pWidth, pHeight,color); 
        p3.setShape(formatter.formatPitStyle(p3));
        p3.setMarbles(marbles[3]);

        PitStyle p4 = new PitStyle(6*pWidth + pWidth/4, TOP_Y, pWidth, pHeight,color); 
        p4.setShape(formatter.formatPitStyle(p4));
        p4.setMarbles(marbles[4]);
    
        PitStyle p5 = new PitStyle(7*pWidth + pWidth/2, TOP_Y, pWidth, pHeight,color); 
        p5.setShape(formatter.formatPitStyle(p5));
        p5.setMarbles(marbles[5]);

        PitStyle ps = new PitStyle(10, 75, pWidth, 3*pHeight, color);
        ps.setShape(formatter.formatPitStyle(ps));
        ps.setMarbles(marbles[6]);
        

        //Bottom row
        PitStyle p7 = new PitStyle(pWidth + pWidth/4, BOTTOM_Y+250, pWidth, pHeight, color);
        p7.setShape(formatter.formatPitStyle(p7));
        p7.setMarbles(marbles[7]);

        PitStyle p8 = new PitStyle(2*pWidth + pWidth/2, BOTTOM_Y+250, pWidth, pHeight, color);
        p8.setShape(formatter.formatPitStyle(p8));
        p8.setMarbles(marbles[8]);

        PitStyle p9 = new PitStyle(3*pWidth + 3*pWidth/4, BOTTOM_Y+250, pWidth, pHeight, color);
        p9.setShape(formatter.formatPitStyle(p9));
        p9.setMarbles(marbles[9]);
 
        PitStyle p10 = new PitStyle(5*pWidth, BOTTOM_Y+250, pWidth, pHeight, color);
        p10.setShape(formatter.formatPitStyle(p10));
        p10.setMarbles(marbles[10]);

        PitStyle p11 = new PitStyle(6*pWidth + pWidth/4, BOTTOM_Y+250, pWidth, pHeight, color);
        p11.setShape(formatter.formatPitStyle(p11));
        p11.setMarbles(marbles[11]);

        PitStyle p12 = new PitStyle(7*pWidth + pWidth/2, BOTTOM_Y+250, pWidth, pHeight, color);
        p12.setShape(formatter.formatPitStyle(p12));
        p12.setMarbles(marbles[12]);
        
        PitStyle ps2 = new PitStyle(870, 75, pWidth, 3*pHeight, color);
        ps2.setShape(formatter.formatPitStyle(ps2));
        ps2.setMarbles(marbles[13]);

        pits.add(p7);
        pits.add(p8);
        pits.add(p9);
        pits.add(p10);
        pits.add(p11);
        pits.add(p12);   
        pits.add(ps2);
        pits.add(p5);
        pits.add(p4);
        pits.add(p3);
        pits.add(p2);
        pits.add(p1);
        pits.add(p0);
        pits.add(ps);

    }
    
    /**
     * This method paints the board and the pits.
     * @param g is the Graphics object
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        RoundRectangle2D ps2border = new RoundRectangle2D.Double(870, 75, pWidth, 3*pHeight, 75, 75);
        g2.setColor(Color.YELLOW);
        g2.fill(ps2border);
        
        RoundRectangle2D psborder = new RoundRectangle2D.Double(10, 75, pWidth, 3*pHeight, 75, 75);
        g2.setColor(Color.YELLOW);
        g2.fill(psborder);
        
        //Iterates through the ArrayList<PitStyle> and paints each pit
        for(PitStyle p: pits)
        {
            p.fill(g2);
        }
        
        String centerText = "";
        if (model.getCurrentStatus() == BoardModel.GameStatus.RUNNING)
        {
            if (model.getCurrentPlayer() == BoardModel.Player.ONE)
            {
                centerText = "Turn: Player ONE";
            }
            else
            {
                centerText = "Turn: Player TWO";
            }
        }
        else if (model.getCurrentStatus() == BoardModel.GameStatus.ENDED)
        {
            if(model.getScore(BoardModel.Player.ONE) > model.getScore(BoardModel.Player.TWO))
            	
            centerText = "Player ONE wins";
            else centerText = "Player TWO wins";
        }
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        g2.drawString(centerText, this.getX() + this.getWidth() / 2 - fm.stringWidth(centerText)/2 -30, this.getY() + this.getHeight() / 2 -30);
    }

    /**
     * This method is called when the model calls for a board style change.
     * @param e ChangeEvent notifies the model
     */
    @Override
    public void stateChanged(ChangeEvent e){
        computeBoard();
        marbles = model.getPits();
        for(int i = 0; i < pits.size(); i++){
            pits.get(i).setMarbles(marbles[i]);
        }
        repaint();
    }
    
    /**
     * This method sets the format of the model
     * @param s is the ShapeStyle that is desired to be used in the model
     */
    public void setFormatter(ShapeStyle s)
    {
        formatter = s;
        computeBoard();
    }
}