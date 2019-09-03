package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.IMove;
import game.impl.moves.CaptureMove;
import game.impl.Location;
import game.impl.moves.Move;
import pieces.IPiece;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonPiece extends Piece {

    public CommonPiece(Color color) {
        super(color);
    }

    @Override
    public List<IMove> getPossibleMoves(IBoard board, ILocation from) {
        List<IMove> result = new ArrayList<>();
        for (int r = 0; r<IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c<IBoard.BOARD_WIDTH; c++) {
                ILocation to = new Location(r,c);
                if (canMove(board, from, to)) {
                    result.add(new Move(this, from, to));
                }
                if (canCapture(board, from, to)) {
                    result.add(new CaptureMove(this, from, to));
                }
            }
        }
        return result;
    }

    @Override
    public boolean canMove(IBoard board, ILocation from, ILocation to) {
        if (!controls(board, from, to)) {
            return false;
        }

        return (board.getPiece(to) == null) && (super.canMove(board, from, to));

    }

    @Override
    public boolean canCapture(IBoard board, ILocation from, ILocation to) {
        if (!controls(board, from, to)) {
            return false;
        }

        IPiece targetPiece = board.getPiece(to);
        return (targetPiece != null) && (targetPiece.getColor() != getColor()) && (super.canCapture(board, from, to));
    }
}
