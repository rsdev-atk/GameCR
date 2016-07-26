package ru.rsdev.gamecr.data.managers;

public class SingleGameSetting {

    private static final SingleGameSetting instance = new SingleGameSetting();
    private int typeSingleGame;
    private int timeSingleGame;

    private SingleGameSetting(){
    }

    public static SingleGameSetting getInstance(){
        return instance;
    }

    public int getTypeSingleGame() {
        return typeSingleGame;
    }

    public void setTypeSingleGame(int typeSingleGame) {
        this.typeSingleGame = typeSingleGame;
    }

    public int getTimeSingleGame() {
        return timeSingleGame;
    }

    public void setTimeSingleGame(int timeSingleGame) {
        this.timeSingleGame = timeSingleGame;
    }


}
