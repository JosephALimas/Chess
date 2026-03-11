package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Position;

public final class MoveParser {
    private MoveParser() {
    }

    public static Move parse(String rawMove) {
        if (rawMove == null) {
            throw new IllegalArgumentException("Move input cannot be null.");
        }

        String value = rawMove.trim();
        if (!value.matches("(?i)^[a-h][1-8][a-h][1-8]$")) {
            throw new IllegalArgumentException("Invalid move format: " + rawMove + ". Use format like e2e4.");
        }

        Position from = Position.fromAlgebraic(value.substring(0, 2));
        Position to = Position.fromAlgebraic(value.substring(2, 4));
        return new Move(from, to);
    }
}
