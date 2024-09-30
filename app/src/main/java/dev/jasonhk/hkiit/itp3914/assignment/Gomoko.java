package dev.jasonhk.hkiit.itp3914.assignment;

public class Gomoko
{
    public static void main(String[] args)
    {
        
    }

    private enum ChessPiece
    {
        EMPTY('0', 0),
        BLACK('1', -1),
        WHITE('2', 1);

        private final char piece;

        private final int value;

        ChessPiece(char piece, int value)
        {
            this.piece = piece;
            this.value = value;
        }

        public char getPiece()
        {
            return piece;
        }

        public int getValue()
        {
            return value;
        }
    }
}
