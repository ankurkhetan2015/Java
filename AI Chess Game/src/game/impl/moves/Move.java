package game.impl.moves;

import game.ILocation;
import pieces.IPiece;

public class Move extends SimpleMove {
    public Move(IPiece piece, ILocation from, ILocation to) {
        super(piece, from, to);
    }

    @Override
    public String toString() {
        String result = getPiece().getNotationView() + " " + getFrom().toString() + " - " + getTo().toString();
        return result;
    }
}
