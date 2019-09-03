package game.impl.moves;

import game.ILocation;
import pieces.IPiece;

public class PawnStartJump extends Move {
    private int column;

    public PawnStartJump(IPiece piece, ILocation from, ILocation to, int column) {
        super(piece, from, to);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
