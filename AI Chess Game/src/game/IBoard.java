package game;

import game.impl.CastlingInfo;
import pieces.IPiece;

import java.util.List;

public interface IBoard extends Iterable<IPiece> {
    int BOARD_WIDTH = 8;
    int BOARD_HEIGHT = 8;

    void init();

    IPiece getPiece(ILocation location);

    IBoard makeTurn(IMove move);

    List<IMove> getPossibleMoves(GameStatus currentStatus);
    GameStatus updateGameStatus(GameStatus previousStatus);

    boolean isCheck(Color color);
    boolean isCheckmate(Color color);

    CastlingInfo getCastlingInfo();
    Integer getEnPassant();
}
