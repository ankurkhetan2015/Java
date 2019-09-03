package game.impl;

import game.*;
import pieces.IPiece;

import java.util.List;

public class GameState implements IGameState {
    private IBoard board;
    private GameStatus gameStatus;



    public GameState() {
        board = new Board();
    }

    public GameState(IBoard board, GameStatus gameStatus) {
        this.board = board;
        this.gameStatus = gameStatus;
    }

    @Override
    public void init() {
        board.init();
        gameStatus = GameStatus.WHITE_TURN;
    }

    @Override
    public List<IMove> getPossibleMoves() {
        return board.getPossibleMoves(gameStatus);
    }

    @Override
    public IGameState makeTurn(IMove move) {
        assert gameStatus == GameStatus.WHITE_TURN
                || gameStatus == GameStatus.BLACK_TURN;

        IBoard newBoard = board.makeTurn(move);
        GameStatus newGameStatus = newBoard.updateGameStatus(gameStatus);

        return new GameState(newBoard, newGameStatus);
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public int evaluate() {
        if (gameStatus == GameStatus.WHITE_WON) {
            return 1000000;
        }
        if (gameStatus == GameStatus.BLACK_WON) {
            return -1000000;
        }
        if (gameStatus == GameStatus.DRAW) {
            return 0;
        }

        int eval = 0;
        for (IPiece piece : board) {
            if (piece == null) {
                continue;
            }

            if (piece.getColor() == Color.WHITE) {
                eval += piece.evaluate();
            }
            else {
                eval -= piece.evaluate();
            }
        }
        return eval;
    }

    @Override
    public String toString() {
        return board.toString();
    }

}
