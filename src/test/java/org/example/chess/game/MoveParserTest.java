package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveParserTest {
    @Test
    void parsesCoordinateMove() {
        Move move = MoveParser.parse("e2e4");
        assertEquals(new Move(Position.fromAlgebraic("e2"), Position.fromAlgebraic("e4")), move);
    }

    @Test
    void rejectsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> MoveParser.parse("e2-e4"));
        assertThrows(IllegalArgumentException.class, () -> MoveParser.parse("hello"));
    }
}
