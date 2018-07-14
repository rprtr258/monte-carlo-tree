package com.rprtr258.mctree;

import java.util.List;

public interface Game {
    List<String> getAvailableTurns();
    GameResult getResult();
    void makeTurn(String turn);

    String getState();
}
