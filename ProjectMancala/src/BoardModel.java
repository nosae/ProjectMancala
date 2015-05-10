import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class serves Model of the game in the MVC pattern.
 * @author Daniel Rica, Nosa Edogun, Brandon Mercado
 * @version CS 151 Section 3
 */
public class BoardModel
{
    private static final int NUM_OF_PITS = 14;
    private static final int PLAYER_ONE = 6;
    private static final int PLAYER_TWO = 13;
    private final int MAXIMUM_UNDO = 3;
    
    public static enum GameStatus {RUNNING, ENDED};
    public static enum Player {ONE, TWO};

    private Player currentPlayer;
    private GameStatus currentStatus;
    private int[] pits;
    private int[] previousPits;
    private int oneUndos;
    private int twoUndos;
    private boolean lastInMancala;
    private boolean canUndo;

    private ArrayList<ChangeListener> listeners;

    /**
     * Creates a new BoardModel.
     */
    public BoardModel()
    {
        lastInMancala = false;
        oneUndos = 0;
        twoUndos = 0;
        previousPits = new int[NUM_OF_PITS];
        pits = new int[NUM_OF_PITS];
        currentPlayer = Player.ONE;
        currentStatus = GameStatus.RUNNING;
        listeners = new ArrayList<ChangeListener>();
        canUndo = false;
    }
    
    /**
     * This method attaches the ChangeListeners to the BoardModel
     * @param c is the ChangeListener that will be attached 
     */
    public void attach(ChangeListener c)
    {
        listeners.add(c);
    }
    
    /**
     * This method notifies all of the ChangeListeners in the BoardModel
     */
    private void notifyAllListeners()
    {
        for (ChangeListener c : listeners)
        {
            c.stateChanged(new ChangeEvent(this));
        }
    }
    
    /**
     * This method gets the Player's pit
     * @param indexPit is the index where the pit is located
     * @return the index the Player owns
     */
    private Player getPlayer(int indexPit)
    {
        if (indexPit >= 0 && indexPit <= PLAYER_ONE)
            return Player.ONE;
        return Player.TWO;
    }

    /**
     * This method checks if the move the player makes is valid or not
     * @param indexPit is the index that checks whether it belongs to the player or not
     * @return true if the move is valid
     * @return false if the move is invalid
     */
    public boolean canMove(int indexPit)
    {

        if (indexPit < 0 || indexPit > NUM_OF_PITS || isPlayerPit(indexPit))
            return false;

        if (currentStatus != GameStatus.RUNNING)
            return false;
        if (pits[indexPit] == 0)
            return false;

        // Moves can be made only if the player owns the pit
        if (getPlayer(indexPit) != currentPlayer)
            return false;

        return true;
    }

    /**
     * This method sets which player will be next
     */
    private void nextTurn()
    {
        if (currentPlayer == Player.ONE)
        {
            currentPlayer = Player.TWO;
        }
        else
        {
            currentPlayer = Player.ONE;
        }
    }
    
    /**
     * This method checks if the indexPit is the scoring pit of the Player
     * @param indexPit is the index which checks if it is the Player's scoring pit
     * @return true if it's the index at which the Player owns
     * @return false if it isn't the index at which the Player owns
     */
    private boolean isPlayerPit(int indexPit)
    {
        return (indexPit == PLAYER_ONE ||
                indexPit == PLAYER_TWO);
    }

    /**
     * This method returns the opposite side's pit
     * @param indexPit is the index of which will check the opposite side
     * @return the opposite index of the given indexPit
     */
    private int opposite(int indexPit)
    {
        return Math.abs(12 - indexPit);
    }

    /**
     * This method is called when the game is over, and the remaining marbles are given to the owner
     */
    private void collectMarbles()
    {
        for (int i = 0; i < NUM_OF_PITS; i++) 
        {
                if (!isPlayerPit(i))
                {
                	if (getPlayer(i) == Player.ONE)
                	{
                		pits[PLAYER_ONE] += pits[i];
                		pits[PLAYER_TWO] += pits[i];
                		pits[i] = 0;
                	}
                	
                	if (getPlayer(i) == Player.TWO)
                	{
                		pits[PLAYER_ONE] += pits[i];
                		pits[PLAYER_TWO] += pits[i];
                		pits[i] = 0;
                	}
                }
        }
    }

    /**
     * This method checks to see if the game is done
     * @return true if the game is over
     * @return false if the game isn't over
     */
    public boolean isDone()
    {
        int totalOne = 0;
        int totalTwo = 0;
        for (int i = 0; i < NUM_OF_PITS; i++) 
        {
        	if (!isPlayerPit(i))
            {
                if (getPlayer(i) == Player.ONE)
                {
                	totalOne += pits[i];
                }
                else
                {
                	totalTwo += pits[i];
                }
            }
        }
        return (totalOne == 0 || totalTwo == 0);
    }

    /**
     * This method moves the number of marbles to the next pit, until there are no more in hand
     * @param indexPit is the index where the marbles are selected
     */
    public void move(int indexPit)
    {
    	//Save in case of undo
        this.save();
        
        //Set undos
        canUndo = true;
        if(currentPlayer.equals(Player.ONE))
            twoUndos = 0;
        else
            oneUndos = 0;
        
        int marbles = pits[indexPit]; //This is the number of marbles in that player's hand
        pits[indexPit] = 0; //This sets the number of marbles in that pit to 0

        int location = indexPit; //This is the location of the pit
        while (marbles > 0)
        {
        	//This updates the location of the pit
            location++;
            if (location >= NUM_OF_PITS) 
                location = 0;

            //If it isn't in the current player's scoring pit, it is the next person's turn
            if (isPlayerPit(location) && getPlayer(location) != currentPlayer)
            {
                continue;
            }
            // Place marbles in pit
            pits[location]++;
            marbles--;
            //System.out.println(location); //Prints the location
            //System.out.println(marbles); //Prints the number of marbles in your hand
            
        }

        //*This part of the logic checks who is next*\\
        
        //If you land on your side, it is still your turn
        if (getPlayer(location) == currentPlayer && isPlayerPit(location))
        {
            lastInMancala = true;
        }
        
        //If you land on an empty put on your side
        else if (getPlayer(location) == currentPlayer
                && pits[location] == 1
                && pits[opposite(location)] > 0)
        {
        	//Steal other player's marbles
            int captured = pits[location] + pits[opposite(location)];
            pits[location] = pits[opposite(location)] = 0;
            if (currentPlayer == Player.ONE)
            {
                pits[PLAYER_ONE] += captured;
            }
            else {
                pits[PLAYER_TWO] += captured;
            }
            // Give turn to next player
            lastInMancala = false;
            nextTurn();
        }
        
        //If any other instance, it is the next player's turn
        else{
            // Give turn to next player.
            lastInMancala = false;
            nextTurn();
        }
        //_The end of where it checks for who is next_\\
        
        //Checks if the game is done
        if (isDone())
        {
            collectMarbles();
            currentStatus = GameStatus.ENDED;
        }
        // Notifies all the Listeners
        this.notifyAllListeners();
    }

    /**
     * This method sets the number of marbles that are in that pit
     * @param n is the number of marbles that will be set inside that pit
     */
    public void setNumMarbles(int n)
    {
        for(int i = 0; i < pits.length; i++)
        {
            if (!isPlayerPit(i))
                pits[i] = n;
        }

        this.notifyAllListeners();
    }

    /**
     * This method sets the status of the game
     * @param status is the status of the game
     */
    public void setCurrentStatus(String status)
    {
        if(status.equals("RUNNING"))
            currentStatus = GameStatus.RUNNING;
        else
            currentStatus = GameStatus.ENDED;
    }


    /**
     * This method gets the current player of the game
     * @return Player is the current player
     */
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * This method gets the current status of the game
     * @return GameStatus is the current status
     */
    public GameStatus getCurrentStatus()
    {
        return currentStatus;
    }

    /**
     * This method gets the number marbles in each pit
     * @return int[] array which contains the pits which have the number of marbles in each
     */
    public int[] getPits()
    {
        return pits;
    }

    /**
     * This method gets the score of the player
     * @param player is the player
     * @return the score of the specified player
     */
    public int getScore(BoardModel.Player player)
    {
        if (player == BoardModel.Player.ONE)
            return pits[PLAYER_ONE];
        return pits[PLAYER_TWO];
    }

    /**
     * This method undoes the current player's turn, with a maximum of 3 undos
     */
    public void undo()
    {
        if (!canUndo) //If undo is false, return.
            return;
        
        boolean possible = false;
        switch (currentPlayer)
        {
                case ONE:
                {
                        if (lastInMancala && oneUndos < MAXIMUM_UNDO)
                        {
                                oneUndos++;
                                possible = true;
                        }
                        else if (!lastInMancala && twoUndos < MAXIMUM_UNDO)
                        {
                                twoUndos++;
                                currentPlayer = Player.TWO;
                                possible = true;
                        }
                        break;
                }
                case TWO:
                {
                        if (lastInMancala && twoUndos < MAXIMUM_UNDO)
                        {
                                twoUndos++;
                                possible = true;
                        }
                        else if (!lastInMancala && oneUndos < MAXIMUM_UNDO)
                        {
                                oneUndos++;
                                possible = true;
                                currentPlayer = Player.ONE;
                        }
                        break;
                }
        }
        
        if (oneUndos < MAXIMUM_UNDO && currentStatus.equals(GameStatus.ENDED)  || 
                twoUndos < MAXIMUM_UNDO && currentStatus.equals(GameStatus.ENDED))
        {
            currentStatus = GameStatus.RUNNING;
        }
        
        if (possible)
        {
            canUndo = false;
            pits = previousPits.clone();
            notifyAllListeners();
        }
    }

    /**
     * This method clones the state of the pit in case of a redo
     */
    public void save()
    {
        previousPits = pits.clone();
    }
}