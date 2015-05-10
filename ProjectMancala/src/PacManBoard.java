import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;

/**
 * PacManBoard implements the ShapeStyle interface and acts as a selectable style for the user.  
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */

public class PacManBoard implements ShapeStyle
{

	/**
     * This method returns a the color which will be used for the PacmanBoard
     * @return color the color of the pits used for the PacmanBoard
     */
    @Override
    public Color formatPitColor()
    {
        return Color.YELLOW;
    }
    
    /**
     * This method returns a specific shape(Arc) which will determine the shape of the pits of the PacmanBoard
     * @param p the PitStyle with dimensions
     * @return a PacMan shape with the dimensions of the given PitStyle p.
     */
    @Override
    public Shape formatPitStyle(PitStyle p)
    {
    	return new Arc2D.Double(p.getX(), p.getY(), p.getWidth(), p.getHeight(), 15, 330, Arc2D.PIE);
    }
    
}