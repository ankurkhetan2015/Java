package pieces;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.IMove;
import pieces.impl.*;

import java.util.List;

public interface IPiece {
    IPiece[] PIECES_TYPES = new IPiece[]{
            new King(Color.WHITE),
            new Queen(Color.WHITE),
            new Rook(Color.WHITE),
            new Bishop(Color.WHITE),
            new Knight(Color.WHITE),
            new Pawn(Color.WHITE),
            new King(Color.BLACK),
            new Queen(Color.BLACK),
            new Rook(Color.BLACK),
            new Bishop(Color.BLACK),
            new Knight(Color.BLACK),
            new Pawn(Color.BLACK)};

    Color getColor();

    List<IMove> getPossibleMoves(IBoard board, ILocation from);
    boolean controls(IBoard board, ILocation from, ILocation target);

    String getTextView();
    String getNotationView();

    int evaluate();

    static IPiece parsePiece(String s) {
        assert s.length() == 1;
        for (IPiece piece : PIECES_TYPES) {
            if (piece.getTextView().equals(s)) {
                return piece;
            }
        }
        return null;
    }
}
