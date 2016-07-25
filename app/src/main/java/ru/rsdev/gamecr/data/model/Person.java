package ru.rsdev.gamecr.data.model;

public class Person {
    public String name;
    public String age;

    public String population;
    public String dateStart;
    public String numberAnswer;


    public Person(String name, String age, String population, String dateStart, String numberAnswer) {
        this.name = name;
        this.age = age;
        this.population = population;
        this.dateStart = dateStart;

        this.numberAnswer = numberAnswer;

    }
}