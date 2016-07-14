package ru.rsdev.gamecr.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.managers.GameAlgorithm;
import ru.rsdev.gamecr.data.model.Person;
import ru.rsdev.gamecr.data.storage.DBHelper;
import ru.rsdev.gamecr.ui.adapters.RVAdapter;

public class PreviewFragment extends Fragment {
    private List<Person> persons;
    private RecyclerView rv;

    GameAlgorithm mGameAlgorithm;
    DBHelper mDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recyclerview_activity, null);
        rv=(RecyclerView)v.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        initializeData();
        initializeAdapter();

        mDBHelper = new DBHelper(getActivity());
        Button buttonTest = (Button)v.findViewById(R.id.button_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = mDBHelper.getRandomCity();
                Toast.makeText(getActivity(), answer, Toast.LENGTH_SHORT).show();
            }
        });





        return v;
    }

    private void initializeData(){
        persons = new ArrayList<>();
        for(int i=0; i<50; i++){
            persons.add(new Person("Name_" + i, "years old " + i*i, android.R.drawable.arrow_up_float));

        }
        }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
