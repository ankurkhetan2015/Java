package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.impl.Location;

public class Bishop extends CommonPiece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation to) {
        return Bishop.canBishopMoveTowards(board, from, to);
    }

    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "B" : "b";
    }

    @Override
    public String getNotationView() {
        return "B";
    }

    @Override
    public int evaluate() {
        return 3;
    }

    public static boolean canBishopMoveTowards(IBoard board, ILocation from, ILocation to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if ((rowDiff == 0) || (colDiff == 0)) {
            return false;
        }

        if (rowDiff == colDiff) {
            int length = Math.abs(rowDiff);
            int step = rowDiff / length;
            for (int i = 1; i < length; i++) {
                Location location = new Location(from.getRow() + i * step, from.getCol() + i*step);
                if (board.getPiece(location) != null) {
                    return false;
                }
            }
            return true;
        }
        else if (rowDiff == -colDiff) {
            int length = Math.abs(rowDiff);
            int step = rowDiff / length;
            for (int i = 1; i < length; i++) {
                Location location = new Location(from.getRow() + i * step, from.getCol() - i*step);
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
