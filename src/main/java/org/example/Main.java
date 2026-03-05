package org.example;

import org.example.chess.game.ChessGame;
import org.example.chess.model.Move;
import org.example.chess.model.Position;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();

        System.out.println("Tablero inicial:");
        System.out.println(game.board().toAscii());

        game.applyMove(new Move(Position.of(6, 4), Position.of(4, 4))); // e2 -> e4

        System.out.println();
        System.out.println("Después de e2-e4:");
        System.out.println(game.board().toAscii());
        System.out.println("Turno actual: " + game.turn());
    }
}
