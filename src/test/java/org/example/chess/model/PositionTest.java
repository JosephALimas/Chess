package org.example.chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionTest {
    @Test
    void createsPositionInsideBoard() {
        Position position = Position.of(3, 4);
        assertEquals(3, position.row());
        assertEquals(4, position.col());
    }

    @Test
    void rejectsOutOfBoundsPosition() {
        assertThrows(IllegalArgumentException.class, () -> Position.of(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> Position.of(0, 8));
    }

    @Test
    void formatsToAlgebraic() {
        assertEquals("a8", Position.of(0, 0).toString());
        assertEquals("e4", Position.of(4, 4).toString());
        assertEquals("h1", Position.of(7, 7).toString());
    }

    @Test
    void parsesFromAlgebraic() {
        Position position = Position.fromAlgebraic("E2");
        assertEquals(Position.of(6, 4), position);
    }

    @Test
    void rejectsInvalidAlgebraicSquare() {
        assertThrows(IllegalArgumentException.class, () -> Position.fromAlgebraic("z9"));
        assertThrows(IllegalArgumentException.class, () -> Position.fromAlgebraic("e22"));
    }

    @Test
    void exposesDeltas() {
        Position from = Position.fromAlgebraic("c2");
        Position to = Position.fromAlgebraic("c4");
        assertEquals(-2, from.deltaRowTo(to));
        assertEquals(0, from.deltaColTo(to));
    }

    @Test
    void equalityAndHashCodeDependOnCoordinates() {
        Position left = Position.of(5, 2);
        Position same = Position.of(5, 2);
        Position other = Position.of(5, 3);

        assertEquals(left, same);
        assertEquals(left.hashCode(), same.hashCode());
        assertNotEquals(left, other);
    }
}
