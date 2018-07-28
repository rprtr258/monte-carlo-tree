package com.rprtr258.mctree;

import com.rprtr258.game.TicTacToe;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MCTreeTest {
    @Test
    public void ticTacToeTest() {
        MCTree tree1 = new MCTree();
        MCTree tree2 = new MCTree();
        trainTicTacToeTrees(tree1, tree2);
        GameResult firstPlayerGameResult = playTicTacToeGame(tree1, tree2);
        GameResult secondPlayerGameResult = invertGameResult(firstPlayerGameResult);
        assertTrue(firstPlayerGameResult == GameResult.DRAW);
        assertTrue(secondPlayerGameResult == GameResult.DRAW);
    }

    private void trainTicTacToeTrees(MCTree tree1, MCTree tree2) {
        for (int i = 0; i < 100; i++) {
            playTicTacToeGame(tree1, tree2);
        }
    }

    private GameResult playTicTacToeGame(MCTree tree1, MCTree tree2) {
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
        GameResult secondPlayerGameResult = invertGameResult(firstPlayerGameResult);
        tree2.endGame(secondPlayerGameResult);
        return firstPlayerGameResult;
    }

    private GameResult invertGameResult(GameResult gameResult) {
        switch (gameResult) {
            case WIN:
                return GameResult.LOSE;
            case LOSE:
                return GameResult.WIN;
            case DRAW:
                return GameResult.DRAW;
        }
        throw new IllegalArgumentException("Incorrect game result: " + gameResult);
    }

    public class MCTicTacToe extends TicTacToe implements Game {
        @Override
        public List<String> getAvailableTurns() {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (canMakeTurn(getCurrentPlayer(), i, j))
                        result.add(String.format("%d %d", i, j));
            return result;
        }

        @Override
        public GameResult getResult() {
            com.rprtr258.game.GameState gameState = getCurrentState();
            switch (gameState) {
                case CROSS_WIN:
                    return GameResult.WIN;
                case ZERO_WIN:
                    return GameResult.LOSE;
                case DRAW:
                    return GameResult.DRAW;
                case CROSS_TURN:
                    return GameResult.FIRST_PLAYER_TURN;
                case ZERO_TURN:
                    return GameResult.SECOND_PLAYER_TURN;
                default:
                    throw new IllegalStateException("Incorrect game state: " + gameState);
            }
        }

        @Override
        public void makeTurn(String turn) {
            int row = Integer.parseInt(turn.substring(0, turn.indexOf(' ')));
            int column = Integer.parseInt(turn.substring(turn.indexOf(' ') + 1));
            makeTurn(row, column);
        }

        @Override
        public String getState() {
            return toString();
        }
    }
}