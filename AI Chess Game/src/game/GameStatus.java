package game;

public enum GameStatus {
    WHITE_TURN (false),
    BLACK_TURN (false),

    WHITE_WON (true),
    BLACK_WON (true),
    DRAW (true);

    private boolean isOver;

    GameStatus (boolean isOver) {
        this.isOver = isOver;
    }

    public boolean isOver() {
        return isOver;
    }
}