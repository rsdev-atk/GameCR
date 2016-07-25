package ru.rsdev.gamecr.data.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

import ru.rsdev.gamecr.data.managers.GameAlgorithm;

public class DBHelper {
    private SQLiteDatabase database;
    int countTown = 1126;
    String firstTown;
    private static final String TABLE_NAME = "city";
    private static final String ID = "_id";
    private static final String CITY = "CityName";

    public GameAlgorithm gameAlgorithm = new GameAlgorithm();


    public DBHelper(Context context){
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(context);
        database = dbOpenHelper.openDataBase();
    }

    //Получение случайного города для первого хода
    public String getRandomCity()
    {
        Random rand = new Random();
        int rndInDB = rand.nextInt(countTown);
        String sqlQuery = "SELECT "+ CITY + " FROM " + TABLE_NAME + " WHERE " + ID  + "=" + String.valueOf(rndInDB).toString();
        Cursor firstTownCursor = database.rawQuery(sqlQuery, new  String[] {});

        if (firstTownCursor.moveToFirst()) {
            int nameColIndex = firstTownCursor.getColumnIndex(CITY);
            do {
                firstTown = firstTownCursor.getString(nameColIndex);
            } while (firstTownCursor.moveToNext());
        } else{}
        firstTownCursor.close();
        return firstTown;
    }

    //Получение нового города (ответа ПК) по ответу пользователя
    public ArrayList getAnswerPC(String userCity)
    {
        ArrayList<String> answersVariant = new ArrayList<String>();
        char firstLatter = gameAlgorithm.getLastLetter(userCity);
        String sqlQueryAnswer = "SELECT "+ CITY + " FROM " + TABLE_NAME + " WHERE " + CITY + " LIKE '" + String.valueOf(firstLatter) + "%'";
        Cursor cursor = database.rawQuery(sqlQueryAnswer, new  String[] {});

        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex(CITY);
            do {
                String cursorData = cursor.getString(nameColIndex);
                answersVariant.add(cursorData);
            } while (cursor.moveToNext());
        } else
        {
            //result = "No";
        }
        cursor.close();

        return answersVariant;





    }

    //Поиск города в БД
    public boolean findCityByUserAnswer(String userCity)
    {
        boolean result=false;
        String sqlQuery2 = "SELECT "+ CITY + " FROM " + TABLE_NAME + " WHERE " + CITY + " = '" + userCity + "'";
        Cursor cursorAnswer = database.rawQuery(sqlQuery2, new  String[] {});

        if (cursorAnswer.moveToFirst()) {
            int nameColIndex = cursorAnswer.getColumnIndex(CITY);
            do {
                String cursorData = cursorAnswer.getString(nameColIndex);
                if(cursorData.equalsIgnoreCase(userCity))
                {
                    result = true;
                }
            } while (cursorAnswer.moveToNext());
        } else
        {
            //result = "No";
        }
        cursorAnswer.close();
        return result;
    }

    //Получение дополнительных данных о городе
    public ArrayList<String> getAllDataAboutCity(String userAnswer){
        ArrayList<String> listExtra = new ArrayList<>();
        String sqlQueryAnswer = "SELECT * FROM " + TABLE_NAME + " WHERE " + CITY + " = '" + userAnswer +"'";
        Cursor cursor = database.rawQuery(sqlQueryAnswer, new  String[] {});
        if (cursor.moveToFirst()) {
            int nameColStateName = cursor.getColumnIndex("StateName");
            int nameColFOName = cursor.getColumnIndex("FOName");
            int nameColPopulation = cursor.getColumnIndex("Population");
            int nameColDataStart = cursor.getColumnIndex("DataStart");
            int nameColCapital = cursor.getColumnIndex("Capital");

            do {
                listExtra.add(cursor.getString(nameColStateName));
                listExtra.add(cursor.getString(nameColFOName));
                listExtra.add(cursor.getString(nameColPopulation));
                listExtra.add(cursor.getString(nameColDataStart));
                listExtra.add(cursor.getString(nameColCapital));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listExtra;
    }

}
