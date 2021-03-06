package ru.rsdev.gamecr.utils;

public interface ConstantManager {

    //Варианты времени для одиночной игры
    int TIMER_SINGLE_GAME_LOW = 60 * 1000;
    int TIMER_SINGLE_GAME_MEDIUM = 300 * 1000;
    int TIMER_SINGLE_GAME_HIGH = 0;

    //Сложность одиночной игры
    int TYPE_SINGLE_GAME_LOW = 0;
    int TYPE_SINGLE_GAME_MEDIUM = 1;
    int TYPE_SINGLE_GAME_HIGH = 2;

    //Максимальное количество ответов для ПК в одиночной игре
    int MAX_COUNT_SINGLE_GAME_LOW = 15;
    int MAX_COUNT_SINGLE_GAME_MEDIUM = 35;
    int MAX_COUNT_SINGLE_GAME_HIGH = 100;


    //Результаты
    String NAME_WINNER_RESULT = "NAME_WINNER_RESULT";
    String POINT_COUNT_RESULT = "POINT_COUNT_RESULT";





}
