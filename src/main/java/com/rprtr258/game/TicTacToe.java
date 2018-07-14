package com.rprtr258.game;

/**
 * Class for making tic-tac-toe game through it's interface.
 */
public class TicTacToe {
    private TicTacToeField field = null;
    private final int CROSS_PLAYER = 1;
    private final int ZERO_PLAYER = -1;
    private int currentPlayer = CROSS_PLAYER;

    /**
     * Constructor for empty game.
     */
    public TicTacToe() {
        field = new TicTacToeField();
    }

    /**
     * Restarts game.
     */
    public void restart() {
        field.clear();
        currentPlayer = CROSS_PLAYER;
    }

    /**
     * Makes turn in given cell.
     * @param row row of cell.
     * @param column column of cell.
     * @return <b>true</b> if turn was made.
     */
    public boolean makeTurn(int row, int column) {
        if (!canMakeTurn(currentPlayer == CROSS_PLAYER ? "X" : "O", row, column))
            return false;
        field.setCellState(row, column, currentPlayer == CROSS_PLAYER ? CellState.CROSS_CELL : CellState.ZERO_CELL);
        changePlayer();
        return true;
    }

    /**
     * @return state of the game.
     */
    public GameState getCurrentState() {
        int winState = field.getMaxLineSum() / field.getSize();
        if (winState != 0)
            return (winState == 1 ? GameState.CROSS_WIN : GameState.ZERO_WIN);
        if (field.countEmptyCells() == 0)
            return GameState.DRAW;
        return (currentPlayer == ZERO_PLAYER ? GameState.ZERO_TURN : GameState.CROSS_TURN);
    }

    /**
     * Checks if given player can make turn to given position.
     *
     * @param playerName player mark.
     * @param row row coordinate.
     * @param column column coordinate.
     * @return true if player can perform that turn.
     */
    public boolean canMakeTurn(String playerName, int row, int column) {
        if (ended())
            return false;
        GameState gameState = getCurrentState();
        if (gameState == GameState.ZERO_TURN && "O".equals(playerName) ||
            gameState == GameState.CROSS_TURN && "X".equals(playerName))
            return (field.getCellState(row, column) == CellState.EMPTY_CELL);
        return false;
    }

    public String getCurrentPlayer() {
        return currentPlayer == CROSS_PLAYER ? "X" : "O";
    }

    /**
     * Changes one player to another when turn ends.
     */
    private void changePlayer() {
        currentPlayer = (currentPlayer == CROSS_PLAYER ? ZERO_PLAYER : CROSS_PLAYER);
    }

    private boolean ended() {
        GameState gameState = getCurrentState();
        return gameState == GameState.CROSS_WIN || gameState == GameState.ZERO_WIN || gameState == GameState.DRAW;
    }

    /**
     * Represents game as string.
     *
     * @return game string representation.
     */
    @Override
    public String toString() {
        return String.valueOf(field) + getCurrentState();
    }
}
