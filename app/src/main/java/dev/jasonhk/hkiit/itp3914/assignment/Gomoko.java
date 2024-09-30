package dev.jasonhk.hkiit.itp3914.assignment;

import java.util.Scanner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Gomoko
{
    /**
     * The size of the Gomoko board.
     */
    private static final int BOARD_SIZE = 10;

    /**
     * The amount of consecutive-pieces needed to win the game.
     */
    private static final int PIECES_TO_WIN = 4;

    /**
     * The width needed to display a Gomoko piece.
     */
    private static final int PIECE_WIDTH = Integer.toString(BOARD_SIZE - 1).length();

    /**
     * The entrypoint of the application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        var scanner = new Scanner(System.in);

        var board = new GomokoBoard();
        board.printBoard();

        while (!board.isFull())
        {
            System.out.printf("Player %s's turn.%n", board.getCurrentPiece().getPiece());

            System.out.print("Enter row and column (e.g., 0 1): ");
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            if (!board.isValidMove(row, column)) { continue; }
            GomokoPiece winner = board.placePiece(row, column);

            board.printBoard();

            if (winner != null)
            {
                System.out.printf("Player %s wins!%n", winner.getPiece());
                break;
            }
            else if (board.isFull())
            {
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }

    /**
     * The Gomoko pieces.
     */
    private static enum GomokoPiece
    {
        /** Empty slot. */
        EMPTY("0", 0),
        /** Black piece. */
        BLACK("1", -1),
        /** White piece. */
        WHITE("2", 1);

        private final String piece;

        private final int score;

        private final int winningScore;

        private GomokoPiece(String piece, int score)
        {
            this.piece = piece;
            this.score = score;
            winningScore = score * PIECES_TO_WIN;
        }

        /**
         * Retrieve the symbol representing the Gomoko piece.
         * 
         * @return The representing symbol.
         */
        @Nonnull
        public String getPiece()
        {
            return piece;
        }

        public int getScore()
        {
            return score;
        }

        @Nullable
        public static GomokoPiece fromWinningScore(int score)
        {
            if (score == BLACK.winningScore)
            {
                return BLACK;
            }
            else if (score == WHITE.winningScore)
            {
                return WHITE;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * The Gomoko game board.
     */
    private static class GomokoBoard
    {
        private static final int SIZE = BOARD_SIZE * BOARD_SIZE;

        private GomokoPiece[][] board = new GomokoPiece[BOARD_SIZE][BOARD_SIZE];

        private int placedCount = 0;

        private GomokoPiece currentPiece = GomokoPiece.BLACK;

        public GomokoBoard()
        {
            for (int i = 0; i < BOARD_SIZE; i++)
            {
                for (int j = 0; j < BOARD_SIZE; j++)
                {
                    board[i][j] = GomokoPiece.EMPTY;
                }
            }
        }

        public boolean isFull()
        {
            if (placedCount > SIZE)
            {
                throw new IllegalStateException(String.format("placedCount should never be larger than %d.", SIZE));
            }

            return (placedCount == SIZE);
        }

        public boolean isValidMove(int row, int column)
        {
            if (((row < 0) || (row >= BOARD_SIZE)) || ((column < 0) || (column >= BOARD_SIZE)))
            {
                System.err.println("Out of range! Input again.");
                return false;
            }
            else if (board[row][column] != GomokoPiece.EMPTY)
            {
                System.err.println("Invalid move. Try again.");
                return false;
            }
            else
            {
                return true;
            }
        }

        public GomokoPiece getCurrentPiece()
        {
            return currentPiece;
        }

        public GomokoPiece placePiece(int row, int column)
        {
            if (board[row][column] != GomokoPiece.EMPTY)
            {
                throw new IllegalArgumentException(String.format("board[%d][%d] was not empty.", row, column));
            }

            board[row][column] = currentPiece;
            currentPiece = (currentPiece == GomokoPiece.BLACK) ? GomokoPiece.WHITE : GomokoPiece.BLACK;

            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                GomokoPiece winner;
                
                winner = checkHorizontalWinner(row, column - i);
                if (winner != null) { return winner; }

                winner = checkVerticalWinner(row - i, column);
                if (winner != null) { return winner; }
            }

            return null;
        }

        public void printBoard()
        {
            System.out.println();

            for (int i = 0; i <= BOARD_SIZE; i++)
            {
                if (i < BOARD_SIZE)
                {
                    System.out.printf("%" + PIECE_WIDTH + "d |", i);
                }
                else
                {
                    System.out.println(" ".repeat(PIECE_WIDTH + 1) + "+" + "-".repeat((PIECE_WIDTH + 1) * BOARD_SIZE));
                    System.out.print(" ".repeat(PIECE_WIDTH + 2));
                }

                for (int j = 0; j < BOARD_SIZE; j++)
                {
                    System.out.printf(" %" + PIECE_WIDTH + "s", (i < BOARD_SIZE) ? board[i][j].getPiece() : Integer.toString(j));
                    if (j == (BOARD_SIZE - 1)) { System.out.println(); }
                }
            }

            System.out.println();
        }

        @Nullable
        private GomokoPiece checkHorizontalWinner(int row, int column)
        {
            if ((column < 0) || ((column + PIECES_TO_WIN - 1) >= BOARD_SIZE)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                score += board[row][column + i].getScore();
            }

            return GomokoPiece.fromWinningScore(score);
        }

        @Nullable
        private GomokoPiece checkVerticalWinner(int row, int column)
        {
            if ((row < 0) || ((row + PIECES_TO_WIN - 1) >= BOARD_SIZE)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                if ((row + i) >= BOARD_SIZE) { return null; }

                score += board[row + i][column].getScore();
            }

            return GomokoPiece.fromWinningScore(score);
        }

        @Nullable
        private GomokoPiece checkDiagonalLeftWinner(int row, int column)
        {
            if ((row < 0) || (column < 0)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                score += board[row + i][column + (PIECES_TO_WIN - i - 1)].getScore();
            }

            return GomokoPiece.fromWinningScore(score);
        }

        @Nullable
        private GomokoPiece checkDiagonalRightWinner(int row, int column)
        {
            if ((row < 0) || (column < 0)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                score += board[row + i][column + i].getScore();
            }

            return GomokoPiece.fromWinningScore(score);
        }
    }
}
