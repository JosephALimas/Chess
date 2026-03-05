package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;

public class ChessGame {
    private final Board board;
    private PieceColor turn;

    public ChessGame() {
        this.board = new Board();
        this.board.setupInitialPosition();
        this.turn = PieceColor.WHITE;
    }

    public Board board() {
        return board;
    }

    public PieceColor turn() {
        return turn;
    }

    public Piece applyMove(Move move) {
        Piece movingPiece = board.getPiece(move.from());
        if (movingPiece == null) {
            throw new IllegalStateException("No piece at " + move.from());
        }
        if (movingPiece.color() != turn) {
            throw new IllegalStateException("It is " + turn + " turn.");
        }

        Piece captured = board.move(move.from(), move.to());
        turn = (turn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        return captured;
    }
}
