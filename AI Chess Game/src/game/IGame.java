package game;

public interface IGame {
    void init();
    void makeTurn();
    boolean isOver();
    GameStatus getGameState();
    void showPosition();

    String getNotation();
}
