package dev.jasonhk.hkiit.itp3914.assignment;

public class Gomoko
{
    private static final int BOARD_SIZE = 10;

    public static void main(String[] args)
    {
        var board = new GomokoBoard();
        board.printBoard();
    }

    private static enum GomokoPiece
    {
        EMPTY("0", 0),
        BLACK("1", -1),
        WHITE("2", 1);

        private final String piece;

        private final int value;

        GomokoPiece(String piece, int value)
        {
            this.piece = piece;
            this.value = value;
        }

        public String getPiece()
        {
            return piece;
        }

        public int getValue()
        {
            return value;
        }
    }

    private static class GomokoBoard
    {
        private GomokoPiece[][] board = new GomokoPiece[BOARD_SIZE][BOARD_SIZE];

        public GomokoBoard()
        {
            for (int x = 0; x < BOARD_SIZE; x++)
            {
                for (int y = 0; y < BOARD_SIZE; y++)
                {
                    board[x][y] = GomokoPiece.EMPTY;
                }
            }
        }

        public void printBoard()
        {
            for (int x = 0; x <= BOARD_SIZE; x++)
            {
                if (x < BOARD_SIZE)
                {
                    System.out.printf("%d |", x);
                }
                else
                {
                    System.out.printf("  +%s%n   ", "-".repeat(2 * BOARD_SIZE));
                }

                for (int y = 0; y < BOARD_SIZE; y++)
                {
                    System.out.printf(" %s", (x < BOARD_SIZE) ? board[x][y].getPiece() : Integer.toString(y));
                    if (y == (BOARD_SIZE - 1)) { System.out.println(); }
                }
            }
        }
    }
}
