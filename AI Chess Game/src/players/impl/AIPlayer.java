package players.impl;

import game.IGameState;
import game.IMove;
import players.IPlayer;

public abstract class AIPlayer implements IPlayer {
    public IMove chooseTurn(IGameState gameState) {
        return chooseBestMove(gameState);
    }

    protected abstract IMove chooseBestMove(IGameState gameState);
}
