package pieces.impl;

import game.Color;
import game.IBoard;
import game.ILocation;

public class Knight extends CommonPiece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean controls(IBoard board, ILocation from, ILocation to) {
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());
        return ((rowDiff == 2) && (colDiff == 1)) ||
                ((colDiff == 2) && (rowDiff == 1));
    }

    @Override
    public String getTextView() {
        return (getColor() == Color.WHITE) ? "N" : "n";
    }

    @Override
    public String getNotationView() {
        return "N";
    }

    @Override
    public int evaluate() {
        return 3;
    }
}
