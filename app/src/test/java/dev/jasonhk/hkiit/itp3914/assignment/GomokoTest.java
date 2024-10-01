package dev.jasonhk.hkiit.itp3914.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GomokoTest
{
    @Test
    void boardSizeIsValid()
    {
        assertTrue(Gomoko.BOARD_SIZE >= Gomoko.PIECES_TO_WIN);
    }
}
