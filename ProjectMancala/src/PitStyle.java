import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * This class creates the pits of the game board.
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */
public class PitStyle
{
    private int x, y, width, height, marbles;
    private Shape shape;
    private Color color;
    private final int marbleSize = 20;
    
    /**
     * PitStyle Creates a new Pit shape
     * @param x is the x position
     * @param y is the y position
     * @param width is the width
     * @param height is the height
     * @param color is the color of the shape.
     */
    public PitStyle(int x, int y, int width, int height, Color color)
    {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
    }
    
    /**
     * This method draws a shape and fills it with the given color
     * @param g is the Graphics2D object
     */
    public void draw(Graphics2D g)
    {
        g.draw(shape);
        fill(g);
    }
    
    /**
     * This method fills the pits with the marbles and displays the number of marbles that are in the pit 
     * @param g Graphics2D object
     */
    public void fill(Graphics2D g)
    {
        g.setColor(color);
        g.fill(shape);
            
        Ellipse2D.Double marble = new Ellipse2D.Double(x, y, marbleSize, marbleSize);
        for(int i = 0; i < marbles; i++)
        {

        		if (i%4 == 0) {
                    marble.y += 20;
                    marble.x = x+10;
                } else {
                    marble.x += 20;
                }
            g.setPaint(Color.BLACK);
            g.fill(marble);
            g.setColor(Color.GRAY);
            g.draw(marble);
        }
    }
    /**
     * This method checks if a shape contains a given point
     * @param point is the point that may or may not be within the shape
     * @return true if the point is located within the shape, and false if it isn't
     */
    public boolean contains(Point2D point)
    {
        if(shape.contains(point))
            return true;
        else
            return false;
    }
    
    /**
     * This method returns the number of marbles
     * @return the number of marbles in a pit
     */
    public int getStones()
    {
        return marbles;
    }
    /**
     * This method sets the number of marbles in a pit
     * @param n is the number of marbles to place in a pit
     */
    public void setMarbles(int n)
    {
        marbles = n;
    }
    
    /**
     * This method sets the shape of the pits
     * @param s is the desired Shape of the pit
     */
    public void setShape(Shape s)
    {
        shape = s;
    }
    /**
     * This method gets the shape of the pit
     * @return the shape of the pit
     */
    public Shape getShape()
    {
        return shape;
    }
    /**
     * This method gets the x coordinate of the pit
     * @return the x-coordinate of the pit
     */
    public int getX()
    {
        return x;
    }
    /**
     * This method gets the y coordinate of the pit
     * @return the y-coordinate of the pit
     */
    public int getY()
    {
        return y;
    }
    /**
     * This method gets the width of the pit
     * @return the width of the pit
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * This method gets the height of the pit
     * @return the height of the pit
     */
    public int getHeight()
    {
        return height;
    }
}