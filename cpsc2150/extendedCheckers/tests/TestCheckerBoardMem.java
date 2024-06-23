package cpsc2150.extendedCheckers.tests;

import cpsc2150.extendedCheckers.models.*;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCheckerBoardMem {

    private ICheckerBoard makeBoard(int dimension) {
        return new CheckerBoardMem(dimension);
    }

    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();

        sb.append("|  ");
        for (int j = 0; j < board.length; j++) {
            if (j < 10) {
                if (j == board.length - 1) {
                    sb.append("| ").append(j).append("|");
                } else {
                    sb.append("| ").append(j);
                }
            } else {
                if (j == board.length - 1) {
                    sb.append("|").append(j).append("|");
                } else {
                    sb.append("|").append(j);
                }
            }
        }
        sb.append("\n");

        for (int i = 0; i < board.length; i++) {
            if (i < 10) {
                sb.append("|").append(i).append(" |");
            } else {
                sb.append("|").append(i).append("|");
            }
            for (int j = 0; j < board.length; j++) {
                if ((i + j) % 2 == 0) {
                    sb.append(board[i][j]).append(" |");
                } else {
                    sb.append("*").append(" |");
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

    @Test
    public void testCheckerboard_ValidDimension_8x8() {
        ICheckerBoard board = new CheckerBoardMem(8);
        int expected = 8;
        int observedRow = board.getRowNum();
        int observedCol = board.getColNum();
        assertEquals(expected, observedRow);
        assertEquals(expected, observedCol);
    }

    @Test
    public void testCheckerboard_ValidDimension_12x12() {
        ICheckerBoard board = new CheckerBoardMem(12);
        int expected = 12;
        int observedRow = board.getRowNum();
        int observedCol = board.getColNum();
        assertEquals(expected, observedRow);
        assertEquals(expected, observedCol);
    }

    @Test
    public void testCheckerboard_ValidDimension_16x16() {
        ICheckerBoard board = new CheckerBoardMem(16);
        int expected = 16;
        int observedRow = board.getRowNum();
        int observedCol = board.getColNum();
        assertEquals(expected, observedRow);
        assertEquals(expected, observedCol);
    }

    @Test
    public void testWhatsAtPos_MaxRow_MinCol_8x8_BlackTile() {
        ICheckerBoard board = new CheckerBoardMem(8);
        BoardPosition pos = new BoardPosition(7, 0);
        char expected = ICheckerBoard.EMPTY_POS;
        char observed = board.whatsAtPos(pos);
        assertEquals(expected, observed);
    }

    @Test
    public void testWhatsAtPos_8x8_EmptyTile() {
        ICheckerBoard board = new CheckerBoardMem(8);
        BoardPosition pos = new BoardPosition(4, 0);
        char expected = ICheckerBoard.EMPTY_POS;
        char observed = board.whatsAtPos(pos);
        assertEquals(expected, observed);
    }

    @Test
    public void testWhatsAtPos_MaxRow_MaxCol_16x16_OTile() {
        ICheckerBoard board = new CheckerBoardMem(16);
        BoardPosition pos = new BoardPosition(15, 15);
        char expected = 'o';
        char observed = board.whatsAtPos(pos);
        assertEquals(expected, observed);

    }

    @Test
    public void testWhatsAtPos_MinRow_MaxCol_8x8() {
        ICheckerBoard board = new CheckerBoardMem(8);
        BoardPosition pos = new BoardPosition(0, 7);
        char expected = ICheckerBoard.EMPTY_POS;
        char observed = board.whatsAtPos(pos);
        assertEquals(expected, observed);
    }

    @Test
    public void testWhatsAtPos_MinRow_MinCol_16x16() {
        ICheckerBoard board = new CheckerBoardMem(16);
        BoardPosition pos = new BoardPosition(0, 0);
        char expected = 'x';
        char observed = board.whatsAtPos(pos);
        assertEquals(expected, observed);
    }

    @Test
    public void testMaxRowNum_16x16() {
        ICheckerBoard board = new CheckerBoardMem(16);
        int expected = 16;
        int observed = board.getRowNum();
        assertEquals(expected, observed);
    }

    @Test
    public void testMaxCol_8x8() {
        ICheckerBoard board = new CheckerBoardMem(8);
        int expected = 8;
        int observed = board.getColNum();
        assertEquals(expected, observed);
    }

    @Test
    public void testJumpPieceRoutine() {
        char[][] expected = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'x', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(8);
        cb.placePiece(new BoardPosition(3, 3), 'x');
        cb.placePiece(new BoardPosition(4, 4), 'o');
        cb.placePiece(new BoardPosition(5, 5), ' ');

        BoardPosition pos = cb.jumpPiece(new BoardPosition(3, 3), DirectionEnum.SE);
        assertEquals(new BoardPosition(5, 5), pos);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(5,5)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(4,4)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(3,3)));
    }

    @Test
    public void testJumpPieceFromCorner() {
        char[][] expected = {
                {' ', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(8);
        cb.placePiece(new BoardPosition(0, 0), 'x');
        cb.placePiece(new BoardPosition(1, 1), 'o');
        cb.placePiece(new BoardPosition(2, 2), ' ');

        BoardPosition pos = cb.jumpPiece(new BoardPosition(0, 0), DirectionEnum.SE);
        assertEquals(new BoardPosition(2, 2), pos);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(2,2)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(1,1)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(0,0)));
    }

    @Test
    public void testJumpPieceToCorner() {
        char[][] expected = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', ' ', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'x'}
        };

        ICheckerBoard cb = makeBoard(8);
        cb.placePiece(new BoardPosition(5, 5), 'x');
        cb.placePiece(new BoardPosition(6, 6), 'o');
        cb.placePiece(new BoardPosition(7, 7), ' ');

        BoardPosition pos = cb.jumpPiece(new BoardPosition(5, 5), DirectionEnum.SE);
        assertEquals(new BoardPosition(7,7), pos);
        assertEquals('x', cb.whatsAtPos(new BoardPosition(7,7)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(6,6)));
        assertEquals(' ', cb.whatsAtPos(new BoardPosition(5,5)));
    }

    @Test
    public void testPlayerLostPiecesRoutine() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<Character, Integer> counts = cb.getPieceCounts();
        counts.put('x', 12);
        counts.put('o', 12);
        cb.playerLostPieces(1, 'o', counts);
        assertEquals(12, (int) counts.get('x'));
        assertEquals(11, (int) counts.get('o'));
    }

    @Test
    public void testScanCorner() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<DirectionEnum, Character> surroundings = cb.scanSurroundingPositions(new BoardPosition(0, 0));
        assertEquals('x', (char) surroundings.get(DirectionEnum.SE));
        assertNull(surroundings.get(DirectionEnum.SW));
        assertNull(surroundings.get(DirectionEnum.NE));
        assertNull(surroundings.get(DirectionEnum.NW));
    }

    @Test
    public void testScanEdge() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<DirectionEnum, Character> surroundings = cb.scanSurroundingPositions(new BoardPosition(0, 2));
        assertEquals('x', (char) surroundings.get(DirectionEnum.SE));
        assertEquals('x', (char) surroundings.get(DirectionEnum.SW));
        assertNull(surroundings.get(DirectionEnum.NE));
        assertNull(surroundings.get(DirectionEnum.NW));
    }

    @Test
    public void testScanCenterPlayerTwo() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<DirectionEnum, Character> surroundings = cb.scanSurroundingPositions(new BoardPosition(6, 6));
        assertEquals('o', (char) surroundings.get(DirectionEnum.SE));
        assertEquals('o', (char) surroundings.get(DirectionEnum.SW));
        assertEquals('o', (char) surroundings.get(DirectionEnum.NE));
        assertEquals('o', (char) surroundings.get(DirectionEnum.NW));
    }

    @Test
    public void testScanCenterPlayerOne() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<DirectionEnum, Character> surroundings = cb.scanSurroundingPositions(new BoardPosition(1, 1));
        assertEquals('x', (char) surroundings.get(DirectionEnum.SE));
        assertEquals('x', (char) surroundings.get(DirectionEnum.SW));
        assertEquals('x', (char) surroundings.get(DirectionEnum.NE));
        assertEquals('x', (char) surroundings.get(DirectionEnum.NW));
    }

    @Test
    public void testScanCenterBothPlayers() {
        char[][] expected = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', 'x', '*', 'x', '*', ' '},
                {' ', '*', ' ', '*', 'o', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(8);
        cb.placePiece(new BoardPosition(4, 4), 'o');
        cb.placePiece(new BoardPosition(3, 3), 'x');
        cb.placePiece(new BoardPosition(3, 5), 'x');

        HashMap<DirectionEnum, Character> surroundings = cb.scanSurroundingPositions(new BoardPosition(4, 4));
        assertEquals('o', (char) surroundings.get(DirectionEnum.SE));
        assertEquals('o', (char) surroundings.get(DirectionEnum.SW));
        assertEquals('x', (char) surroundings.get(DirectionEnum.NE));
        assertEquals('x', (char) surroundings.get(DirectionEnum.NW));
    }

    @Test
    public void testSW() {
        BoardPosition direction = ICheckerBoard.getDirection(DirectionEnum.SW);
        assertEquals(1, direction.getRow());
        assertEquals(-1, direction.getColumn());
    }


    @Test
    public void testCheckPlayerWin_8x8_PlayerxWins() {
        ICheckerBoard cb = makeBoard(8);
        HashMap<Character, Integer> numPieces = cb.getPieceCounts();
        numPieces.put('o', 0);

        Integer expectedPieces = 0;
        Integer observedPieces = cb.getPieceCounts().get('o');
        assertEquals(expectedPieces, observedPieces);

        boolean expectedWin = true;
        boolean observedWin = cb.checkPlayerWin('o'); // true = x wins
        assertEquals(expectedWin, observedWin);
    }

    @Test
    public void testCheckPlayerWin_10x10_returnFalse() {
        ICheckerBoard cb = makeBoard(10);
        HashMap<Character, Integer> numPieces = cb.getPieceCounts();

        Integer expectedPieces = 20;
        Integer observedXPieces = cb.getPieceCounts().get('x');
        assertEquals(expectedPieces, observedXPieces);

        boolean expectedWin = false;
        boolean observedWin = cb.checkPlayerWin('x'); // true = x wins
        assertEquals(expectedWin, observedWin);

        Integer observedOPieces = cb.getPieceCounts().get('o');
        assertEquals(expectedPieces, observedOPieces);

        observedWin = cb.checkPlayerWin('o');
        assertEquals(expectedWin, observedWin);
    }


    // CROWNPIECE
    @Test
    public void testcrownPiece_10x10_playerOne_lastrow() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'X', '*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(10);

        cb.placePiece(new BoardPosition(9,1), 'x');
        cb.crownPiece(new BoardPosition(9,1));

        char expectedPiece = 'X';
        char observedPiece = cb.whatsAtPos(new BoardPosition(9,1));
        assertEquals(expectedPiece, observedPiece);
        assertEquals(boardToString(expectedBoard), cb.toString());
    }

    @Test
    public void testCrownPiece_16x16_playerTwo_firstRow() {
        ICheckerBoard cb = makeBoard(16);
        cb.placePiece(new BoardPosition(0,4), 'o');
        cb.crownPiece(new BoardPosition(0, 4));

        char expectedPiece = 'O';
        char observedPiece = cb.whatsAtPos(new BoardPosition(0, 4));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testCrownPiece_12x12_playerOne_bottomCorner() {
        ICheckerBoard cb = makeBoard(12);
        cb.placePiece(new BoardPosition(11,11), 'x');
        cb.crownPiece(new BoardPosition(11, 11));

        char expectedPiece = 'X';
        char observedPiece = cb.whatsAtPos(new BoardPosition(11, 11));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testCrownPiece_10x10_AlreadyCrowned() {
        ICheckerBoard cb = makeBoard(10);
        cb.placePiece(new BoardPosition(1, 9), 'X');
        BoardPosition pos = cb.movePiece(new BoardPosition(1,9), DirectionEnum.NW);
        cb.crownPiece(pos);

        char expectedPiece = 'X';
        char observedPiece = cb.whatsAtPos(pos);
        assertEquals(expectedPiece, observedPiece);
    }

    // MOVEPIECE
    @Test
    public void testMovePiece_10x10_playerTwo_ValidMove() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'o', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', ' ', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };


        ICheckerBoard cb = makeBoard(10);

        cb.placePiece(new BoardPosition(2,2), ' ');
        cb.placePiece(new BoardPosition(3, 3), 'o');
        cb.movePiece(new BoardPosition(3,3), DirectionEnum.NW);
        assertEquals(boardToString(expectedBoard), cb.toString());

    }

    @Test
    public void testMovePiece_8x8_KingPiece() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'O', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        ICheckerBoard cb = makeBoard(8);

        cb.placePiece(new BoardPosition(6,2), ' ');
        cb.placePiece(new BoardPosition(5, 1), 'O');
        BoardPosition endPos = cb.movePiece(new BoardPosition(5,1), DirectionEnum.SE);
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'O';
        char observedPiece = cb.whatsAtPos(endPos);
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testMovePiece_12x12_toBottomCorner() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'x'}
        };

        ICheckerBoard cb = makeBoard(12);

        cb.placePiece(new BoardPosition(11, 11), ' ');
        cb.placePiece(new BoardPosition(10, 10), 'x');
        BoardPosition endPos = cb.movePiece(new BoardPosition(10,10), DirectionEnum.SE);
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'x';
        char observedPiece = cb.whatsAtPos(endPos);
        assertEquals(expectedPiece, observedPiece);
    }


    @Test
    public void testplacePiece_16x16_ValidMove() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'o', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        ICheckerBoard cb = makeBoard(16);

        cb.placePiece(new BoardPosition(3, 5), 'o');
        char expectedPiece = 'o';
        char observedPiece = cb.whatsAtPos(new BoardPosition(3, 5));
        assertEquals(expectedPiece, observedPiece);
        assertEquals(boardToString(expectedBoard), cb.toString());
    }

    @Test
    public void testplacePiece_10x10_ValidMove_CenterPosition() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', 'x', '*', ' ', '*', ' '},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(10);
        cb.placePiece(new BoardPosition(5, 5), 'x');
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'x';
        char observedPiece = cb.whatsAtPos(new BoardPosition(5, 5));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testplacePiece_16x16_ValidMove_EdgePosition() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'o', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        ICheckerBoard cb = makeBoard(16);

        cb.placePiece(new BoardPosition(0,14), 'o');
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'o';
        char observedPiece = cb.whatsAtPos(new BoardPosition(0,14));
        assertEquals(expectedPiece, observedPiece);

    }

    @Test
    public void testplacePiece_8x8_ValidMove_CornerPosition() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'x'},
        };

        ICheckerBoard cb = makeBoard(8);

        cb.placePiece(new BoardPosition(7, 7), 'x');
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'x';
        char observedPiece = cb.whatsAtPos(new BoardPosition(7, 7));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testplacePiece_10x10_ValidMove_OccupiedPosition() {
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'x', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(10);

        cb.placePiece(new BoardPosition(7, 5), 'x');
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'x';
        char observedPiece = cb.whatsAtPos(new BoardPosition(7, 5));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testplacePiece_12x12_ValidMove2() { // more specific name?
        char[][] expectedBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'x', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o', '*', 'o'}
        };

        ICheckerBoard cb = makeBoard(12);

        cb.placePiece(new BoardPosition(7,7), 'x');
        assertEquals(boardToString(expectedBoard), cb.toString());

        char expectedPiece = 'x';
        char observedPiece = cb.whatsAtPos(new BoardPosition(7,7));
        assertEquals(expectedPiece, observedPiece);
    }

    @Test
    public void testgetPieceCounts_10x10_EmptyBoard () {
        ICheckerBoard cb = makeBoard(10);

        HashMap<Character, Integer> pieceCounts = cb.getPieceCounts();
        pieceCounts.put('x', 0);
        pieceCounts.put('o', 0);

        Integer expectedpieceCounts = 0;
        Integer observedpieceCounts = cb.getPieceCounts().get('x');
        assertEquals(expectedpieceCounts, observedpieceCounts);

        observedpieceCounts = cb.getPieceCounts().get('o');
        assertEquals(expectedpieceCounts, observedpieceCounts);


    }

    @Test
    public void testgetViableDirections_10x10_playersViableDirections() {
        ICheckerBoard cb = makeBoard(10);

        ArrayList<DirectionEnum> expectedxDirs = new ArrayList<>();
        expectedxDirs.add(DirectionEnum.SE);
        expectedxDirs.add(DirectionEnum.SW);
        ArrayList<DirectionEnum> observedDirs = cb.getViableDirections().get('x');
        assertEquals(expectedxDirs, observedDirs);

        ArrayList<DirectionEnum> expectedoDirs = new ArrayList<>();
        expectedoDirs.add(DirectionEnum.NE);
        expectedoDirs.add(DirectionEnum.NW);
        observedDirs = cb.getViableDirections().get('o');
        assertEquals(expectedoDirs, observedDirs);
    }

    @Test
    public void testaddViableDirections_16x16_playerOneDirs() {
        ICheckerBoard cb = makeBoard(16);

        cb.addViableDirections('x', DirectionEnum.NW);

        ArrayList<DirectionEnum> expectedDirs = new ArrayList<>();
        expectedDirs.add(DirectionEnum.SE);
        expectedDirs.add(DirectionEnum.SW);
        expectedDirs.add(DirectionEnum.NW);

        assertEquals(expectedDirs, cb.getViableDirections().get('x'));
    }

}

