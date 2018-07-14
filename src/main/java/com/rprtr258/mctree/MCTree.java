package com.rprtr258.mctree;

import java.util.*;

public class MCTree {
    private Map<GameStateAndTurn, TurnData> data;
    private List<GameStateAndTurn> gameHistory;

    public MCTree() {
        data = new TreeMap<>();
        gameHistory = new ArrayList<>();
    }

    public String askTurn(String gameState, List<String> availableTurns) {
        if (availableTurns.isEmpty())
            throw new IllegalArgumentException("Provided empty list of available turns.");

        double maxScorePerTotal = -1;
        String resultTurn = "";
        for (String turn : availableTurns) {
            GameStateAndTurn gameStateAndTurn = new GameStateAndTurn(gameState, turn);
            if (!data.containsKey(gameStateAndTurn))
                continue;
            TurnData turnData = data.get(gameStateAndTurn);
            if (turnData.won / turnData.total > maxScorePerTotal) {
                maxScorePerTotal = turnData.won / turnData.total;
                resultTurn = turn;
            }
        }
        if (maxScorePerTotal > 0) {
            gameHistory.add(new GameStateAndTurn(gameState, resultTurn));
            return resultTurn;
        }
        for (String turn : availableTurns) {
            GameStateAndTurn gameStateAndTurn = new GameStateAndTurn(gameState, turn);
            if (!data.containsKey(gameStateAndTurn)) {
                gameHistory.add(gameStateAndTurn);
                data.put(gameStateAndTurn, new TurnData());
                return turn;
            }
        }
        gameHistory.add(new GameStateAndTurn(gameState, availableTurns.get(0)));
        return availableTurns.get(0);
    }

    public void endGame(GameResult gameResult) {
        switch (gameResult) {
            case WIN:
                backPropagate(1);
            case LOSE:
                backPropagate(0);
                break;
            case DRAW:
                backPropagate(0.1);
                break;
        }
    }

    private void backPropagate(double winning) {
        for (GameStateAndTurn gameStateAndTurn : gameHistory) {
            TurnData turnData = data.get(gameStateAndTurn);
            turnData.total += 1;
            turnData.won += winning;
            data.put(gameStateAndTurn, turnData);
        }
        gameHistory.clear();
    }

    private class GameStateAndTurn implements Comparable<GameStateAndTurn> {
        private String gameState;
        private String turn;

        private GameStateAndTurn(String gameState, String turn) {
            this.gameState = gameState;
            this.turn = turn;
        }

        @Override
        public int compareTo(GameStateAndTurn other) {
            return toString().compareTo(other.toString());
        }

        @Override
        public String toString() {
            return gameState + turn;
        }
    }

    private class TurnData {
        private double won;
        private double total;

        private TurnData() {
            won = 0;
            total = 0;
        }

        @Override
        public String toString() {
            return String.format("%f/%f", won, total);
        }
    }
}
