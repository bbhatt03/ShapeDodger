package game;

public class ScoreTracker {
	private int score;

    public ScoreTracker() {
        this.score = 0;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }
}
