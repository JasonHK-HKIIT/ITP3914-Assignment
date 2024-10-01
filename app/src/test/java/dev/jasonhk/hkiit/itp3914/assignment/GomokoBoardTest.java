package dev.jasonhk.hkiit.itp3914.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class GomokoBoardTest
{
    @Test
    void isFullIsExpected()
    {
        var board = new GomokoBoard();

        for (int i = 0; i < Gomoko.BOARD_SIZE; i++)
        {
            for (int j = 0; j < Gomoko.BOARD_SIZE; j++)
            {
                assertFalse(board.isFull());
                board.placePiece(i, j);
            }
        }

        assertTrue(board.isFull());
    }

    @Test
    void isValidMoveIsExpected()
    {
        var board = new GomokoBoard();

        assertTrue(board.isValidMove(0, 0));
        assertTrue(board.isValidMove(0, Gomoko.BOARD_SIZE - 1));
        assertTrue(board.isValidMove(Gomoko.BOARD_SIZE - 1, Gomoko.BOARD_SIZE - 1));
        assertTrue(board.isValidMove(Gomoko.BOARD_SIZE - 1, 0));

        assertFalse(board.isValidMove(0, -1));
        assertFalse(board.isValidMove(-1, -1));
        assertFalse(board.isValidMove(-1, 0));

        assertFalse(board.isValidMove(-1, Gomoko.BOARD_SIZE - 1));
        assertFalse(board.isValidMove(-1, Gomoko.BOARD_SIZE));
        assertFalse(board.isValidMove(0, Gomoko.BOARD_SIZE));

        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE - 1, Gomoko.BOARD_SIZE));
        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE, Gomoko.BOARD_SIZE));
        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE, Gomoko.BOARD_SIZE - 1));

        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE, 0));
        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE, -1));
        assertFalse(board.isValidMove(Gomoko.BOARD_SIZE - 1, -1));

        board.placePiece(0, 0);
        assertFalse(board.isValidMove(0, 0));
    }

    @Test
    void getCurrentPieceIsExpected()
    {
        var board = new GomokoBoard();

        assertEquals(board.getCurrentPiece(), GomokoPiece.BLACK);

        board.placePiece(0, 0);
        assertEquals(board.getCurrentPiece(), GomokoPiece.WHITE);

        board.placePiece(0, 1);
        assertEquals(board.getCurrentPiece(), GomokoPiece.BLACK);
    }

    @Test
    @SuppressWarnings("all")
    void checkHorizontalWinnerIsExpected()
    {
        assumeTrue(Gomoko.BOARD_SIZE >= 4);
        assumeTrue(Gomoko.PIECES_TO_WIN == 4);

        {
            var board = new GomokoBoard();

            assertNull(board.placePiece(0, 0));
            assertNull(board.placePiece(1, 0));
            assertNull(board.placePiece(0, 1));
            assertNull(board.placePiece(1, 1));
            assertNull(board.placePiece(0, 2));
            assertNull(board.placePiece(1, 2));
            assertEquals(board.placePiece(0, 3), GomokoPiece.BLACK);
        }
    }

    @Test
    @SuppressWarnings("all")
    void checkVerticalWinnerIsExpected()
    {
        assumeTrue(Gomoko.BOARD_SIZE >= 4);
        assumeTrue(Gomoko.PIECES_TO_WIN == 4);

        var board = new GomokoBoard();

        assertNull(board.placePiece(0, 0));
        assertNull(board.placePiece(0, 1));
        assertNull(board.placePiece(1, 0));
        assertNull(board.placePiece(1, 1));
        assertNull(board.placePiece(2, 0));
        assertNull(board.placePiece(2, 1));
        assertEquals(board.placePiece(3, 0), GomokoPiece.BLACK);
    }

    @Test
    @SuppressWarnings("all")
    void checkDiagonalLeftWinnerIsExpected()
    {
        assumeTrue(Gomoko.BOARD_SIZE >= 4);
        assumeTrue(Gomoko.PIECES_TO_WIN == 4);

        var board = new GomokoBoard();

        assertNull(board.placePiece(0, 3));
        assertNull(board.placePiece(0, 0));
        assertNull(board.placePiece(1, 2));
        assertNull(board.placePiece(1, 1));
        assertNull(board.placePiece(2, 1));
        assertNull(board.placePiece(2, 2));
        assertEquals(board.placePiece(3, 0), GomokoPiece.BLACK);
    }

    @Test
    @SuppressWarnings("all")
    void checkDiagonalRightWinnerIsExpected()
    {
        assumeTrue(Gomoko.BOARD_SIZE >= 4);
        assumeTrue(Gomoko.PIECES_TO_WIN == 4);

        var board = new GomokoBoard();

        assertNull(board.placePiece(0, 0));
        assertNull(board.placePiece(1, 0));
        assertNull(board.placePiece(1, 1));
        assertNull(board.placePiece(1, 2));
        assertNull(board.placePiece(2, 2));
        assertNull(board.placePiece(1, 3));
        assertEquals(board.placePiece(3, 3), GomokoPiece.BLACK);
    }
}
