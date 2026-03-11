package org.example.chess.model;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Position of(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Position out of board bounds: (" + row + ", " + col + ")");
        }
        return new Position(row, col);
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public int deltaRowTo(Position other) {
        return other.row - row;
    }

    public int deltaColTo(Position other) {
        return other.col - col;
    }

    public static Position fromAlgebraic(String value) {
        if (value == null || value.length() != 2) {
            throw new IllegalArgumentException("Invalid square: " + value);
        }

        char file = Character.toLowerCase(value.charAt(0));
        char rank = value.charAt(1);
        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            throw new IllegalArgumentException("Invalid square: " + value);
        }

        int col = file - 'a';
        int row = 8 - Character.getNumericValue(rank);
        return Position.of(row, col);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position other)) {
            return false;
        }
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        char file = (char) ('a' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }
}
