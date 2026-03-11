package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;
import org.example.chess.model.PieceType;
import org.example.chess.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StandardMoveValidatorTest {
    private final MoveValidator validator = new StandardMoveValidator();

    @Test
    void pawnAllowsSingleAndDoubleStepFromStart() {
        Board board = new Board();
        Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Position e2 = pos("e2");
        board.setPiece(e2, whitePawn);

        assertDoesNotThrow(() -> validator.validate(board, move("e2", "e3"), whitePawn));
        assertDoesNotThrow(() -> validator.validate(board, move("e2", "e4"), whitePawn));
    }

    @Test
    void pawnCannotDoubleStepWhenNotOnStartRow() {
        Board board = new Board();
        Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        board.setPiece(pos("e3"), whitePawn);

        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("e3", "e5"), whitePawn));
    }

    @Test
    void pawnCapturesDiagonallyOnlyWhenTargetOccupied() {
        Board board = new Board();
        Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
        board.setPiece(pos("e4"), whitePawn);
        board.setPiece(pos("f5"), new Piece(PieceType.KNIGHT, PieceColor.BLACK));

        assertDoesNotThrow(() -> validator.validate(board, move("e4", "f5"), whitePawn));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("e4", "d5"), whitePawn));
    }

    @Test
    void rookRequiresStraightLineAndClearPath() {
        Board board = new Board();
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        board.setPiece(pos("a1"), rook);

        assertDoesNotThrow(() -> validator.validate(board, move("a1", "a4"), rook));

        board.setPiece(pos("a2"), new Piece(PieceType.PAWN, PieceColor.WHITE));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("a1", "a4"), rook));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("a1", "b2"), rook));
    }

    @Test
    void bishopRequiresDiagonalAndClearPath() {
        Board board = new Board();
        Piece bishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        board.setPiece(pos("c1"), bishop);

        assertDoesNotThrow(() -> validator.validate(board, move("c1", "h6"), bishop));

        board.setPiece(pos("d2"), new Piece(PieceType.PAWN, PieceColor.WHITE));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("c1", "h6"), bishop));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("c1", "c3"), bishop));
    }

    @Test
    void queenSupportsStraightAndDiagonalMoves() {
        Board board = new Board();
        Piece queen = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        board.setPiece(pos("d1"), queen);

        assertDoesNotThrow(() -> validator.validate(board, move("d1", "d5"), queen));
        assertDoesNotThrow(() -> validator.validate(board, move("d1", "h5"), queen));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("d1", "e3"), queen));
    }

    @Test
    void knightMovesInLAndCanJump() {
        Board board = new Board();
        Piece knight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        board.setPiece(pos("b1"), knight);
        board.setPiece(pos("b2"), new Piece(PieceType.PAWN, PieceColor.WHITE));
        board.setPiece(pos("c2"), new Piece(PieceType.PAWN, PieceColor.WHITE));

        assertDoesNotThrow(() -> validator.validate(board, move("b1", "c3"), knight));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("b1", "b3"), knight));
    }

    @Test
    void kingMovesOnlyOneSquare() {
        Board board = new Board();
        Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
        board.setPiece(pos("e1"), king);

        assertDoesNotThrow(() -> validator.validate(board, move("e1", "e2"), king));
        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("e1", "e3"), king));
    }

    @Test
    void cannotCaptureOwnPiece() {
        Board board = new Board();
        Piece rook = new Piece(PieceType.ROOK, PieceColor.WHITE);
        board.setPiece(pos("a1"), rook);
        board.setPiece(pos("a4"), new Piece(PieceType.PAWN, PieceColor.WHITE));

        assertThrows(InvalidMoveException.class,
                () -> validator.validate(board, move("a1", "a4"), rook));
    }

    private static Position pos(String square) {
        return Position.fromAlgebraic(square);
    }

    private static Move move(String from, String to) {
        return new Move(pos(from), pos(to));
    }
}
