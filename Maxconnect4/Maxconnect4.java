import java.util.Scanner;
public class Maxconnect4 {
	

	    public static Scanner s = null;
	    public static GameBoard GameState = null;
	    public static AiPlayer calculon = null;
	    public static final int ONE = 1;
	    public static final int TWO = 2;
	    public static int human_disk;
	    public static int computer_disk;
	    public static int INVALID = 99;
	    public static final String FILEPATH_PREFIX = "../";
	    public static final String COMPUTER_OPT_FILE = "computer.txt";
	    public static final String HUMAN_OPT_FILE = "human.txt";

	    public enum MODE {
	        INTERACTIVE,
	        ONE_MOVE
	    };

	    public enum PLAYER_TYPE {
	        HUMAN,
	        COMP
	    };

	    public static void main(String[] args) throws CloneNotSupportedException {
	        // check for the correct number of arguments
	        if (args.length != 4) {
	            System.out.println("\tFour cmd line arguments are needed:\n"
	                + "Cmd line Argumnet in the form of:  java [program name] interactive [input_file] [computer-next / human-next] [depth]\n");

	            exit_function(0);
	        }

	        // parse the input arguments
	        String game_mode = args[0].toString(); // the game mode
	        String inputFilePath = args[1].toString(); // the input game file
	        int depthLevel = Integer.parseInt(args[3]); // the depth level of the ai search

	        // create and initialize the game board
	        GameState = new GameBoard(inputFilePath);

	        // create the Ai Player
	        calculon = new AiPlayer(depthLevel, GameState);

	        if (game_mode.equalsIgnoreCase("interactive")) {
	            GameState.setGameMode(MODE.INTERACTIVE);
	            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
	                // if it is computer next, make the computer make a move
	                GameState.setFirstTurn(PLAYER_TYPE.COMP);
	                MakeComputerPlay4Interactive();
	            } else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
	                GameState.setFirstTurn(PLAYER_TYPE.HUMAN);
	                MakeHumanPlay();
	            } else {
	                System.out.println("\n" + "Invalid Input for Game Mode");
	                exit_function(0);
	            }

	            if (GameState.isBoardFull()) {
	                System.out.println("\n\tBoard is Full\n\n\tGame\t Over.");
	                exit_function(0);
	            }

	        } else if (!game_mode.equalsIgnoreCase("one-move")) {
	            System.out.println("\n" + game_mode + " This is Not Valid \n Start Again\n");
	            exit_function(0);
	        } else {
	            // /////////// one-move mode ///////////
	            GameState.setGameMode(MODE.ONE_MOVE);
	            String outputFileName = args[2].toString(); // the output game file
	            MakeComputerPlay4OneMove(outputFileName);
	        }
	    } // end of main()
	    
	    /**
	     * This method handles computer's move for one-move mode
	     * 
	     * @param outputFileName path for output file to save game state
	     */
	    private static void MakeComputerPlay4OneMove(String outputFileName) throws CloneNotSupportedException {
	        // TODO Auto-generated method stub

	        // variables to keep up with the game
	        int playColumn = 99; // the players choice of column to play
	        boolean playMade = false; // set to true once a play has been made

	        System.out.print("\nMAX CONNECT4 GAME:\n");

	        System.out.print("Current Game State:\n");

	        // print the current game board
	        GameState.printGameBoard();

	        // print the current scores
	        System.out.println("\tScore: Player1 = \t" + GameState.getScore(ONE) + ", Player2 =\t " + GameState.getScore(TWO)
	            + "\n ");

	        if (GameState.isBoardFull()) {
	            System.out.println("\n\tThe Board is Full\n\n\tGame Over.");
	            return;
	        }

	        // ****************** this chunk of code makes the computer play

	        int current_player = GameState.getCurrentTurn();

	        // AI play - random play
	        playColumn = calculon.bestMoveWithMinMax(GameState);

	        if (playColumn == INVALID) {
	            System.out.println("\n\tThe Board is Full\n\n\tGame Over.");
	            return;
	        }

	        // play the piece
	        GameState.playDisk(playColumn);

	        // display the current game board
	        System.out.println("Played Move Number" + GameState.getDiskCount() + ": Player " + current_player + ", column "
	            + (playColumn + 1));

	        System.out.print("Game state after move:\n");

	        // print the current game board
	        GameState.printGameBoard();

	        // print the current scores
	        System.out.println("\tScore: Player1 =\t " + GameState.getScore(ONE) + ", Player-2 = \t" + GameState.getScore(TWO)
	            + "\n ");

	        GameState.printGameBoardToFile(outputFileName);

	        // ************************** end computer play

	    }

	    /**
	     * This method handles computer's move for interactive mode
	     * 
	     */
	    private static void MakeComputerPlay4Interactive() throws CloneNotSupportedException {

	        printBoardAndScore();

	        System.out.println("\n Computer's turn:\n");

	        int playColumn = INVALID; // the players choice of column to play

	        // AI play - random play
	        playColumn = calculon.bestMoveWithMinMax(GameState);

	        if (playColumn == INVALID) {
	            System.out.println("\n\tThe Board is Full\n\n\tGame Over.");
	            return;
	        }

	        // play the piece
	        GameState.playDisk(playColumn);

	        System.out.println("move: " + GameState.getDiskCount() + " , Player: Computer , Column: " + (playColumn + 1));

	        GameState.printGameBoardToFile(COMPUTER_OPT_FILE);

	        if (GameState.isBoardFull()) {
	            printBoardAndScore();
	            printResult();
	        } else {
	            MakeHumanPlay();
	        }
	    }

	    /**
	     * This method prints final result for the game.
	     * 
	     */
	    
	    private static void printResult() {
	        int human_score = GameState.getScore(Maxconnect4.human_disk);
	        int comp_score = GameState.getScore(Maxconnect4.computer_disk);
	        
	        System.out.println("\n \tFinal Result:\t");
	        if(human_score > comp_score){
	            System.out.println("\n\t Congratulations!! You won."); 
	        } else if (human_score < comp_score) {
	            System.out.println("\n \tYou lost!! ");
	        } else {
	            System.out.println("\n\t It's a Tie.!!");
	        }
	    }

	    /**
	     * This method handles human's move for interactive mode.
	     * 
	     */
	    private static void MakeHumanPlay() throws CloneNotSupportedException {
	        // TODO Auto-generated method stub
	        printBoardAndScore();

	        System.out.println("\n \tHuman's turn\tValid Moves\t[1-7]:");

	        s = new Scanner(System.in);

	        int playColumn = INVALID;

	        do {
	            playColumn = s.nextInt();
	        } while (!isValidPlay(playColumn));

	        // play the piece
	        GameState.playDisk(playColumn - 1);

	        System.out.println("move: " + GameState.getDiskCount() + " , Player: Human , Column: " + playColumn);
	        
	        GameState.printGameBoardToFile(HUMAN_OPT_FILE);

	        if (GameState.isBoardFull()) {
	            printBoardAndScore();
	            printResult();
	        } else {
	            MakeComputerPlay4Interactive();
	        }
	    }

	    private static boolean isValidPlay(int playColumn) {
	        if (GameState.isValidPlay(playColumn - 1)) {
	            return true;
	        }
	        System.out.println("Invalid Input [Range:1-7 only].");
	        return false;
	    }

	    /**
	     * This method displays current board state and score of each player.
	     * 
	     */
	    public static void printBoardAndScore() {
	        System.out.print("\tGame state :\n");

	        // print the current game board
	        GameState.printGameBoard();

	        // print the current scores
	        System.out.println("Score: Player-1 = " + GameState.getScore(ONE) + ", Player-2 = " + GameState.getScore(TWO)
	            + "\n ");
	    }

	    /**
	     * This method is used when to exit the program prematurly.
	     * 
	     * @param value an integer that is returned to the system when the program exits.
	     */
	    private static void exit_function(int value) {
	        System.out.println("\t\n\nExiting\n\n");
	        System.exit(value);
	    }
	} // end of class connectFour

