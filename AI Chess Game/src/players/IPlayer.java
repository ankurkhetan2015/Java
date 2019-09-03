package players;

import game.IGameState;
import game.IMove;

public interface IPlayer {
    IMove chooseTurn(IGameState gameState);
}
