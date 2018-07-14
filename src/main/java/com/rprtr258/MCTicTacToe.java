package com.rprtr258;

import com.rprtr258.game.TicTacToe;
import com.rprtr258.mctree.*;

import java.util.ArrayList;
import java.util.List;

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
