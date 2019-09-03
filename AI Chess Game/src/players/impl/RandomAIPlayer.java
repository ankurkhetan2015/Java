package players.impl;

import game.IGameState;
import game.IMove;

import java.util.List;
import java.util.Random;

public class RandomAIPlayer extends AIPlayer {
    @Override
    protected IMove chooseBestMove(IGameState gameState) {
        List<IMove> possibleMoves = gameState.getPossibleMoves();
        Random r = new Random();
        return possibleMoves.get(r.nextInt(possibleMoves.size()));
    }

    @Override
    public String toString() {
        return "AI Player : Random Possible Move";
    }
}
