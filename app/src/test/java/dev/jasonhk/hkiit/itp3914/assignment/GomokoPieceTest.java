package dev.jasonhk.hkiit.itp3914.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GomokoPieceTest
{
    @Test
    void getPieceIsCorrect()
    {
        assertEquals(GomokoPiece.EMPTY.getPiece(), "0");
        assertEquals(GomokoPiece.BLACK.getPiece(), "1");
        assertEquals(GomokoPiece.WHITE.getPiece(), "2");
    }

    @Test
    void getScoreIsCorrect()
    {
        assertEquals(GomokoPiece.EMPTY.getScore(), 0);
        assertEquals(GomokoPiece.BLACK.getScore(), -1);
        assertEquals(GomokoPiece.WHITE.getScore(), 1);
    }

    @Test
    void fromTotalScoreIsCorrect()
    {
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
