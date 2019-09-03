package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.impl.Location;

public class Rook extends CommonPiece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation to) {
        return Rook.canRookMoveTowards(board, from, to);
    }

    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "R" : "r";
    }

    @Override
    public String getNotationView() {
        return "R";
    }

    @Override
    public int evaluate() {
        return 4;
    }

    public static boolean canRookMoveTowards(IBoard board, ILocation from, ILocation to) {
        if ((from.getRow() == to.getRow()) && (from.getCol() != to.getCol())) {
            int length = Math.abs(to.getCol() - from.getCol());
            int colStep = (to.getCol() - from.getCol()) / length;
            for (int i = 1; i < length; i++) {
                Location location = new Location(from.getRow(), from.getCol() + i*colStep);
                if (board.getPiece(location) != null) {
                    return false;
                }
            }
            return true;
        }
        else if ((from.getCol() == to.getCol()) && (from.getRow() != to.getRow())) {
            int length = Math.abs(to.getRow() - from.getRow());
            int rowStep = (to.getRow() - from.getRow()) / length;
            for (int i = 1; i < length; i++) {
                Location location = new Location(from.getRow() + i*rowStep, from.getCol());
                if (board.getPiece(location) != null) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
}
