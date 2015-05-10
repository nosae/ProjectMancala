import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;

/**
 * RedRoundedBoard implements the ShapeFormatter interface and acts as a selectable style for the user.  
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */

public class RedRoundedBoard implements ShapeStyle
{
	
	/**
     * This method returns a the color which will be used for the RedRoundedBoard
     * @return color the color of the pits used for the RedRoundedBoard
     */
    @Override
    public Color formatPitColor()
    {
        return Color.RED;
    }

    /**
     * This method returns a specific shape(RoundRectangle) which will determine the shape of the pits of the RedRoundedBoard
     * @param p the PitStyle with dimensions
     * @return a rounded rectangle shape with the dimensions of the given PitStyle p
     */
    @Override
    public Shape formatPitStyle(PitStyle p)
    {
        return new RoundRectangle2D.Double(p.getX(), p.getY(), p.getWidth(), p.getHeight(), 75, 75);
        
        
    }
}