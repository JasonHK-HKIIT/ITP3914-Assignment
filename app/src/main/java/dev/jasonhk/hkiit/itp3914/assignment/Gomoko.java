/**
 * @author Kwok Chi Leong (240141706, Class 1A)
 */

package dev.jasonhk.hkiit.itp3914.assignment;

import java.util.Scanner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Gomoko
{
    /**
     * The size of the {@link GomokoBoard}.
     */
    private static final int BOARD_SIZE = 10;

    /**
     * The amount of consecutive-pieces needed to win the game.
     */
    private static final int PIECES_TO_WIN = 4;

    /**
     * The width needed to display a {@link GomokoPiece}.
     */
    private static final int PIECE_WIDTH = Integer.toString(BOARD_SIZE - 1).length();

    /**
     * The entrypoint of the application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args)
    {
        // The standard input scanner.
        var scanner = new Scanner(System.in);

        // The Gomoko game board.
        var board = new GomokoBoard();
        board.printBoard();

        while (!board.isFull())
        {
            // Announce the game turn.
            System.out.printf("Player %s's turn.%n", board.getCurrentPiece().getPiece());

            // Receive inputs.
            System.out.print("Enter row and column (e.g., 0 1): ");
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            // Validate inputs, retry when invalid, otherwise place the piece.
            if (!board.isValidMove(row, column)) { continue; }
            GomokoPiece winner = board.placePiece(row, column);

            // Print the Gomoko game board.
            board.printBoard();

            // Print the winner/draw message.
            if (winner != null)
            {
                System.out.printf("Player %s wins!%n", winner.getPiece());
                break; // Break out of the loop when the game was ended.
            }
            else if (board.isFull())
            {
                System.out.println("It's a draw!");
                break; // This line is actually useless.
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

        /**
         * The symbol representing the Gomoko piece.
         */
        private final String piece;

        /**
         * The score for the Gomoko piece.
         */
        private final int score;

        /**
         * The score when the pieces forms a consecutive line.
         */
        private final int winningScore;

        private GomokoPiece(String piece, int score)
        {
            this.piece = piece;
            this.score = score;
            winningScore = score * PIECES_TO_WIN;
        }

        /**
         * The symbol representing the Gomoko piece.
         */
        @Nonnull
        public String getPiece()
        {
            return piece;
        }

        /**
         * The score for the Gomoko piece.
         */
        public int getScore()
        {
            return score;
        }

        /**
         * Get the appropriate Gomoko piece from a total score.
         *
         * @param The total score.
         * @return The Gomoko piece, {@code null} if no matches.
         */
        @Nullable
        public static GomokoPiece fromTotalScore(int score)
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
        /**
         * The total cells count for the Gomoko game board.
         */
        private static final int SIZE = BOARD_SIZE * BOARD_SIZE;

        /**
         * The Gomoko game board matrix.
         */
        private GomokoPiece[][] board = new GomokoPiece[BOARD_SIZE][BOARD_SIZE];

        /**
         * The number of {@link GomokoPiece}s placed.
         */
        private int placedCount = 0;

        /**
         * The next {@code GomokoPiece} to be placed.
         */
        private GomokoPiece currentPiece = GomokoPiece.BLACK;

        public GomokoBoard()
        {
            // Initialise the game board matrix.
            for (int i = 0; i < BOARD_SIZE; i++)
            {
                for (int j = 0; j < BOARD_SIZE; j++)
                {
                    board[i][j] = GomokoPiece.EMPTY;
                }
            }
        }

        /**
         * Determine whether the Gomoko game board was filled completely.
         * 
         * @return {@code true} when filled completely, {@code false} otherwise.
         */
        public boolean isFull()
        {
            if (placedCount > SIZE)
            {
                throw new IllegalStateException(String.format("placedCount should never be larger than %d.", SIZE));
            }

            return (placedCount == SIZE);
        }

        /**
         * Determine whether the move is valid of not. Print a message to standard error when invalid.
         * 
         * @return {@code true} when valid, {@code false} otherwise.
         */
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

        /**
         * The next {@code GomokoPiece} to be placed.
         */
        public GomokoPiece getCurrentPiece()
        {
            return currentPiece;
        }

        /**
         * Place a Gomoko piece and check whether the move will ends the game.
         * 
         * @return The winning piece if the move ends the game, {@code null} otherwise.
         */
        public GomokoPiece placePiece(int row, int column)
        {
            if (board[row][column] != GomokoPiece.EMPTY)
            {
                throw new IllegalArgumentException(String.format("board[%d][%d] was not empty.", row, column));
            }

            // Place the Gomoko piece and switch the piece to be placed next.
            board[row][column] = currentPiece;
            currentPiece = (currentPiece == GomokoPiece.BLACK) ? GomokoPiece.WHITE : GomokoPiece.BLACK;

            // Check every possible combinations that include this coordinate.
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                GomokoPiece winner;
                
                winner = checkHorizontalWinner(row, column - i);
                if (winner != null) { return winner; }

                winner = checkVerticalWinner(row - i, column);
                if (winner != null) { return winner; }

                winner = checkDiagonalLeftWinner(row - i, column + i);
                if (winner != null) { return winner; }

                winner = checkDiagonalRightWinner(row - i, column - i);
                if (winner != null) { return winner; }
            }

            return null;
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

            return GomokoPiece.fromTotalScore(score);
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

            return GomokoPiece.fromTotalScore(score);
        }

        @Nullable
        private GomokoPiece checkDiagonalLeftWinner(int row, int column)
        {
            if ((row < 0) || ((row + PIECES_TO_WIN - 1) >= BOARD_SIZE) || ((column - PIECES_TO_WIN + 1) < 0) || (column >= BOARD_SIZE)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                score += board[row + i][column - i].getScore();
            }

            return GomokoPiece.fromTotalScore(score);
        }

        @Nullable
        private GomokoPiece checkDiagonalRightWinner(int row, int column)
        {
            if ((row < 0) || ((row + PIECES_TO_WIN - 1) >= BOARD_SIZE) || (column < 0) || ((column + PIECES_TO_WIN - 1) >= BOARD_SIZE)) { return null; }

            int score = 0;
            for (int i = 0; i < PIECES_TO_WIN; i++)
            {
                score += board[row + i][column + i].getScore();
            }

            return GomokoPiece.fromTotalScore(score);
        }

        /**
         * Print the Gomoko game board to standard output.
         */
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
    }
}
