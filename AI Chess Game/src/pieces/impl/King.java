package pieces.impl;

import game.*;
import game.impl.moves.Castling;
import game.impl.CastlingInfo;
import game.impl.Location;
import pieces.IKing;
import pieces.IPiece;

import java.util.List;


public class King extends CommonPiece implements IKing {
    public King(Color color) {
        super(color);
    }


    @Override
    public List<IMove> getPossibleMoves(IBoard board, ILocation from) {
        List<IMove> result = super.getPossibleMoves(board, from);

        CastlingInfo castlingInfo = board.getCastlingInfo();
        if (getColor() == Color.WHITE) {
            if (castlingInfo.isWhiteShortAllowed()) {
                Castling castling = new Castling(getColor(), Castling.Type.SHORT);
                if (castling.isAllowed(board)) {
                    result.add(castling);
                }
            }

            if (castlingInfo.isWhiteLongAllowed()) {
                Castling castling = new Castling(getColor(), Castling.Type.LONG);
                if (castling.isAllowed(board)) {
                    result.add(castling);
                }
            }
        }
        else {
            if (castlingInfo.isBlackShortAllowed()) {
                Castling castling = new Castling(getColor(), Castling.Type.SHORT);
                if (castling.isAllowed(board)) {
                    result.add(castling);
                }
            }

            if (castlingInfo.isBlackLongAllowed()) {
                Castling castling = new Castling(getColor(), Castling.Type.LONG);
                if (castling.isAllowed(board)) {
                    result.add(castling);
                }
            }
        }
        return result;
    }

    @Override
    public boolean isCheck(IBoard board, ILocation kingLocation) {
        for (int r = 0; r < IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c < IBoard.BOARD_WIDTH; c++) {
                Location location = new Location(r, c);
                IPiece piece = board.getPiece(location);
                if (piece != null) {
                    if (piece.getColor() != getColor()) {
                        if (piece.controls(board, location, kingLocation)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isCheckMate(IBoard board, ILocation kingLocation) {
        if (!isCheck(board, kingLocation)) {
            return false;
        }

        List<IMove> possibleMoves = board.getPossibleMoves((getColor() == Color.WHITE) ? GameStatus.WHITE_TURN : GameStatus.BLACK_TURN);
        return possibleMoves.isEmpty();
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        return (Math.abs(rowDiff) <= 1) && (Math.abs(colDiff) <= 1) && ((rowDiff != 0) || (colDiff != 0));
    }

    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "K" : "k";
    }

    @Override
    public String getNotationView() {
        return "K";
    }

    @Override
    public int evaluate() {
        return 100;
    }
}
