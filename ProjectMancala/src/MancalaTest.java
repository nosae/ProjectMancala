/**
 * PacManBoard implements the ShapeStyle interface and acts as a selectable style for the user.  
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */
public class MancalaTest 
{
    public static void main(String[] args) 
    {
       BoardModel m;
       m = new BoardModel();
       BoardFrame g;
       g = new BoardFrame(m, new RedRoundedBoard());
    }
}
