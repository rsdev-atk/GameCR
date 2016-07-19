package ru.rsdev.gamecr.data.managers;

import ru.rsdev.gamecr.data.storage.DBHelper;

public class SingleGame {
    private GameAlgorithm gameAlgorithm;
    private DBHelper DBHelper;

    public void SingleGame(){
        gameAlgorithm = new GameAlgorithm();
    }

    public void startNewGame(){

    }

    public void gameOver(){

    }
}
