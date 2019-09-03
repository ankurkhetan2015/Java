package game.impl.moves;

import game.ILocation;
import pieces.IPiece;

public class CaptureMove extends SimpleMove {


    public CaptureMove(IPiece piece, ILocation from, ILocation to) {
        super(piece, from, to);
    }

    @Override
    public String toString() {
        String result = getPiece().getNotationView() + " " + getFrom().toString() + " x " + getTo().toString();
        return result;
    }
}
