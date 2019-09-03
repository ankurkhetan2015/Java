package game.impl;

import game.*;
import players.IPlayer;

public class Game implements IGame {
    private IGameState gameState;

    private IPlayer whitePlayer;
    private IPlayer blackPlayer;

    private int turnNumber;
    private StringBuilder notationBuilder;

    public Game(IPlayer whitePlayer, IPlayer blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    @Override
    public void init() {
        gameState = new GameState();
        gameState.init();
        turnNumber = 0;
        notationBuilder = new StringBuilder();
    }

    @Override
    public void makeTurn() {
        if (!gameState.getGameStatus().isOver()) {
            IMove move;
            if (gameState.getGameStatus() == GameStatus.WHITE_TURN) {
                turnNumber++;
                System.out.println(Color.WHITE + " to move:");
                move = whitePlayer.chooseTurn(gameState);
                System.out.println("WHITE: " + move);
            }
            else {
                System.out.println(Color.BLACK + " to move:");
                move = blackPlayer.chooseTurn(gameState);
                System.out.println("BLACK: " + move);
            }
            gameState = gameState.makeTurn(move);

            notationBuilder.append(String.format("%4d.  %s\n", turnNumber, move.toString()));
        }
    }

    @Override
    public boolean isOver() {
        return gameState.getGameStatus().isOver();
    }

    @Override
    public GameStatus getGameState() {
        return gameState.getGameStatus();
    }

    @Override
    public void showPosition() {
        System.out.println(gameState.toString());
    }

    @Override
    public String getNotation() {
        return notationBuilder.toString();
    }
}
