package game;

import game.impl.Location;

public interface ILocation {
    int getRow();
    int getCol();

    static ILocation parseLocation(String s) {
        if (s.length() != 2) {
            throw new IllegalStateException("Wrong location format");
        }

        int row = s.charAt(1) - '1';
        int col = s.charAt(0) - 'A';

        if ((row < 0) || (row > 7) || (col < 0) || (col > 7)) {
            throw new IllegalStateException("Location out of bounds");
        }

        return new Location(row, col);
    }
}
