package players.impl;

import game.IGameState;
import game.IMove;
import players.IPlayer;

import java.util.List;

public abstract class HumanPlayer implements IPlayer {
    public IMove chooseTurn(IGameState gameState) {
        List<IMove> possibleMoves = gameState.getPossibleMoves();
        while(true) {
            IMove chosenMove = getHumanMove();
            if (possibleMoves.contains(chosenMove)) {
                return chosenMove;
            }
            System.err.println("Invalid move is chosen");
        }
    }

    protected abstract IMove getHumanMove();
}
