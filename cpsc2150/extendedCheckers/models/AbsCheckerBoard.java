package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class representing a checkerboard.
 * Extends the AbsCheckerBoard and implements the cpsc2150.extendedCheckers.models.ICheckerBoard interface.
 */
abstract class AbsCheckerBoard implements ICheckerBoard {
    private static final int DOUBLE_DIG = 10;
    private static final int DOUBLE_DIG_REDUCTION = 1;
    private static final int DOUBLE_DIG_DIVISION = 2;

    /**
     * Default constructor for AbsCheckerBoard.
     *
     * @post [Initializes an empty AbsCheckerBoard object]
     */
    public AbsCheckerBoard() {}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("|  ");
        for (int j = 0; j < getColNum(); j++) {
            if (j < DOUBLE_DIG) {
                if (j == getColNum() - DOUBLE_DIG_REDUCTION) {
                    sb.append("| ").append(j).append("|");
                } else {
                    sb.append("| ").append(j);
                }
            } else {
                if (j == getColNum() - DOUBLE_DIG_REDUCTION) {
                    sb.append("|").append(j).append("|");
                } else {
                    sb.append("|").append(j);
                }
            }
        }
        sb.append("\n");

        for (int i = 0; i < getRowNum(); i++) {
            if (i < DOUBLE_DIG) {
                sb.append("|").append(i).append(" |");
            } else {
                sb.append("|").append(i).append("|");
            }
            for (int j = 0; j < getColNum(); j++) {
                if ((i + j) % DOUBLE_DIG_DIVISION == 0) {
                    sb.append(whatsAtPos(new BoardPosition(i, j))).append(" |");
                } else {
                    sb.append("*").append(" |");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}