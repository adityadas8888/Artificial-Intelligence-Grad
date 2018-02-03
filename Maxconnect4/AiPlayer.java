import java.util.*;
public class AiPlayer {
	

	    public int depth_level;
	    public GameBoard gameBoardShallow;

	    /**
	     * The constructor essentially does nothing except instantiate an AiPlayer object.
	     * 
	     * @param GameState
	     * 
	     */
	    public AiPlayer(int depth, GameBoard GameState) {
	        this.depth_level = depth;
	        this.gameBoardShallow = GameState;
	    }

	    /**
	     * This method makes the decision to make a move for the computer using 
	     * the min and max value from the below given two functions.
	     * 
	     * @param GameState The GameBoard object that is currently being used to play the game.
	     * @return an integer indicating which column the AiPlayer would like to play in.
	     * @throws CloneNotSupportedException
	     */
	    public int bestMoveWithMinMax(GameBoard GameState) throws CloneNotSupportedException {
	        int playChoice = Maxconnect4.INVALID;
	        if (GameState.getCurrentTurn() == Maxconnect4.ONE) {
	            int v = Integer.MAX_VALUE;
	            for (int i = 0; i < GameBoard.coloumns; i++) {
	                if (GameState.isValidPlay(i)) {
	                    GameBoard nextMoveBoard = new GameBoard(GameState.getGameBoard());
	                    nextMoveBoard.playDisk(i);
	                    int value = Calculate_Max_Utility(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
	                    if (v > value) {
	                        playChoice = i;
	                        v = value;
	                    }
	                }
	            }
	        } else {
	            int v = Integer.MIN_VALUE;
	            for (int i = 0; i < GameBoard.coloumns; i++) {
	                if (GameState.isValidPlay(i)) {
	                    GameBoard nextMoveBoard = new GameBoard(GameState.getGameBoard());
	                    nextMoveBoard.playDisk(i);
	                    int value = Calculate_Min_Utility(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
	                    if (v < value) {
	                        playChoice = i;
	                        v = value;
	                    }
	                }
	            }
	        }
	        return playChoice;
	    }

	    /**
	     * This method calculates the min value.
	     * 
	     * @param gameBoard The GameBoard object that is currently being used to play the game.
	     * @param depth_level depth to which computer will search for making best possible next move
	     * @param alpha_value maximum utility value for MAX player
	     * @param beta_value maximum utility value for MIN player 
	     * @return an integer indicating which column the AiPlayer would like to play in.
	     * @throws CloneNotSupportedException
	     */

	    private int Calculate_Min_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
	        throws CloneNotSupportedException {
	        // TODO Auto-generated method stub
	        if (!gameBoard.isBoardFull() && depth_level > 0) {
	            int v = Integer.MAX_VALUE;
	            for (int i = 0; i < GameBoard.coloumns; i++) {
	                if (gameBoard.isValidPlay(i)) {
	                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
	                    board4NextMove.playDisk(i);
	                    int value = Calculate_Max_Utility(board4NextMove, depth_level - 1, alpha_value, beta_value);
	                    if (v > value) {
	                        v = value;
	                    }
	                    if (v <= alpha_value) {
	                        return v;
	                    }
	                    if (beta_value > v) {
	                        beta_value = v;
	                    }
	                }
	            }
	            return v;
	        } else {
	            // this is a terminal state
	            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
	        }
	    }

	    /**
	     * This method calculates the max value.
	     * 
	     * @param gameBoard The GameBoard object that is currently being used to play the game.
	     * @param depth_level depth to which computer will search for making best possible next move
	     * @param alpha_value maximum utility value for MAX player
	     * @param beta_value maximum utility value for MIN player 
	     * @return an integer indicating which column the AiPlayer would like to play in.
	     * @throws CloneNotSupportedException
	     */

	    private int Calculate_Max_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
	        throws CloneNotSupportedException {
	        // TODO Auto-generated method stub
	        if (!gameBoard.isBoardFull() && depth_level > 0) {
	            int v = Integer.MIN_VALUE;
	            for (int i = 0; i < GameBoard.coloumns; i++) {
	                if (gameBoard.isValidPlay(i)) {
	                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
	                    board4NextMove.playDisk(i);
	                    int value = Calculate_Min_Utility(board4NextMove, depth_level - 1, alpha_value, beta_value);
	                    if (v < value) {
	                        v = value;
	                    }
	                    if (v >= beta_value) {
	                        return v;
	                    }
	                    if (alpha_value < v) {
	                        alpha_value = v;
	                    }
	                }
	            }
	            return v;
	        } else {
	            // this is a terminal state
	            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
	        }
	    }

	}

