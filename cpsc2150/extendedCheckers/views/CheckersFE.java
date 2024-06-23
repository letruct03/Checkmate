package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.*;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.HashMap;
import java.util.Scanner;

/**
 * CheckersFe.java contains main method and control's the Checker game's flow by alternating players' turns,
 * prompting users for input and moving pieces to the directed position. It will also prompt the user to input
 * again if given an invalid input.
 */
public class CheckersFE {
    private static char PLAYER_ONE = 'x';
    private static char PLAYER_TWO = 'o';

    /**
     * Returns the character representing Player 1's piece.
     *
     * @return char PLAYER_ONE
     *
     * @pre none
     *
     * @post [getPlayerOne = PLAYER_ONE]
     */
    public static char getPlayerOne() {
        return PLAYER_ONE;
    }

    /**
     * Returns the character representing Player 2's piece.
     *
     * @return char PLAYER_TWO
     *
     * @pre None
     *
     * @post [getPlayerTwo = PLAYER_TWO]
     */
    public static char getPlayerTwo() {
        return PLAYER_TWO;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean playAgain = true;
        while (playAgain) {
            System.out.println("Welcome to Checkers!");
            PLAYER_ONE = promptPlayerPiece(input, "Player 1");
            PLAYER_TWO = promptPlayerPiece(input, "Player 2");

            String gameType = promptGameType(input);

            int boardSize = promptBoardSize(input);

            ICheckerBoard board;
            if (gameType.equalsIgnoreCase("f")) {
                board = new CheckerBoard(boardSize);
            } else {
                board = new CheckerBoardMem(boardSize);
            }

            playGame(board, input);

            playAgain = promptPlayAgain(input);
        }
    }

    /**
     * This function prompts the player to choose their desired playing piece type.
     * This function will continue to prompt until a single-character input is entered.
     *
     * @param input player input
     * @param player specified player
     *
     * @return char piece that holds the player's piece choice
     *
     * @pre None
     *
     * @post [promptPlayerPiece = piece]
     */
    private static char promptPlayerPiece(Scanner input, String player) {
        char piece;
        int pieceLength = 1;
        int firstChar = 0;
        while (true) {
            System.out.print(player + ", enter your piece: \n");
            String pieceStr = input.nextLine().trim();
            if (pieceStr.length() == pieceLength) {
                piece = pieceStr.charAt(firstChar);
                break;
            }
            System.out.println("Invalid input. Enter a single character.");
        }
        return Character.toLowerCase(piece);
    }

    /**
     * This function prompts the player to choose between a fast game or memory efficient game.
     * This function only accepts 'f', 'F', 'm', or 'M' as input and will continue to prompt
     * until valid input is entered.
     *
     * @param input player input
     *
     * @return String gameType that holds the player's game choice
     *
     * @pre None
     *
     * @post promptGameType = gameType
     */
    private static String promptGameType(Scanner input) {
        String gameType;
        while (true) {
            System.out.print("Do you want a fast game (F/f) or a memory efficient game (M/m)? \n");
            gameType = input.nextLine().trim();
            if (gameType.equalsIgnoreCase("f") || gameType.equalsIgnoreCase("m")) {
                break;
            }
            System.out.println("Invalid input. Try again.");
        }
        return gameType;
    }

    /**
     * This function prompts the player to choose their desired board size.
     * This function only accepts minBoard through maxBoard integers as valid input and will continue
     * to prompt until valid input is entered.
     *
     * @param input player input
     *
     * @return int board that holds the player's board size choice
     *
     * @pre None
     *
     * @post promptBoardSize = boardSize
     */
    private static int promptBoardSize(Scanner input) {
        int boardSize;
        int maxBoard = 16;
        int minBoard = 8;
        while (true) {
            System.out.print("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number: \n");
            String sizeStr = input.nextLine().trim();
            try {
                boardSize = Integer.parseInt(sizeStr);
                if (boardSize >= minBoard && boardSize <= maxBoard && boardSize % 2 == 0) {
                    break;
                }
                System.out.println("Invalid board size. Enter an even number between 8 and 16.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number.");
            }
        }
        return boardSize;
    }

    /**
     * This function handles the game loop, prompting the current player for moves
     * and determines if the player has won the game.
     *
     * @param board current state of checkerboard
     * @param input player input
     *
     * @pre None
     *
     * @post playGame = #playGame
     */
    private static void playGame(ICheckerBoard board, Scanner input) {
        char currentPlayer = PLAYER_ONE;

        while (true) {
            System.out.println("\n" + board);
            System.out.print("Player " + currentPlayer + ", which piece do you wish to move?");

            if (board.checkPlayerWin(getOpponentPlayer(currentPlayer))) {
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }
            // change prompt argument and condition
            BoardPosition startPos = promptPosition(input, " Enter the row followed by a space followed by the column.\n");

            // validate piece is currentPlayer's
            while(board.whatsAtPos(startPos) != currentPlayer) {
                System.out.println("Player " + currentPlayer + ", that isn't your piece. Pick one of your pieces.");
                System.out.print("Player " + currentPlayer + ", which piece do you wish to move?");
                startPos = promptPosition(input, " Enter the row followed by a space followed by the column.\n");
            }

            boolean invalid = true;

            // validate direction
            while(invalid) {
                DirectionEnum direction = promptDirection(input);
                if(!(board.getViableDirections().get(currentPlayer).contains(direction))) {
                    System.out.println("Invalid move. Try again.");
                    direction = promptDirection(input);
                }

                HashMap<DirectionEnum, Character> scans = board.scanSurroundingPositions(startPos);
                System.out.println(scans);


                if(scans.get(direction) == ' ') {
                    BoardPosition endPos = board.movePiece(startPos, direction);
                    if(currentPlayer == getPlayerOne() && endPos.getRow() == board.getRowNum() - 1) {
                        board.crownPiece(endPos);
                    }
                    else if(currentPlayer == getPlayerTwo() && endPos.getRow() == 0) {
                        board.crownPiece(endPos);
                    }
                    currentPlayer = getOpponentPlayer(currentPlayer);
                    invalid = false;
                }
                else if(scans.get(direction) != currentPlayer) {
                    BoardPosition endPos = board.jumpPiece(startPos, direction);
                    if(endPos != startPos) {
                        currentPlayer = getOpponentPlayer(currentPlayer);
                        invalid = false;
                    }
                    else {
                        System.out.println("Invalid move. Try again.");
                    }
                }
                else {
                    System.out.println("Invalid move. Try again.");
                }
            }
        }
    }

    /**
     * This function prompts the player for the row and column of the piece they want to move.
     * This function accepts valid integers represented on the checkerboard in the format "row,col"
     * and will prompt until a valid position is entered.
     *
     * @param input player input
     * @param prompt prompt for piece position
     *
     * @return boardPosition of player input coordinates
     *
     * @pre None
     *
     * @post [promptPosition = boardPosition]
     */
    private static BoardPosition promptPosition(Scanner input, String prompt) {
        int numOfChar = 2;
        int partsFirstElement = 0;
        int partSecondElement = 1;
        while (true) {
            System.out.print(prompt);
            String posStr = input.nextLine().trim();
            String[] parts = posStr.split(" ");
            if (parts.length == numOfChar) {
                try {
                    int row = Integer.parseInt(parts[partsFirstElement].trim());
                    int col = Integer.parseInt(parts[partSecondElement].trim());
                    return new BoardPosition(row, col);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter valid row and column numbers.");
                }
            } else {
                System.out.println("Invalid input. Enter the position in the format 'row col'.");
            }
        }
    }

    /**
     * This function prompts the player for their chosen piece's moving direction.
     * This function accepts any of the four cardinal directions (NE, NW, SE, SW) as input
     * and will continue to prompt until a valid direction is entered.
     *
     * @param input player input
     *
     * @return DirectionEnum dirStr which holds the player's chosen direction
     *
     * @pre None
     *
     * @post promptDirection = dirStr
     */
    private static DirectionEnum promptDirection(Scanner input) {
        while (true) {
            System.out.println("In which direction do you wish to move the piece?");
            System.out.println("Enter one of these options: ");
            
            String dirStr = input.nextLine().trim().toUpperCase();
            try {
                return DirectionEnum.valueOf(dirStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Enter a valid direction (NE/SE/NW/SW).");
            }
        }
    }

    /**
     * This function finds the character representing the opponent's piece.
     *
     * @param player opponent player
     *
     * @return char player which holds the opponent's piece
     *
     * @pre None
     *
     * @post getOpponentPlayer = player
     */
    private static char getOpponentPlayer(char player) {
        return player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
    }

    /**
     * This function prompts the player to choose whether they want to play again or not.
     * This function accepts the character 'Y' or 'N' as valid input and will continue
     * prompting until valid input is entered.
     *
     * @param input player input
     *
     * @return True if player chooses to play again, otherwise false
     *
     * @pre None
     *
     * @post promptPlayAgain = true IF player chooses to play again, false OW
     */
    private static boolean promptPlayAgain(Scanner input) {
        while (true) {
            System.out.print("Do you want to play again? (Y/N): ");
            String playAgainStr = input.nextLine().trim().toUpperCase();
            if (playAgainStr.equals("Y")) {
                return true;
            } else if (playAgainStr.equals("N")) {
                return false;
            }
            System.out.println("Invalid input. Enter 'Y' or 'N'.");
        }
    }
}