package game;

import game.impl.moves.*;
import pieces.IPiece;
import pieces.impl.Pawn;

import java.util.List;

public interface IMove {
    List<ILocation[]> getPositionChanges();

    static IMove parseMove(String s) {
        String[] tokens = s.split(" ");
        IPiece piece = IPiece.parsePiece(tokens[0]);

        if (tokens.length == 2) {
            if (tokens[1].equals("0-0")) {
                return new Castling(piece.getColor(), Castling.Type.SHORT);
            }
            if (tokens[1].equals("0-0-0")) {
                return new Castling(piece.getColor(), Castling.Type.LONG);
            }
            throw new IllegalStateException();
        }


        ILocation from = ILocation.parseLocation(tokens[1]);
        ILocation to = ILocation.parseLocation(tokens[3]);

        assert tokens[2].equals("x") || tokens[2].equals("-");

        IPiece promotion = null;
        if (piece instanceof Pawn) {
            if (tokens.length == 5) {
                promotion = IPiece.parsePiece(tokens[4]);
                if (promotion instanceof Pawn) {
                    throw new IllegalStateException("Pawn must promote to another piece");
                }
                if (piece.getColor() != promotion.getColor()) {
                    throw new IllegalStateException("Pawn must promote to the piece of the same color");
                }

                if (tokens[2].equals("x")) {
                    return new PawnCapturePromotion(piece, from, to, promotion);
                }
                else {
                    return new PawnMovePromotion(piece, from, to, promotion);
                }
            }

            if ((from.getCol() == to.getCol()) && (Math.abs(from.getRow() - to.getRow()) == 2)) {
                return new PawnStartJump(piece, from, to, from.getCol());
            }
        }
        if (tokens[2].equals("x")) {
            return new CaptureMove(piece, from, to);
        }
        else {
            return new Move(piece, from, to);
        }

    }
}
