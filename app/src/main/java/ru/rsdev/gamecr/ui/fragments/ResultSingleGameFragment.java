package ru.rsdev.gamecr.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.utils.ConstantManager;

public class ResultSingleGameFragment extends Fragment {

    private TextView winnerName, pointsGame;
    private Button newGameButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result_single_game, null);

        winnerName = (TextView)v.findViewById(R.id.winner_name_result);
        pointsGame = (TextView)v.findViewById(R.id.points_count_result);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String winner = bundle.getString(ConstantManager.NAME_WINNER_RESULT);
            Integer progressGame = bundle.getInt(ConstantManager.POINT_COUNT_RESULT);
            winnerName.setText(winner);
            pointsGame.setText("Количество ходов: " + progressGame.toString());
        }

        newGameButton = (Button) v.findViewById(R.id.new_single_game_button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SingleGameFragment singleGameFragment = new SingleGameFragment();
                FragmentTransaction fTrans;
                fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fragment_container, singleGameFragment);
                fTrans.commit();
            }
        });
        return v;
    }


}
