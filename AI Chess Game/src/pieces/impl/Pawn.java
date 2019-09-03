package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.IMove;
import game.impl.*;
import game.impl.moves.*;
import pieces.IPiece;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<IMove> getPossibleMoves(IBoard board, ILocation from) {
        List<IMove> result = new ArrayList<>();
        for (int r = 0; r<IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c<IBoard.BOARD_WIDTH; c++) {
                ILocation to = new Location(r,c);
                if (canMove(board, from, to)) {
                    if (Math.abs(from.getRow() - to.getRow()) == 2) {
                        result.add(new PawnStartJump(this, from, to, from.getCol()));
                    }
                    else {
                        if (((getColor() == Color.WHITE) && (to.getRow() == IBoard.BOARD_HEIGHT - 1)) ||
                                ((getColor() == Color.BLACK) && (to.getRow() == 0))) {
                            for (IPiece piece : IPiece.PIECES_TYPES) {
                                if ((piece instanceof Pawn) || (piece instanceof King)) {
                                    continue;
                                }
                                if (piece.getColor() == getColor()) {
                                    result.add(new PawnMovePromotion(this, from, to, piece));
                                }
                            }
                        }
                        else {
                            result.add(new Move(this, from, to));
                        }
                    }
                }
                if (canCapture(board, from, to)) {
                    if (((getColor() == Color.WHITE) && (to.getRow() == IBoard.BOARD_HEIGHT - 1)) ||
                            ((getColor() == Color.BLACK) && (to.getRow() == 0))) {
                        for (IPiece piece : IPiece.PIECES_TYPES) {
                            if ((piece instanceof Pawn) || (piece instanceof King)) {
                                continue;
                            }
                            if (piece.getColor() == getColor()) {
                                result.add(new PawnCapturePromotion(this, from, to, piece));
                            }
                        }
                    }
                    else {
                        result.add(new CaptureMove(this, from, to));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean canMove(IBoard board, ILocation from, ILocation to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        if (colDiff == 0) {
            if (getColor() == Color.WHITE) {
                if (rowDiff == 1) {
                    return (board.getPiece(to) == null) && (super.canMove(board, from, to));
                }
                if ((rowDiff == 2) && (from.getRow() == 1)) {
                    Location temp = new Location(from.getRow() + 1, from.getCol());
                    return (board.getPiece(temp) == null) && (board.getPiece(to) == null) && (super.canMove(board, from, to));
                }
            }
            else {
                if (rowDiff == -1) {
                    return (board.getPiece(to) == null) && (super.canMove(board, from, to));
                }
                if ((rowDiff == -2) && (from.getRow() == 6)) {
                    Location temp = new Location(from.getRow() - 1, from.getCol());
                    return (board.getPiece(temp) == null) && (board.getPiece(to) == null) && (super.canMove(board, from, to));
                }
            }
        }
        return false;
    }


    @Override
    public boolean canCapture(IBoard board, ILocation from, ILocation to) {
        if (!controls(board, from, to)) {
            return false;
        }

        IPiece targetPiece = board.getPiece(to);

        boolean enPassant = false;
        if ((targetPiece == null) && (board.getEnPassant() != null)) {
            if (getColor() == Color.WHITE) {
                if ((to.getRow() == 5) && (board.getEnPassant() == to.getCol())) {
                    ILocation pawnLocation = new Location(4, to.getCol());
                    IPiece pawnPiece =  board.getPiece(pawnLocation);
                    if ((pawnPiece instanceof Pawn) && (pawnPiece.getColor() == Color.BLACK)) {
                        enPassant = true;
                    }
                }
            }
            else {
                if ((to.getRow() == 2) && (board.getEnPassant() == to.getCol())) {
                    ILocation pawnLocation = new Location(3, to.getCol());
                    IPiece pawnPiece =  board.getPiece(pawnLocation);
                    if ((pawnPiece instanceof Pawn) && (pawnPiece.getColor() == Color.WHITE)) {
                        enPassant = true;
                    }
                }
            }
        }
        return (enPassant || ((targetPiece != null) && (targetPiece.getColor() != getColor()))) && (super.canCapture(board, from, to));
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation target) {
        int rowDiff = target.getRow() - from.getRow();
        int colDiff = target.getCol() - from.getCol();
        if (Math.abs(colDiff) == 1) {
            if (getColor() == Color.WHITE) {
                return (rowDiff == 1);
            }
            else {
                return (rowDiff == -1);
            }
        }
        return false;
    }

    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "P" : "p";
    }

    @Override
    public String getNotationView() {
        return " ";
    }

    @Override
    public int evaluate() {
        return 1;
    }
}

