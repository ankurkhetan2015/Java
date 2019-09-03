package players.impl;

import game.IMove;

import java.util.Scanner;

public class ConsolePlayer extends HumanPlayer {
    private Scanner scanner;

    public ConsolePlayer(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    protected IMove getHumanMove() {
        while (true) {
            System.out.println("Please enter your move ('help' for information):");
            String line = scanner.nextLine().trim();
            if ("help".equalsIgnoreCase(line)) {
                printHelp();
            }
            else {
                try {
                    IMove move = IMove.parseMove(line);
                    return move;
                }
                catch (IllegalStateException ise) {
                    System.err.println(ise.getMessage());
                }

            }

        }
    }

    @Override
    public String toString() {
        return "Human Player : Console";
    }

    public static void printHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("To make a move you enter a text command, representing your move");
        builder.append("To simply move your piece into empty cell, you must enter\n");
        builder.append("1. Symbol of your chosen piece (uppercase for whites and lowercase for blacks), i.e. 'N'\n");
        builder.append("2. Chess-style coordinates of its location, i.e 'G1'\n");
        builder.append("3. Symbol '-'\n");
        builder.append("4. Chess-style coordinates of destination cell, i.e 'F3'\n");
        builder.append("All of these tokens must be separated by a single space, i.e.:\n");
        builder.append("N G1 - F3\n\n");

        builder.append("In case, your move captures opposite piece, everything is the same, except the third step:\n");
        builder.append("3. Symbol 'x'\n");
        builder.append("So, for example you can obtain:\n");
        builder.append("N G1 x F3\n\n");

        builder.append("If your move is pawn move and pawn reaches final line of the board, the promotion must be made.\n");
        builder.append("In this case, you just have to enter symbol of desired piece (according to your color) as fifth option:\n");
        builder.append("5. Symbol 'B'\n");
        builder.append("So, for example you can obtain:\n");
        builder.append("P G7 x H8 B\n\n");

        builder.append("If your move is a castling, the command contais 2 tokens:\n");
        builder.append("1. King symbol ('K' or 'k'):\n");
        builder.append("2. Castling string ('0-0' - for short side castling and '0-0-0' - for long side castling)\n");
        builder.append("So, for example you can obtain:\n");
        builder.append("k 0-0-0\n\n");

        builder.append("After your move is parsed successfully, game will check if such move is allowed at the moment.\n");
        builder.append("If it is OK, this move will performed.\n");
        builder.append("Good Luck!!!\n\n");
        System.out.println(builder.toString());
    }
}
