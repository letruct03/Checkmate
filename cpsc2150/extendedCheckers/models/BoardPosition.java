package cpsc2150.extendedCheckers.models;
public class BoardPosition
{
    /**
     * @invariant (1 <= row <= 16) and (1 <= column <= 16)
     */

    /**
     * Row component of the BoardPosition
     */
    private int row;

    /**
     * Column component of the BoardPosition
     */
    private int column;

    /**
     * Constructor for BoardPosition class
     *
     * @param aRow the row of the BoardPosition
     * @param aColumn the column of the BoardPosition
     *
     * @pre 0 <= aRow < CheckerBoard.ROW_NUM AND 0 <= aCol < CheckerBoard.COL_NUM
     *
     * @post row = aRow AND column = aCol
     */
    public BoardPosition(int aRow, int aColumn) {
        this.row = aRow;
        this.column = aColumn;
    }

    /**
     * Function for getting player's row position
     *
     * @return the value stored in the row field
     *
     * @pre None
     *
     * @post getRow = row AND column = #column
     *
     */
    public int getRow() {
        return row;
    }

    /**
     * Function for getting player's column position
     *
     * @return the value stored in the column field
     *
     * @pre None
     *
     * @post getColumn = column AND row = #row
     */
    public int getColumn() {
        return column;
    }

    /**
     * Function for adding two BoardPositions together
     *
     * @param posOne of type BoardPosition to be added to posTwo
     * @param posTwo of type BoardPosition to be added to posOne
     *
     * @pre posOne != null AND posTwo != null
     *
     * @post add = [new BoardPosition with a row and column that is the sum of posOne and posTwo rows and columns]
     *
     */
    public static BoardPosition add(BoardPosition posOne, BoardPosition posTwo) {
        return new BoardPosition(posOne.getRow() + posTwo.getRow(), posOne.getColumn() + posTwo.getColumn());
    }

    /**
     * Doubles BoardPositions row and column elements
     *
     * @param pos BoardPosition object with instances to double
     *
     * @return new Board Position with a row and column equal to double the row and column of BoardPosition parameter
     *
     * @pre pos != null
     *
     * @post doubleBoardPosition = [new Board Position]
     */
    public static BoardPosition doubleBoardPosition(BoardPosition pos) {
        return new BoardPosition(pos.getRow() + pos.getRow(), pos.getColumn() + pos.getColumn());
    }

    /**
     * Checks if BoardPosition is within the dimensions of the board
     *
     * @param rowBound an int to compare to the player's row to determine if the row is within bounds
     * @param columnBound an int to compare to the player's column to determine if the row is within bounds
     *
     * @return true or false of type boolean if BoardPosition is or is not within bounds
     *
     * @pre none
     *
     * @post isValid = [true if BoardPosition is within bounds, false OW] AND row = #row AND column = #column
     */
    public boolean isValid(int rowBound, int columnBound) {
        return row >= 0 && row < rowBound && column >= 0 && column < columnBound;
    }

    /**
     * Checks if the BoardPosition is equal to the parameter object
     *
     * @param obj of type Object to compare to BoardPosition
     *
     * @return true of type boolean if BoardPosition equals the parameter object
     *
     * @pre obj != null
     *
     * @post equals = [true if BoardPosition is equal to the parameter object, false OW] AND row = #row AND column = #column
     */
    public boolean equals(Object obj) {
        if (obj instanceof BoardPosition) {
            BoardPosition other = (BoardPosition) obj;
            return row == other.row && column == other.column;
        }
        return false;
    }

    /**
     * Returns a string representation of the BoardPosition
     *
     * @return BoardPosition as a String
     *
     * @pre none
     *
     * @post toString = BoardPosition AND row = #row AND column = #column
     */
    public String toString() {
        return row + "," + column;
    }
}