package game.impl.moves;

import game.Color;
import game.IBoard;
import game.ILocation;
import game.IMove;
import game.impl.Location;
import pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Castling implements IMove {
    public enum Type { SHORT, LONG; }
    private Color color;
    private Type type;


    public Castling(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public List<ILocation[]> getPositionChanges() {
        List<ILocation[]> positionChanges = new ArrayList<>();
        ILocation[] kingChange = new ILocation[2];
        ILocation[] rookChange = new ILocation[2];

        if (color == Color.WHITE) {
            kingChange[0] = new Location(0,4);
            if (type == Type.SHORT) {
                rookChange[0] = new Location(0, 7);
            }
            else {
                rookChange[0] = new Location(0,0);
            }
        }
        else {
            kingChange[0] = new Location(7,4);
            if (type == Type.SHORT) {
                rookChange[0] = new Location(7, 7);
            }
            else {
                rookChange[0] = new Location(7,0);
            }
        }

        if (type == Type.SHORT) {
            kingChange[1] = new Location(kingChange[0].getRow(), kingChange[0].getCol() + 2);
            rookChange[1] = new Location(rookChange[0].getRow(), rookChange[0].getCol() - 2);
        }
        else {
            kingChange[1] = new Location(kingChange[0].getRow(), kingChange[0].getCol() - 2);
            rookChange[1] = new Location(rookChange[0].getRow(), rookChange[0].getCol() + 4);
        }


        positionChanges.add(kingChange);
        positionChanges.add(rookChange);
        return positionChanges;
    }

    public boolean isAllowed(IBoard board) {
        List<Location> uncontrol = new ArrayList<>();
        List<Location> empty = new ArrayList<>();
        if (color == Color.WHITE) {
            if (type == Type.SHORT) {
                uncontrol.add(new Location(0,5));
                uncontrol.add(new Location(0,6));
                empty.add(new Location(0, 5));
                empty.add(new Location(0, 6));
            }
            else {
                uncontrol.add(new Location(0,2));
                uncontrol.add(new Location(0,3));
                empty.add(new Location(0, 1));
                empty.add(new Location(0, 2));
                empty.add(new Location(0, 3));
            }
        }
        else {
            if (type == Type.SHORT) {
                uncontrol.add(new Location(7,5));
                uncontrol.add(new Location(7,6));
                empty.add(new Location(7, 5));
                empty.add(new Location(7, 6));
            }
            else {
                uncontrol.add(new Location(7, 2));
                uncontrol.add(new Location(7, 3));
                empty.add(new Location(7, 1));
                empty.add(new Location(7, 2));
                empty.add(new Location(7, 3));
            }
        }

        for (int r = 0; r < IBoard.BOARD_HEIGHT; r++) {
            for (int c = 0; c < IBoard.BOARD_WIDTH; c++) {
                ILocation location = new Location(r, c);
                IPiece piece = board.getPiece(location);
                if ((piece != null) && (piece.getColor() != color)) {
                    for (Location target : uncontrol) {
                        if (piece.controls(board, location, target)) {
                            return false;
                        }
                    }
                }

                for (Location target : empty) {
                    if (board.getPiece(target) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Castling castling = (Castling) o;
        return color == castling.color &&
                type == castling.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        if (type == Type.SHORT) {
            return "0-0";
        }
        else {
            return "0-0-0";
        }
    }
}
