package players.impl;

import game.Color;
import game.IGameState;
import game.IMove;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxAIPlayer extends AIPlayer {
    private static final int DEFAULT_DEPTH = 4;

    private Color color;
    private int depth;

    public MiniMaxAIPlayer(Color color) {
        this(color, DEFAULT_DEPTH);
    }

    public MiniMaxAIPlayer(Color color, int depth) {
        this.color = color;
        this.depth = depth;
    }

    @Override
    protected IMove chooseBestMove(IGameState gameState) {
        if (color == Color.WHITE) {
            return maxLevel(gameState, 1, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
        }
        else {
            return minLevel(gameState, 1, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
        }
    }



    public MoveEval maxLevel(IGameState gameState, int step, int alpha, int beta) {
        List<IMove> possibleMoves = gameState.getPossibleMoves();
        MoveEval result = new MoveEval(null, Integer.MIN_VALUE);
        for (IMove move : possibleMoves) {
            IGameState nextGameState = gameState.makeTurn(move);
            int eval;
            if (nextGameState.getGameStatus().isOver()) {
                eval = nextGameState.evaluate();
            }
            else {
                eval = (step == depth) ? nextGameState.evaluate() : minLevel(nextGameState, step+1, alpha, beta).eval;
            }

            if (eval > result.eval) {
                result.move = move;
                result.eval = eval;
            }

            if (result.eval >= beta) {
                return result;
            }

            alpha = Math.max(alpha, result.eval);
        }
        return result;
    }

    public MoveEval minLevel(IGameState gameState, int step, int alpha, int beta) {
        List<IMove> possibleMoves = gameState.getPossibleMoves();
        MoveEval result = new MoveEval(null, Integer.MAX_VALUE);
        for (IMove move : possibleMoves) {
            IGameState nextGameState = gameState.makeTurn(move);
            int eval;
            if (nextGameState.getGameStatus().isOver()) {
                eval = nextGameState.evaluate();
            }
            else {
                eval = (step == depth) ? nextGameState.evaluate() : maxLevel(nextGameState, step+1, alpha, beta).eval;
            }

            if (eval < result.eval) {
                result.move = move;
                result.eval = eval;
            }

            if (result.eval <= alpha) {
                return result;
            }

            beta = Math.min(result.eval, beta);
        }
        return result;
    }

    static class MoveEval {
        IMove move;
        int eval;

        MoveEval(IMove move, int eval) {
            this.move = move;
            this.eval = eval;
        }
    }

    @Override
    public String toString() {
        return "AI Player : MiniMax Player (depth = " + depth + ")";
    }
}
