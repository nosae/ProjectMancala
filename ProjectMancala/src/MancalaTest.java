/**
 * Main class for Mancala project.  Initializes a StyleFrame object.
 * @author Donovon Bacon
 * @author Hoang Luong
 * @author Alexander Ung
 * @version May 6, 2014
 */
public class MancalaTest 
{
    public static void main(String[] args) 
    {
       BoardModel m;
       m = new BoardModel();
       BoardFrame g;
       g = new BoardFrame(m, new RedRoundedBoard());
    	//new StyleFrame();
    }
}
