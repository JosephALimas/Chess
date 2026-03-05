package org.example.chess.model;

public record Piece(PieceType type, PieceColor color) {

    public String shortName() {
        char symbol = switch (type) {
            case KING -> 'K';
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case PAWN -> 'P';
        };

        if (color == PieceColor.BLACK) {
            symbol = Character.toLowerCase(symbol);
        }
        return String.valueOf(symbol);
    }
}
