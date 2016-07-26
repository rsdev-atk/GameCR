package ru.rsdev.gamecr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.filippudak.ProgressPieView.ProgressPieView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.model.City;
import ru.rsdev.gamecr.data.storage.DBHelper;
import ru.rsdev.gamecr.ui.adapters.RVAdapter;
import ru.rsdev.gamecr.utils.ConstantManager;

public class SingleGameFragment extends Fragment {
    private List<City> cities;
    private RecyclerView recyclerView;
    private RelativeLayout containerFragment;
    private DBHelper dbHelper;
    private EditText answerEditText;
    private com.github.clans.fab.FloatingActionButton answerFAB, surrenderFAB, helpFAB;
    private ProgressPieView progressPieView;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.recyclerview_activity, null);
        dbHelper = new DBHelper(getActivity());
        initializationView(v);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        initializationData();
        initializationAdapter();
        return v;
    }

    private void initializationData(){
        cities = new ArrayList<>();
        String answer = dbHelper.getRandomCity();
        addAnswer(answer);
        startTimer(ConstantManager.TIMER_SINGLE_GAME_LOW);
    }

    private void initializationAdapter(){
        RVAdapter adapter = new RVAdapter(cities);
        recyclerView.setAdapter(adapter);
    }

    private void setEditText(boolean enable){
        answerEditText.setEnabled(enable);
        answerEditText.setFocusable(enable);
        answerEditText.setFocusableInTouchMode(enable);
    }

    private void initializationView(View v){
        recyclerView =(RecyclerView)v.findViewById(R.id.rv);
        answerFAB = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_answer);
        surrenderFAB = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_surrender);
        helpFAB = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_help);
        progressPieView = (ProgressPieView)v.findViewById(R.id.progressPieView);
        containerFragment = (RelativeLayout)v.findViewById(R.id.container_fragmetn);
        answerEditText = (EditText)v.findViewById(R.id.answer_edit_text);

        answerEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //Переопределение кнопки Enter
                if(KeyEvent.ACTION_DOWN == keyEvent.getAction()){
                    switch (i){
                        case KeyEvent.KEYCODE_ENTER:
                            answerFAB.callOnClick();
                            break;
                        default:
                    }
                    return true;
                }
                return false;
            }
        });

        answerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ответ пользователя
                String oldAnswer = cities.get(0).name;
                if(chekUserAnswer(oldAnswer)) {
                    initializationAdapter();
                    //Скрываем клавиатуру
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    //Оnвет ПК
                    String pcAnswer = getPCAnswer();
                    addAnswer(pcAnswer);
                    initializationAdapter();
                    //setEditText(true);

                    restartTimer();
                    startTimer(ConstantManager.TIMER_SINGLE_GAME_LOW);
                }
            }
        });

        helpFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar("Подсказка");
                final int timeTimerMax = ConstantManager.TIMER_SINGLE_GAME_LOW;
                new CountDownTimer(timeTimerMax, 1000) {

                    float timeKoef = (float)timeTimerMax/(100*1000);

                    public void onTick(long millisUntilFinished) {
                        progressPieView.setText(String.valueOf((millisUntilFinished-1) / 1000));
                        progressPieView.setProgress((int) (millisUntilFinished / (1000*timeKoef)));
                    }

                    public void onFinish() {
                        progressPieView.setText("0");
                        progressPieView.setProgress(0);
                        gameOver("Соперник выиграл");
                        //showSnackbar("GameOver");
                    }
                }.start();


            }
        });

        surrenderFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar("Сдаться");
                int pointGame = cities.size();
                gameOver("Победил соперник");
            }
        });


    }

    private boolean chekUserAnswer(String oldAnswer){
        String answer = null;
        //Проверка на валидность ответа
        if (answerEditText.getText().length() > 0) {
            answer = answerEditText.getText().toString();
            //Нормализация ответа
            answer = dbHelper.gameAlgorithm.getCorrectCityName(answer);
        }else {
            showSnackbar("Введите ответ");
            return false;
        }
        boolean correctFirstLetter = dbHelper.gameAlgorithm.checkFirstLatter(answer,oldAnswer);
        //Проверка на первую букву
        if (!correctFirstLetter){
            showSnackbar("Не верна первая буква");
            return false;
            //Проверка на существование города
        }else if(!dbHelper.findCityByUserAnswer(answer)){
            showSnackbar("Города не существует");
            return false;
        }
        //Проверка на повтор
        else if(!dbHelper.gameAlgorithm.getDoublingAnswer(cities, answer)){
            showSnackbar("Повтор города");
            return false;
        }
        else {
            addAnswer(answer);
            answerEditText.setText("");
            return true;
        }
    }

    private String getPCAnswer(){
        String oldAnswer = cities.get(0).name;
        ArrayList<String> answersVariant = dbHelper.getAnswerPC(oldAnswer);

        //Проверка на повторы
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i< cities.size(); i++){
            list.add(cities.get(i).name);
        }
        answersVariant.removeAll(list);

        //Выбор случайного города из всех вариантов
        final Random random = new Random();
        int numberOfAnswer = random.nextInt(answersVariant.size());
        return answersVariant.get(numberOfAnswer);
    }

    private void addAnswer(String answer){
        List<String> list = dbHelper.getAllDataAboutCity(answer);
        String stateName = list.get(0);
        String population = list.get(2);
        String dateStart = list.get(3);
        cities.add(0, new City(
                answer,
                stateName,
                population,
                dateStart,
                String.valueOf(cities.size() + 1)
        ));
    }


    private void gameOver(String winnerName){
        Integer pointsCount = cities.size();
        ResultSingleGameFragment resultSingleGameFragment = new ResultSingleGameFragment();
        Bundle arguments = new Bundle();

        arguments.putString(ConstantManager.NAME_WINNER_RESULT, winnerName);
        arguments.putInt(ConstantManager.POINT_COUNT_RESULT, pointsCount);

        resultSingleGameFragment.setArguments(arguments);

        FragmentTransaction fTrans;
        fTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fragment_container, resultSingleGameFragment);
        fTrans.commit();
    }

    private void showSnackbar(String message){
        Snackbar.make(containerFragment, message, Snackbar.LENGTH_SHORT).show();
    }

    private void startTimer(final int timeTimerMax){

        countDownTimer = new CountDownTimer(timeTimerMax, 1000) {

            float timeKoef = (float)timeTimerMax/(100*1000);

            public void onTick(long millisUntilFinished) {
                progressPieView.setText(String.valueOf((millisUntilFinished-1) / 1000));
                progressPieView.setProgress((int) (millisUntilFinished / (1000*timeKoef)));
            }

            public void onFinish() {
                progressPieView.setText("0");
                progressPieView.setProgress(0);
                gameOver("Соперник выиграл");
            }
        }.start();
    }

    private void restartTimer(){
        countDownTimer.cancel();
    }


}
