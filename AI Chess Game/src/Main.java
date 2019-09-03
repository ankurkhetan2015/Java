import game.Color;
import game.impl.Game;
import game.IGame;
import players.IPlayer;
import players.impl.ConsolePlayer;
import players.impl.MiniMaxAIPlayer;
import players.impl.RandomAIPlayer;

import java.util.Scanner;

public class Main {
	private static IPlayer whitePlayer;
	private static IPlayer blackPlayer;
	private static Scanner scanner;


    public static void main(String[] args) {
    	scanner = new Scanner(System.in);

		getGameParameters();
	    IGame game = new Game(whitePlayer, blackPlayer);
	    game.init();

	    System.out.println();
	    System.out.println(Color.WHITE + " ----- " + whitePlayer.toString());
		System.out.println(Color.BLACK + " ----- " + blackPlayer.toString());
		System.out.println("The game is ready to start");
		System.out.println();

	    while (!game.isOver()) {
			game.showPosition();

			game.makeTurn();
		}
		game.showPosition();
		System.out.println("The game is over.");
	    System.out.println(game.getGameState());
	    System.out.println(game.getNotation());

	    scanner.close();
    }


    private static void getGameParameters() {
    	System.out.println("Welcome to CHESS!");
		whitePlayer = choosePlayer(Color.WHITE);
		blackPlayer = choosePlayer(Color.BLACK);
	}

	private static IPlayer choosePlayer(Color color) {
    	while (true) {
			System.out.println();
    		System.out.println("Please, choose player for " + color + " side");
			System.out.println("1. Human Console Player");
			System.out.println("2. AI Random Player");
			System.out.println("3. AI MiniMax Player");
			System.out.println("Enter 1,2 or 3");

			try {
				String line = scanner.nextLine().trim();
				int choice = Integer.parseInt(line);
				switch (choice) {
					case 1:
						return new ConsolePlayer(scanner);
					case 2:
						return new RandomAIPlayer();
					case 3:
						return new MiniMaxAIPlayer(color);
					default:
						throw new NumberFormatException();
				}
			}
			catch (NumberFormatException nfe) {
				System.err.println("Invalid input");
			}

		}

	}
}
