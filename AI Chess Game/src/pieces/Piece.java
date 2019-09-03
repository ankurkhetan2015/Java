package pieces;


import game.Color;
import game.IBoard;
import game.ILocation;
import game.IMove;
import game.impl.moves.CaptureMove;
import game.impl.moves.Move;

import java.util.Objects;

public abstract class Piece implements IPiece {
    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    protected boolean canMove(IBoard board, ILocation from, ILocation to) {
        IMove move = new Move(board.getPiece(from), from, to);
        IBoard afterBoard = board.makeTurn(move);
        if (afterBoard.isCheck(color)) {
            return false;
        }
        return true;
    }

    protected boolean canCapture(IBoard board, ILocation from, ILocation to) {
        IMove move = new CaptureMove(board.getPiece(from), from, to);
        IBoard afterBoard = board.makeTurn(move);
        if (afterBoard.isCheck(color)) {
            return false;
        }
        return true;
    }

}
