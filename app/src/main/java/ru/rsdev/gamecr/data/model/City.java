package ru.rsdev.gamecr.data.model;

public class City {
    public String name;
    public String region;
    public String population;
    public String dateStart;
    public String numberAnswer;


    public City(String name, String region, String population, String dateStart, String numberAnswer) {
        this.name = name;
        this.region = region;
        this.population = population;
        this.dateStart = dateStart;
        this.numberAnswer = numberAnswer;
    }
}