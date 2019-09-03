package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;

public class Queen extends CommonPiece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation to) {
        return Queen.canQueenMoveTowards(board, from, to);
    }


    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "Q" : "q";
    }

    @Override
    public String getNotationView() {
        return "Q";
    }

    @Override
    public int evaluate() {
        return 0;
    }

    public static boolean canQueenMoveTowards(IBoard board, ILocation from, ILocation to) {
        return Rook.canRookMoveTowards(board, from, to) || Bishop.canBishopMoveTowards(board, from, to);
    }
}
