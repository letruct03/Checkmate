package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class representing a checkerboard. Extends the AbsCheckerBoard class.
 *
 * @defines board: the board used to play checkers
 *          pieceCount: the number of tokens each player has left on the board
 *          viableDirections: the viable directions for each piece
 *
 * @corresponds self = board
 */
public class CheckerBoard extends AbsCheckerBoard {
    private static final int MIN_BOARD_SIZE = 8;
    private static final int MAX_BOARD_SIZE = 16;
    private static final int BOARD_SIZE_INCREMENT = 2;
    private static final int EMPTY_ROWS = 2;
    private static final int NUM_PLAYERS = 2;

    private final char[][] board;
    private final HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
    private final HashMap<Character, Integer> pieceCount;

    private final int rowNum;
    private final int colNum;
    private final int startingCount;
    private final int rowsPerPlayer;
    private final char playerOneChar;
    private final char playerTwoChar;
    private final char playerOneKing;
    private final char playerTwoKing;

    /**
     * Constructor for Checkerboard object. Constructor initializes instance variables.
     *
     * @param aDimension Represents the dimension (number of rows and columns) of the checkerboard being created.
     *
     * @pre aDimension is either 8, 10, 12, 14, or 16
     *
     * @post Initializes board with dimensions ROW_NUM x COL_NUM, with each position initialized to either player
     *      character, asterisk, or empty space.
     * Initializes pieceCount as new HashMap<Character, Integer> and maps STARTING_COUNT.
     * Initializes viableDirections as new HashMap<Character, ArrayList<DirectionEnum>> and maps starting directions of
     *      SE and SW, and NE and NW.
     */
    public CheckerBoard(int aDimension) {
        if (aDimension < MIN_BOARD_SIZE || aDimension > MAX_BOARD_SIZE || aDimension % BOARD_SIZE_INCREMENT != 0) {
            throw new IllegalArgumentException("Invalid board dimensions");
        }

        playerOneChar = CheckersFE.getPlayerOne();
        playerOneKing = Character.toUpperCase(playerOneChar);
        playerTwoChar = CheckersFE.getPlayerTwo();
        playerTwoKing = Character.toUpperCase(playerTwoChar);
        rowNum = aDimension;
        colNum = aDimension;
        board = new char[rowNum][colNum];
        viableDirections = new HashMap<>();
        pieceCount = new HashMap<>();

        rowsPerPlayer = (rowNum - EMPTY_ROWS) / NUM_PLAYERS;
        startingCount = (aDimension / NUM_PLAYERS) * rowsPerPlayer;

        initializeBoard();
    }

    /**
     * Initializes the checkerboard with the starting positions of the pieces.
     *
     * @pre None
     *
     * @post The board is initialized with the starting positions of the pieces:
     *       - Player one pieces are placed in the top rows of the board
     *       - Player two pieces are placed in the bottom rows of the board
     *       - Empty positions are represented by EMPTY_POS
     *       - Black tiles are represented by BLACK_TILE
     *       - The piece counts for each player are initialized to the starting count
     *       - The viable directions for each player are initialized based on their starting positions
     */
    private void initializeBoard() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if ((i + j) % BOARD_SIZE_INCREMENT == 0) {
                    if (i < rowsPerPlayer) {
                        board[i][j] = playerOneChar;
                    }
                    else if (i >= rowNum - rowsPerPlayer) {
                        board[i][j] = playerTwoChar;
                    }
                    else {
                        board[i][j] = EMPTY_POS;
                    }
                }
                else {
                    board[i][j] = BLACK_TILE;
                }
            }
        }
        getPieceCounts().put(playerOneChar, startingCount);
        getPieceCounts().put(playerTwoChar, startingCount);

        viableDirections.put(playerOneChar, new ArrayList<>());
        viableDirections.put(playerTwoChar, new ArrayList<>());
        viableDirections.put(playerOneKing, new ArrayList<>());
        viableDirections.put(playerTwoKing, new ArrayList<>());

        addViableDirections(playerOneChar, DirectionEnum.SE);
        addViableDirections(playerOneChar, DirectionEnum.SW);
        addViableDirections(playerTwoChar, DirectionEnum.NE);
        addViableDirections(playerTwoChar, DirectionEnum.NW);

    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public int getColNum() {
        return colNum;
    }

    @Override
    public int getStartingCount() {
        return startingCount;
    }

    @Override
    public void placePiece(BoardPosition pos, char player) {
        int row = pos.getRow();
        int col = pos.getColumn();
        board[row][col] = player;
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        if (pos == null) {
            throw new IllegalArgumentException("BoardPosition cannot be null.");
        }
        int row = pos.getRow();
        int col = pos.getColumn();
        return board[row][col];
    }

    @Override
    public void removePiece(BoardPosition pos, char player) {
        board[pos.getRow()][pos.getColumn()] = EMPTY_POS;
    }

    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    @Override
    public void playerLostPieces(int numPieces, char player, HashMap<Character, Integer> pieceCount) {
        pieceCount.put(player, getPieceCounts().get(player) - numPieces);
    }
}