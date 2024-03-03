package game;

/**
 * The ScoreTracker class represents a tracker for the player's score in the game.
 * It keeps track of the player's current score and provides methods to increase the score.
 */
public class ScoreTracker {
    private int score;

    /**
     * Constructs a new ScoreTracker object with an initial score of zero.
     */
    public ScoreTracker() {
        this.score = 0;
    }

    /**
     * Increases the player's score by the specified number of points.
     *
     * @param points the number of points to increase the score by
     */
    public void increaseScore(int points) {
        score += points;
    }

    /**
     * Retrieves the current score of the player.
     *
     * @return the current score of the player
     */
    public int getScore() {
        return score;
    }
}
