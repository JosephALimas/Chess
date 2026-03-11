package org.example.chess.game;

import org.example.chess.model.Move;
import org.example.chess.model.Piece;

public interface MoveValidator {
    void validate(Board board, Move move, Piece movingPiece);
}
