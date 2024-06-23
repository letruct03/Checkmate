package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a memory-conscious implementation of a checkerboard.
 * Extends AbsCheckerBoard and implements the cpsc2150.extendedCheckers.models.ICheckerBoard interface.
 *
 * @invariant A player cannot exist on or move to a black tile.
 *            A player can never have a negative number of pieces.
 *            A player can never have more than STARTING_COUNT number of pieces.
 *            Pieces cannot be moved outside the bounds of the board.
 *            When a piece moves, it leaves an EMPTY_POS where it started.
 *
 * @correspondence self = board
 *                 pieceCounts = pieceCounts
 *                 viableDirections = viableDirections
 */
public class CheckerBoardMem extends AbsCheckerBoard {
    private static final int MIN_BOARD_SIZE = 8;
    private static final int MAX_BOARD_SIZE = 16;
    private static final int BOARD_SIZE_INCREMENT = 2;
    private static final int EMPTY_ROWS = 2;
    private static final int NUM_PLAYERS = 2;


    private final Map<Character, List<BoardPosition>> board;
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
     * Constructor for CheckerBoardMem that initializes member variables according to argument value.
     *
     * @param aDimension Represents the dimension (number of rows and columns) of the checkerboard being created.
     *
     * @pre None aDimension should be 8, 10, 12, 14, or 16.
     *
     * @post board is initialized with the specified dimension.
     *       pieceCounts is initialized with the starting count for each player.
     *       viableDirections is initialized with the viable directions for each player.
     */
    public CheckerBoardMem(int aDimension) {
        if (aDimension < MIN_BOARD_SIZE || aDimension > MAX_BOARD_SIZE || aDimension % BOARD_SIZE_INCREMENT != 0) {
            throw new IllegalArgumentException("Invalid board dimensions");
        }

        rowNum = aDimension;
        colNum = aDimension;
        board = new HashMap<>();
        viableDirections = new HashMap<>();
        pieceCount = new HashMap<>();

        rowsPerPlayer = (rowNum - EMPTY_ROWS) / NUM_PLAYERS;
        startingCount = (aDimension / NUM_PLAYERS) * rowsPerPlayer;

        playerOneChar = CheckersFE.getPlayerOne();
        playerOneKing = Character.toUpperCase(playerOneChar);
        playerTwoChar = CheckersFE.getPlayerTwo();
        playerTwoKing = Character.toUpperCase(playerTwoChar);

        initializeBoard();
    }

    /**
     * Creates the playing board based on member variable values.
     *
     * @pre None
     *
     * @post board is initialized with the correct positions for each player's pieces.
     *       pieceCounts is updated with the starting count for each player.
     */
    private void initializeBoard() {
        getPieceCounts().put(playerOneChar, startingCount);
        getPieceCounts().put(playerTwoChar, startingCount);

        board.put(playerOneChar, new ArrayList<>());
        board.put(playerTwoChar, new ArrayList<>());
        board.put(playerOneKing, new ArrayList<>());
        board.put(playerTwoKing, new ArrayList<>());

        viableDirections.put(playerOneChar, new ArrayList<>());
        viableDirections.put(playerTwoChar, new ArrayList<>());
        viableDirections.put(playerOneKing, new ArrayList<>());
        viableDirections.put(playerTwoKing, new ArrayList<>());

        addViableDirections(playerOneChar, DirectionEnum.SE);
        addViableDirections(playerOneChar, DirectionEnum.SW);
        addViableDirections(playerTwoChar, DirectionEnum.NE);
        addViableDirections(playerTwoChar, DirectionEnum.NW);

        addViableDirections(playerOneKing, DirectionEnum.SE);
        addViableDirections(playerOneKing, DirectionEnum.SW);
        addViableDirections(playerOneKing, DirectionEnum.NE);
        addViableDirections(playerOneKing, DirectionEnum.NW);

        addViableDirections(playerTwoKing, DirectionEnum.SE);
        addViableDirections(playerTwoKing, DirectionEnum.SW);
        addViableDirections(playerTwoKing, DirectionEnum.NE);
        addViableDirections(playerTwoKing, DirectionEnum.NW);

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if ((i + j) % BOARD_SIZE_INCREMENT == 0) {
                    if (i < rowsPerPlayer) {
                        board.get(playerOneChar).add(new BoardPosition(i, j));
                    } else if (i >= rowNum - rowsPerPlayer) {
                        board.get(playerTwoChar).add(new BoardPosition(i, j));
                    }
                }
            }
        }
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
        if(player == EMPTY_POS) {
            for(Map.Entry<Character, List<BoardPosition>> entry : board.entrySet()) {
                if(entry.getValue().contains(pos)) {
                    entry.getValue().remove(pos);
                    return;
                }
            }
        }
        else if(whatsAtPos(pos) == EMPTY_POS) {
            board.get(player).add(pos);
        }
        else {
            removePiece(pos, whatsAtPos(pos));
            board.get(player).add(pos);
        }
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        if (pos == null) {
            throw new IllegalArgumentException("BoardPosition cannot be null.");
        }
        if (board.get(playerOneChar).contains(pos)) {
            return playerOneChar;
        } else if (board.get(playerOneKing).contains(pos)) {
            return playerOneKing;
        } else if (board.get(playerTwoChar).contains(pos)) {
            return playerTwoChar;
        } else if (board.get(playerTwoKing).contains(pos)) {
            return playerTwoKing;
        } else {
            return EMPTY_POS;
        }
    }

    @Override
    public void removePiece(BoardPosition pos, char player) {
        board.get(player).remove(pos);
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