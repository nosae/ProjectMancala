import java.awt.Color;
import java.awt.Shape;

/**
 * This is an interface for changing the PitStyle. It follows the strategy pattern to achieve this change.
 * The style can be changed between the RedRoundedBoard or the PacmanBoard.
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */
public interface ShapeStyle
{
    Color formatPitColor();
    Shape formatPitStyle(PitStyle p);
}