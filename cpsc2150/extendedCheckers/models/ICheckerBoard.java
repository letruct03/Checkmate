package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for representing a checkerboard and its operations.
 * Provides methods for managing the game state, moving pieces, and checking win conditions.
 *
 * @defines board: 2D character array representing the checkerboard
 *          pieceCount: a map of each player's character to the number of pieces they have on the board
 *          viableDirections: a map of each player's character to a list of their viable move directions
 *
 * @constraints board[i][j] = EMPTY_POS OR board[i][j] = playerOne OR board[i][j] = playerTwo,
 *              0 <= i < rowNum, 0 <= j < colNum
 *              0 <= pieceCount.get(playerOne) <= getStartingCount()
 *              0 <= pieceCount.get(playerTwo) <= getStartingCount()
 *              viableDirections.get(playerOne) contains only valid DirectionEnum values
 *              viableDirections.get(playerTwo) contains only valid DirectionEnum values
 *
 * @initialization_ensures board[i][j] = EMPTY_POS, 0 <= i < rowNum, 0 <= j < colNum
 *                         pieceCount.get(playerOne) = getStartingCount()
 *                         pieceCount.get(playerTwo) = getStartingCount()
 *                         viableDirections.get(playerOne) = {NE, SE}
 *                         viableDirections.get(playerTwo) = {NW, SW}
 */
public interface ICheckerBoard {
    char EMPTY_POS = ' ';
    char BLACK_TILE = '*';
    int SPACE_NUM = 1;

    /**
     * Retrieves the number of rows on the board.
     *
     * @return The number of rows on the board.
     *
     * @post getRowNum = rowNum
     */
    int getRowNum();

    /**
     * Retrieves the number of columns on the board.
     *
     * @return The number of columns on the board.
     *
     * @post getColNum = colNum
     */
    int getColNum();

    /**
     * Retrieves the starting count of pieces for each player.
     *
     * @return The starting count of pieces for each player.
     *
     * @post getStartingCount = startingCount
     */
    int getStartingCount();

    /**
     * Retrieves the viable directions for each player.
     *
     * @return A HashMap containing the viable directions for each player.
     *
     * @post getViableDirections = viableDirections
     */
    HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();

    /**
     * Adds a viable direction for a player.
     *
     * @param player The player character.
     * @param dir    The direction to add.
     *
     * @pre player = playerOne OR player = playerTwo
     *
     * @post viableDirections.get(player) = #viableDirections.get(player)
     */
    default void addViableDirections(char player, DirectionEnum dir) {
            getViableDirections().get(player).add(dir);
    }

    /**
     * Retrieves the piece counts for each player.
     *
     * @return A HashMap containing the piece counts for each player.
     *
     * @post getPieceCounts = pieceCounts
     */
    HashMap<Character, Integer> getPieceCounts();

    /**
     * Places a piece on the board at the specified position for the specified player.
     *
     * @param pos    The position on the board where the piece is to be placed.
     * @param player The player character.
     *
     * @ore 0 <= pos.getRow() < rowNum AND 0 <= pos.getColumn() < colNum
     *      player = playerOne OR player = playerTwo
     *
     * @post board[pos.getRow()][pos.getColumn()] = player
     */
    void placePiece(BoardPosition pos, char player);

    /**
     * Retrieves the character representing the player's piece at the specified position on the board.
     *
     * @param pos The position on the board to check.
     *
     * @return The character representing the player's piece at the specified position.
     *
     * @pre 0 <= pos.getRow() < rowNum AND 0 <= pos.getColumn() < colNum
     *
     * @post whatsAtPos = board[pos.getRow()][pos.getColumn()]
     */
    char whatsAtPos(BoardPosition pos);

    /**
     * Checks if the specified player has won the game.
     *
     * @param player The player character.
     *
     * @return true if the player has won, false otherwise.
     *
     * @pre player = playerOne OR player = playerTwo
     *
     * @post checkPlayerWin = (pieceCounts.get(player) == 0)
     */
    default boolean checkPlayerWin(Character player) {
        HashMap<Character, Integer> pieceCount = getPieceCounts();
        Integer count = pieceCount.get(player);
        return count != null && count == 0;
    }

    /**
     * Crowns a piece at the specified position on the board.
     *
     * @param posOfPlayer The position of the player's piece to crown.
     *
     * @pre 0 <= posOfPlayer.getRow() < rowNum AND 0 <= posOfPlayer.getColumn() < colNum
     *      board[posOfPlayer.getRow()][posOfPlayer.getColumn()] = playerOne OR
     *      board[posOfPlayer.getRow()][posOfPlayer.getColumn()] = playerTwo
     *
     * @post board[posOfPlayer.getRow()][posOfPlayer.getColumn()] = Character.toUpperCase(board[posOfPlayer.getRow()][posOfPlayer.getColumn()])
     */
    default void crownPiece(BoardPosition posOfPlayer) {
        int row = posOfPlayer.getRow();
        int col = posOfPlayer.getColumn();
        char playerPiece = whatsAtPos(posOfPlayer);
        if (Character.isLowerCase(playerPiece)) {
            removePiece(posOfPlayer, playerPiece);
            placePiece(posOfPlayer, Character.toUpperCase(playerPiece));
        }
    }

    /**
     * Checkers if the specified position is valid on the board.
     *
     * @param pos The position to check.
     *
     * @return true if the position is valid, false otherwise.
     *
     * @pre pos != null
     *
     * @post isValidPosition = (0 <= pos.getRow() < rowNum AND 0 <= pos.getColumn() < colNum)
     */
    default boolean isValidPosition(BoardPosition pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return row >= 0 && row < getRowNum() && col >= 0 && col < getColNum();
    }

    /**
     * Moves a piece on the board in the specified direction.
     *
     * @param startingPos The starting position of the piece to move.
     * @param dir         The direction in which to move the piece.
     *
     * @return The new position of the piece after moving.
     *
     * @pre 0 <= startingPos.getRow() < rowNum AND 0 <= startingPos.getColumn() < colNum
     *      board[startingPos.getRow()][startingPos.getColumn()] != EMPTY_POS
     *
     * @post [movePiece = newPos AND board[newPos.getRow()][newPos.getColumn()] = board[startingPos.getRow()][startingPos.getColumn()]
     *       AND board[startingPos.getRow()][startingPos.getColumn()] = EMPTY_POS
     *       AND viableDirections = #viableDirections AND pieceCount = #pieceCount]
     */
    default BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir) {
        char player = whatsAtPos(startingPos);

        BoardPosition newPos = BoardPosition.add(startingPos, ICheckerBoard.getDirection(dir));
        removePiece(startingPos, player);
        placePiece(newPos, player);

        return newPos;
    }

    /**
     * removes player piece from the checkerboard
     *
     * @param pos position of player piece that will be removed
     * @param player specified player
     *
     * @pre 0 < pos.getRow() < rowNum AND 0 <= pos.getColumn() < colNum()
     *      player = whatsAtPos(pos)
     *
     * @post board[pos.getRow()][pos.getColumn()] = EMPTY_POS
     *       AND viableDirections = #viableDirections AND pieceCount = #pieceCount
     */
    void removePiece(BoardPosition pos, char player);

    /**
     * Moves a piece by "jumping" an opponent player piece.
     *
     * @param startingPos The starting position of the piece to move.
     * @param dir The direction in which to move the piece.
     *
     * @return The new position of the piece after jumping.
     *
     * @pre 0 <= startingPos.getRow() < rowNum AND 0 <= startingPos.getColumn() < colNum
     *      board[startingPos.getRow()][startingPos.getColumn()] != EMPTY_POS
     *
     * @post [jumpPiece = newPos AND board[newPos.getRow()][newPos.getColumn()] = board[startingPos.getRow()][startingPos.getColumn()]
     *       AND board[startingPos.getRow()][startingPos.getColumn()] = EMPTY_POS
     *       AND board[jumpedPos.getRow()][jumpedPos.getColumn()] = EMPTY_POS
     *       AND pieceCount.get(opponentPlayer) = #pieceCount.get(opponentPlayer) - SINGLE_SPACE
     *       AND viableDirections = #viableDirections]
     */
    default BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir) {
        char player = whatsAtPos(startingPos);

        BoardPosition jumpedPos = BoardPosition.add(startingPos, ICheckerBoard.getDirection(dir));
        BoardPosition newPos = BoardPosition.add(jumpedPos, ICheckerBoard.getDirection(dir));

            removePiece(startingPos, player);
            removePiece(jumpedPos, getOpponentPlayer(player));
            placePiece(newPos, player);
            playerLostPieces(1, getOpponentPlayer(player), getPieceCounts());
            return newPos;
    }

    /**
     * Retrieves the opponent player character of the given player.
     *
     * @param player The player character.
     *
     * @return The opponent player character.
     *
     * @pre player = playerOne or player = playerTwo
     *
     * @post getOpponentPlayer = [playerOne if player = playerTwo, playerTwo if player = playerOne]
     */
    default char getOpponentPlayer(char player) {
        return player == CheckersFE.getPlayerOne() ? CheckersFE.getPlayerTwo() : CheckersFE.getPlayerOne();
    }

    /**
     * Removes specified number of pieces from player's count.
     *
     * @param numPieces The number of pieces to remove.
     * @param player The player form whom to remove pieces.
     *
     * @pre player = playerOne OR player = playerTwo
     *      0 <= numPieces <= pieceCount.get(player)
     *
     * @post viableDirections = #viableDirections AND board = #board AND pieceCounts.get(player) =
     *       #pieceCounts.get(player) - numPieces
     */
    void playerLostPieces(int numPieces, char player, HashMap<Character, Integer> pieceCounts);

    /**
     * Scans the surrounding positions of the specified position on the board.
     *
     * @param startingPos The starting position for the scan.
     *
     * @return A HashMap containing the directions and characters of the surrounding positions.
     *
     * @pre 0 <= startingPos.getRow() < rowNum AND 0 <= startingPos.getColumn() < colNum
     *
     * @post scanSurroundingPositions = surroundingPositions
     */
    default HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos) {
        HashMap<DirectionEnum, Character> surroundingPositions = new HashMap<>();

        for (DirectionEnum dir : DirectionEnum.values()) {
            BoardPosition pos = BoardPosition.add(startingPos, ICheckerBoard.getDirection(dir));
            if (isValidPosition(pos)) {
                int row = pos.getRow();
                int col = pos.getColumn();
                if (row >= 0 && row < getRowNum() && col >= 0 && col < getColNum()) {
                    surroundingPositions.put(dir, whatsAtPos(pos));
                }
            }
        }
        return surroundingPositions;
    }

    /**
     * Retrieves the position in the specified direction from the starting position.
     *
     * @param dir The direction in which to retrieve the position.
     *
     * @return The position in the specified direction from the starting position.
     *
     * @pre dir is a valid DirectionEnum
     *
     * @post getDirection = new BoardPosition(newRow, newCol)
     */
    static BoardPosition getDirection(DirectionEnum dir) {
        int newRow = 0;
        int newCol = 0;

        if (dir == DirectionEnum.NE) {
            newRow -= SPACE_NUM;
            newCol += SPACE_NUM;
        } else if (dir == DirectionEnum.SE) {
            newRow += SPACE_NUM;
            newCol += SPACE_NUM;
        } else if (dir == DirectionEnum.NW) {
            newRow -= SPACE_NUM;
            newCol -= SPACE_NUM;
        } else if (dir == DirectionEnum.SW) {
            newRow += SPACE_NUM;
            newCol -= SPACE_NUM;
        }
        return new BoardPosition(newRow, newCol);
    }
}