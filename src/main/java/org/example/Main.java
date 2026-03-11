package org.example;

import org.example.chess.game.ChessGame;
import org.example.chess.game.MoveParser;
import org.example.chess.model.Move;
import org.example.chess.model.Piece;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chess CLI ready. Enter moves like e2e4. Type 'exit' to quit.");
        while (true) {
            System.out.println();
            System.out.println(game.board().toAscii());
            System.out.print(game.turn() + " to move > ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                Move move = MoveParser.parse(input);
                Piece captured = game.applyMove(move);
                if (captured == null) {
                    System.out.println("Moved " + move.from() + " -> " + move.to());
                } else {
                    System.out.println("Moved " + move.from() + " -> " + move.to() + ", captured " + captured.shortName());
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        System.out.println("Bye.");
    }
}
