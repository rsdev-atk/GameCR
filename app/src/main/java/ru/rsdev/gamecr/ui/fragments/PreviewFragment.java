package ru.rsdev.gamecr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.model.Person;
import ru.rsdev.gamecr.data.storage.DBHelper;
import ru.rsdev.gamecr.ui.adapters.RVAdapter;

public class PreviewFragment extends Fragment {
    private List<Person> persons;
    private RecyclerView rv;
    private RelativeLayout containerFragmetn;
    DBHelper mDBHelper;
    EditText answerEditText;
    com.github.clans.fab.FloatingActionButton fabAnswer, fabSurrender, fabHelp;
    ProgressPieView mProgressPieView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.recyclerview_activity, null);
        mDBHelper = new DBHelper(getActivity());
        initializeView(v);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        initializeData();
        initializeAdapter();





        return v;
    }

    private void initializeData(){
        persons = new ArrayList<>();
        String answer = mDBHelper.getRandomCity();
        persons.add(0, new Person(answer, "years old ", android.R.drawable.arrow_up_float));
        }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }

    private void setEditText(boolean enable){
        answerEditText.setEnabled(enable);
        answerEditText.setFocusable(enable);
        answerEditText.setFocusableInTouchMode(enable);
    }

    private void initializeView(View v){
        rv=(RecyclerView)v.findViewById(R.id.rv);
        fabAnswer = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_answer);
        fabSurrender = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_surrender);
        fabHelp = (com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.fab_help);
        mProgressPieView = (ProgressPieView)v.findViewById(R.id.progressPieView);


        answerEditText = (EditText)v.findViewById(R.id.answer_edit_text);
        answerEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //Переопределение кнопки Enter
                if(KeyEvent.ACTION_DOWN == keyEvent.getAction()){
                    switch (i){
                        case KeyEvent.KEYCODE_ENTER:
                            fabAnswer.callOnClick();
                            break;
                        default:
                    }
                    return true;
                }
                return false;
            }
        });

        fabAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ответ пользователя
                String oldAnswer = persons.get(0).name;
                if(chekUserAnswer(oldAnswer)) {
                    initializeAdapter();
                    //Скрываем клавиатуру
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    //Оnвет ПК
                    String pcAnswer = getPCAnswer();
                    persons.add(0, new Person(pcAnswer, "years old ", android.R.drawable.arrow_up_float));
                    initializeAdapter();
                    //setEditText(true);
                }
            }
        });

        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar("Подсказка");


                //final int timeTimerMax = ConstantManager.TIMER_SIMGLE_GAME_LOW;
                final int timeTimerMax = 40000;

                new CountDownTimer(timeTimerMax, 1000) {

                    float timeKoef = (float)timeTimerMax/(100*1000);

                    public void onTick(long millisUntilFinished) {
                        mProgressPieView.setText(String.valueOf((millisUntilFinished-1) / 1000));

                        mProgressPieView.setProgress((int) (millisUntilFinished / (1000*timeKoef)));
                    }

                    public void onFinish() {
                        mProgressPieView.setText("done!");
                        mProgressPieView.setProgress(0);

                        //showSnackbar("GameOver");
                    }
                }.start();


            }
        });

        fabSurrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar("Сдаться");
            }
        });

        containerFragmetn = (RelativeLayout)v.findViewById(R.id.container_fragmetn);

    }

    private boolean chekUserAnswer(String oldAnswer){
        String answer = null;
        //Проверка на валидность ответа
        if (answerEditText.getText().length() > 0) {
            answer = answerEditText.getText().toString();
            //Нормализация ответа
            answer = mDBHelper.gameAlgorithm.getCorrectCityName(answer);
        }else {
            showSnackbar("Введите ответ");
            return false;
        }
        boolean correctFirstLetter = mDBHelper.gameAlgorithm.checkFirstLatter(answer,oldAnswer);
        //Проверка на первую букву
        if (!correctFirstLetter){
            showSnackbar("Не верна первая буква");
            return false;
            //Проверка на существование города
        }else if(!mDBHelper.findCityByUserAnswer(answer)){
            showSnackbar("Города не существует");
            return false;
        }
        //Проверка на повтор
        else if(!mDBHelper.gameAlgorithm.getDoublingAnswer(persons, answer)){
            showSnackbar("Повтор города");
            return false;
        }
        else {
            persons.add(0, new Person(answer, "years old ", android.R.drawable.arrow_up_float));
            answerEditText.setText("");
            return true;
        }
    }

    private String getPCAnswer(){
        String oldAnswer = persons.get(0).name;
        return mDBHelper.getAnswerPC(oldAnswer);
    }



    private void showSnackbar(String message){
        Snackbar.make(containerFragmetn, message, Snackbar.LENGTH_SHORT).show();
    }




}
