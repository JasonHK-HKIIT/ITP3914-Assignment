/**
 * GomokoPiece.java - Part of a Gomoko game.
 * 
 * @author Kwok Chi Leong (240141706, Class 1A)
 */

 package dev.jasonhk.hkiit.itp3914.assignment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The Gomoko pieces.
 */
enum GomokoPiece
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
        winningScore = score * Gomoko.PIECES_TO_WIN;
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
