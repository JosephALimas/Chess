package org.example.chess.game;

import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;
import org.example.chess.model.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChessGameTest {
    @Test
    void whiteStartsAndTurnAlternatesAfterValidMove() {
        ChessGame game = new ChessGame();
        assertEquals(PieceColor.WHITE, game.turn());

        game.applyMove(MoveParser.parse("e2e4"));
        assertEquals(PieceColor.BLACK, game.turn());
    }

    @Test
    void cannotMoveOpponentPieceOnYourTurn() {
        ChessGame game = new ChessGame();
        InvalidMoveException exception = assertThrows(
                InvalidMoveException.class,
                () -> game.applyMove(MoveParser.parse("e7e5"))
        );

        assertEquals("It is WHITE turn.", exception.getMessage());
        assertEquals(PieceColor.WHITE, game.turn());
    }

    @Test
    void sourceSquareMustContainPiece() {
        ChessGame game = new ChessGame();
        InvalidMoveException exception = assertThrows(
                InvalidMoveException.class,
                () -> game.applyMove(MoveParser.parse("e4e5"))
        );

        assertEquals("No piece at e4.", exception.getMessage());
        assertEquals(PieceColor.WHITE, game.turn());
    }

    @Test
    void illegalMoveIsRejectedAndTurnDoesNotChange() {
        ChessGame game = new ChessGame();
        assertThrows(InvalidMoveException.class, () -> game.applyMove(MoveParser.parse("e2e5")));
        assertEquals(PieceColor.WHITE, game.turn());
    }

    @Test
    void captureReturnsCapturedPiece() {
        ChessGame game = new ChessGame();

        assertNull(game.applyMove(MoveParser.parse("e2e4")));
        assertNull(game.applyMove(MoveParser.parse("d7d5")));
        Piece captured = game.applyMove(MoveParser.parse("e4d5"));

        assertEquals(new Piece(PieceType.PAWN, PieceColor.BLACK), captured);
        assertEquals(PieceColor.BLACK, game.turn());
    }
}
