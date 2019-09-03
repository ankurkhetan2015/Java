package game.impl.moves;

import game.ILocation;
import game.IMove;
import pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SimpleMove implements IMove {
    private IPiece piece;
    private ILocation from;
    private ILocation to;

    public SimpleMove(IPiece piece, ILocation from, ILocation to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }

    @Override
    public List<ILocation[]> getPositionChanges() {
        List<ILocation[]> positionChanges = new ArrayList<>();
        ILocation[] positionChange = new ILocation[2];
        positionChange[0] = from;
        positionChange[1] = to;
        positionChanges.add(positionChange);
        return positionChanges;
    }

    protected IPiece getPiece() {
        return piece;
    }

    protected ILocation getFrom() {
        return from;
    }

    protected ILocation getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMove that = (SimpleMove) o;
        return piece.equals(that.piece) &&
                from.equals(that.from) &&
                to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, from, to);
    }
}
