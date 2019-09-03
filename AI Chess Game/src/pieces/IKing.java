package pieces;

import game.IBoard;
import game.ILocation;

public interface IKing extends IPiece {
    boolean isCheck(IBoard board, ILocation kingLocation);
    boolean isCheckMate(IBoard board, ILocation kingLocation);
}
