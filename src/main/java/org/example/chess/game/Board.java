package org.example.chess.game;

import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;
import org.example.chess.model.PieceType;
import org.example.chess.model.Position;

public class Board {
    private static final int SIZE = 8;
    private final Piece[][] squares = new Piece[SIZE][SIZE];

    public void setupInitialPosition() {
        clear();
        setupBackRank(0, PieceColor.BLACK);
        setupPawns(1, PieceColor.BLACK);

        setupPawns(6, PieceColor.WHITE);
        setupBackRank(7, PieceColor.WHITE);
    }

    public Piece getPiece(Position position) {
        return squares[position.row()][position.col()];
    }

    public boolean isEmpty(Position position) {
        return getPiece(position) == null;
    }

    public boolean isOccupied(Position position) {
        return !isEmpty(position);
    }

    public boolean isOccupiedByColor(Position position, PieceColor color) {
        Piece piece = getPiece(position);
        return piece != null && piece.color() == color;
    }

    public boolean isPathClear(Position from, Position to) {
        int rowStep = Integer.compare(to.row(), from.row());
        int colStep = Integer.compare(to.col(), from.col());

        int row = from.row() + rowStep;
        int col = from.col() + colStep;

        while (row != to.row() || col != to.col()) {
            if (squares[row][col] != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return true;
    }

    public void setPiece(Position position, Piece piece) {
        squares[position.row()][position.col()] = piece;
    }

    public Piece move(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece == null) {
            throw new IllegalStateException("No piece at " + from);
        }

        Piece captured = getPiece(to);
        setPiece(to, piece);
        setPiece(from, null);
        return captured;
    }

    public String toAscii() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            builder.append(8 - row).append(' ');
            for (int col = 0; col < SIZE; col++) {
                Piece piece = squares[row][col];
                builder.append(piece == null ? "." : piece.shortName()).append(' ');
            }
            builder.append(System.lineSeparator());
        }
        builder.append("  a b c d e f g h");
        return builder.toString();
    }

    private void clear() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = null;
            }
        }
    }

    private void setupBackRank(int row, PieceColor color) {
        setPiece(Position.of(row, 0), new Piece(PieceType.ROOK, color));
        setPiece(Position.of(row, 1), new Piece(PieceType.KNIGHT, color));
        setPiece(Position.of(row, 2), new Piece(PieceType.BISHOP, color));
        setPiece(Position.of(row, 3), new Piece(PieceType.QUEEN, color));
        setPiece(Position.of(row, 4), new Piece(PieceType.KING, color));
        setPiece(Position.of(row, 5), new Piece(PieceType.BISHOP, color));
        setPiece(Position.of(row, 6), new Piece(PieceType.KNIGHT, color));
        setPiece(Position.of(row, 7), new Piece(PieceType.ROOK, color));
    }

    private void setupPawns(int row, PieceColor color) {
        for (int col = 0; col < SIZE; col++) {
            setPiece(Position.of(row, col), new Piece(PieceType.PAWN, color));
        }
    }
}
