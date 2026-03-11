package org.example.chess.game;

import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;
import org.example.chess.model.PieceType;
import org.example.chess.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {
    @Test
    void setupInitialPositionPlacesMainPieces() {
        Board board = new Board();
        board.setupInitialPosition();

        assertEquals(new Piece(PieceType.KING, PieceColor.WHITE), board.getPiece(Position.fromAlgebraic("e1")));
        assertEquals(new Piece(PieceType.KING, PieceColor.BLACK), board.getPiece(Position.fromAlgebraic("e8")));
        assertEquals(new Piece(PieceType.PAWN, PieceColor.WHITE), board.getPiece(Position.fromAlgebraic("a2")));
        assertEquals(new Piece(PieceType.PAWN, PieceColor.BLACK), board.getPiece(Position.fromAlgebraic("h7")));
        assertNull(board.getPiece(Position.fromAlgebraic("e4")));
    }

    @Test
    void moveRelocatesPieceAndReturnsCapturedWhenPresent() {
        Board board = new Board();
        Position from = Position.fromAlgebraic("a2");
        Position to = Position.fromAlgebraic("a4");
        Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        board.setPiece(from, pawn);

        Piece captured = board.move(from, to);
        assertNull(captured);
        assertNull(board.getPiece(from));
        assertEquals(pawn, board.getPiece(to));
    }

    @Test
    void moveCapturesPieceOnTargetSquare() {
        Board board = new Board();
        Position from = Position.fromAlgebraic("b4");
        Position to = Position.fromAlgebraic("c5");
        Piece attacker = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece victim = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        board.setPiece(from, attacker);
        board.setPiece(to, victim);

        Piece captured = board.move(from, to);
        assertEquals(victim, captured);
        assertEquals(attacker, board.getPiece(to));
    }

    @Test
    void moveThrowsWhenSourceIsEmpty() {
        Board board = new Board();
        assertThrows(IllegalStateException.class,
                () -> board.move(Position.fromAlgebraic("a1"), Position.fromAlgebraic("a2")));
    }

    @Test
    void pathCheckDetectsBlockersForSlidingPieces() {
        Board board = new Board();
        Position from = Position.fromAlgebraic("a1");
        Position blocker = Position.fromAlgebraic("a2");
        Position to = Position.fromAlgebraic("a4");
        board.setPiece(from, new Piece(PieceType.ROOK, PieceColor.WHITE));

        assertTrue(board.isPathClear(from, to));
        board.setPiece(blocker, new Piece(PieceType.PAWN, PieceColor.WHITE));
        assertFalse(board.isPathClear(from, to));
    }
}
