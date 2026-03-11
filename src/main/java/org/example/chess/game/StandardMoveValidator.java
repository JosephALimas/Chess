package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Piece;
import org.example.chess.model.PieceColor;
import org.example.chess.model.Position;

public class StandardMoveValidator implements MoveValidator {
    @Override
    public void validate(Board board, Move move, Piece movingPiece) {
        Position from = move.from();
        Position to = move.to();

        if (from.equals(to)) {
            throw illegalMove(move, movingPiece, "Source and target must be different.");
        }
        if (board.isOccupiedByColor(to, movingPiece.color())) {
            throw illegalMove(move, movingPiece, "Cannot capture your own piece.");
        }

        int deltaRow = from.deltaRowTo(to);
        int deltaCol = from.deltaColTo(to);
        int absRow = Math.abs(deltaRow);
        int absCol = Math.abs(deltaCol);

        boolean legal = switch (movingPiece.type()) {
            case PAWN -> isLegalPawnMove(board, from, to, movingPiece.color(), deltaRow, deltaCol);
            case ROOK -> isRookMove(deltaRow, deltaCol) && board.isPathClear(from, to);
            case BISHOP -> absRow == absCol && board.isPathClear(from, to);
            case QUEEN -> (isRookMove(deltaRow, deltaCol) || absRow == absCol) && board.isPathClear(from, to);
            case KNIGHT -> (absRow == 2 && absCol == 1) || (absRow == 1 && absCol == 2);
            case KING -> absRow <= 1 && absCol <= 1;
        };

        if (!legal) {
            throw illegalMove(move, movingPiece, "Movement pattern is not legal.");
        }
    }

    private static boolean isLegalPawnMove(
            Board board,
            Position from,
            Position to,
            PieceColor color,
            int deltaRow,
            int deltaCol
    ) {
        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        int startRow = (color == PieceColor.WHITE) ? 6 : 1;

        if (deltaCol == 0 && deltaRow == direction && board.isEmpty(to)) {
            return true;
        }

        if (deltaCol == 0 && deltaRow == 2 * direction && from.row() == startRow) {
            Position middle = Position.of(from.row() + direction, from.col());
            return board.isEmpty(middle) && board.isEmpty(to);
        }

        return Math.abs(deltaCol) == 1 && deltaRow == direction && board.isOccupied(to);
    }

    private static boolean isRookMove(int deltaRow, int deltaCol) {
        return (deltaRow == 0 && deltaCol != 0) || (deltaRow != 0 && deltaCol == 0);
    }

    private InvalidMoveException illegalMove(Move move, Piece movingPiece, String reason) {
        return new InvalidMoveException(
                "Illegal move %s -> %s for %s %s. %s"
                        .formatted(move.from(), move.to(), movingPiece.color(), movingPiece.type(), reason)
        );
    }
}
