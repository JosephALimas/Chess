package org.example.chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceTest {
    @Test
    void shortNameForWhitePiecesIsUppercase() {
        assertEquals("K", new Piece(PieceType.KING, PieceColor.WHITE).shortName());
        assertEquals("Q", new Piece(PieceType.QUEEN, PieceColor.WHITE).shortName());
        assertEquals("R", new Piece(PieceType.ROOK, PieceColor.WHITE).shortName());
        assertEquals("B", new Piece(PieceType.BISHOP, PieceColor.WHITE).shortName());
        assertEquals("N", new Piece(PieceType.KNIGHT, PieceColor.WHITE).shortName());
        assertEquals("P", new Piece(PieceType.PAWN, PieceColor.WHITE).shortName());
    }

    @Test
    void shortNameForBlackPiecesIsLowercase() {
        assertEquals("k", new Piece(PieceType.KING, PieceColor.BLACK).shortName());
        assertEquals("q", new Piece(PieceType.QUEEN, PieceColor.BLACK).shortName());
        assertEquals("r", new Piece(PieceType.ROOK, PieceColor.BLACK).shortName());
        assertEquals("b", new Piece(PieceType.BISHOP, PieceColor.BLACK).shortName());
        assertEquals("n", new Piece(PieceType.KNIGHT, PieceColor.BLACK).shortName());
        assertEquals("p", new Piece(PieceType.PAWN, PieceColor.BLACK).shortName());
    }
}
