package com.rprtr258;

import com.rprtr258.mctree.*;

public class Main {
    public static void main(String[] args) {
        MCTree tree1 = new MCTree();
        MCTree tree2 = new MCTree();
        for (int i = 0; i < 100; i++) {
            Game currentGame = new MCTicTacToe();
            while (currentGame.getResult() == GameResult.FIRST_PLAYER_TURN ||
                   currentGame.getResult() == GameResult.SECOND_PLAYER_TURN) {
                if (currentGame.getResult() == GameResult.FIRST_PLAYER_TURN) {
                    String turn = tree1.askTurn(currentGame.getState(), currentGame.getAvailableTurns());
                    currentGame.makeTurn(turn);
                } else {
                    String turn = tree2.askTurn(currentGame.getState(), currentGame.getAvailableTurns());
                    currentGame.makeTurn(turn);
                }
            }
            GameResult firstPlayerGameResult = currentGame.getResult();
            tree1.endGame(firstPlayerGameResult);
            GameResult secondPlayerGameResult = null;
            switch (firstPlayerGameResult) {
                case WIN:
                    secondPlayerGameResult = GameResult.LOSE;
                    break;
                case LOSE:
                    secondPlayerGameResult = GameResult.WIN;
                    break;
                case DRAW:
                    secondPlayerGameResult = GameResult.DRAW;
                    break;
            }
            tree2.endGame(secondPlayerGameResult);
            System.out.println(firstPlayerGameResult);
        }
    }
}
