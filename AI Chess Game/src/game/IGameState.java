package game;

import java.util.List;

public interface IGameState {
    void init();
    List<IMove> getPossibleMoves();
    IGameState makeTurn(IMove move);
    GameStatus getGameStatus();
    int evaluate();
}
