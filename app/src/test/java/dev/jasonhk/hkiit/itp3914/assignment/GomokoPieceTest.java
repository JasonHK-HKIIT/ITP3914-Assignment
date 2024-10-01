package dev.jasonhk.hkiit.itp3914.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class GomokoPieceTest
{
    @Test
    void getPieceIsExpected()
    {
        assertEquals(GomokoPiece.EMPTY.getPiece(), "0");
        assertEquals(GomokoPiece.BLACK.getPiece(), "1");
        assertEquals(GomokoPiece.WHITE.getPiece(), "2");
    }

    @Test
    void getScoreIsExpected()
    {
        assertEquals(GomokoPiece.EMPTY.getScore(), 0);
        assertEquals(GomokoPiece.BLACK.getScore(), -1);
        assertEquals(GomokoPiece.WHITE.getScore(), 1);
    }

    @Test
    @SuppressWarnings("all")
    void fromTotalScoreIsExpected()
    {
        assumeTrue(Gomoko.PIECES_TO_WIN == 4);

        assertEquals(GomokoPiece.fromTotalScore(-4), GomokoPiece.BLACK);
        assertEquals(GomokoPiece.fromTotalScore(-3), null);
        assertEquals(GomokoPiece.fromTotalScore(-2), null);
        assertEquals(GomokoPiece.fromTotalScore(-1), null);
        assertEquals(GomokoPiece.fromTotalScore(0), null);
        assertEquals(GomokoPiece.fromTotalScore(1), null);
        assertEquals(GomokoPiece.fromTotalScore(2), null);
        assertEquals(GomokoPiece.fromTotalScore(3), null);
        assertEquals(GomokoPiece.fromTotalScore(4), GomokoPiece.WHITE);
    }
}
