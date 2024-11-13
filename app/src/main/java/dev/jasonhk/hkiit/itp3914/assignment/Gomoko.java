/**
 * Gomoko.java - Part of a Gomoko game.
 * 
 * @author Kwok Chi Leong (240141706, Class 1A)
 */

package dev.jasonhk.hkiit.itp3914.assignment;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Gomoko
{
    /**
     * The size of the {@link GomokoBoard}.
     */
    static final int BOARD_SIZE = 10;

    /**
     * The amount of consecutive-pieces needed to win the game.
     */
    static final int PIECES_TO_WIN = 4;

    /**
     * The entrypoint of the application.
     */
    public static void main(String[] args)
    {
        // The standard input scanner.
        var scanner = new Scanner(System.in);

        // The Gomoko game board.
        var board = new GomokoBoard();
        board.printBoard();

        while (true)
        {
            // Announce the game turn.
            System.out.printf("Player %s's turn.%n", board.getCurrentPiece().getPiece());

            // Receive inputs.
            System.out.print("Enter row and column (e.g., 0 1): ");
            int row, column;
            try
            {
                row = scanner.nextInt();
                column = scanner.nextInt();
            }
            catch (InputMismatchException ex)
            {
                scanner.nextLine();
                System.err.println("Invalid input. Try again.");
                continue;
            }

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
                break; //  Break out of the loop when the game was ended.
            }
        }

        scanner.close();
    }
}
