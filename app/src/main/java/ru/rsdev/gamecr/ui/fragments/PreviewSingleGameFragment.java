package ru.rsdev.gamecr.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.managers.SingleGameSetting;
import ru.rsdev.gamecr.utils.ConstantManager;

public class PreviewSingleGameFragment extends Fragment{

    private Button startNewSingleGame;
    private RadioGroup timeRadioGroup, typeGameRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preview_single_game, null);

        typeGameRadioGroup = (RadioGroup)v.findViewById(R.id.rb_type_game_group);
        timeRadioGroup = (RadioGroup)v.findViewById(R.id.rb_time_group);

        startNewSingleGame = (Button)v.findViewById(R.id.txt_start_new_single_game);
        startNewSingleGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer timeSetting = 0;
                Integer typeSetting = 0;

                int time = timeRadioGroup.getCheckedRadioButtonId();
                switch (time) {
                    case R.id.rb_time_light:
                        timeSetting = ConstantManager.TIMER_SINGLE_GAME_LOW;
                        break;
                    case R.id.rb_time_medium:
                        timeSetting = ConstantManager.TIMER_SINGLE_GAME_MEDIUM;
                        break;
                    case R.id.rb_time_high:
                        timeSetting = ConstantManager.TIMER_SINGLE_GAME_HIGH;
                        break;
                }

                int typeGame = typeGameRadioGroup.getCheckedRadioButtonId();
                switch (typeGame) {
                    case R.id.rb_type_game_light:
                        typeSetting = ConstantManager.TYPE_SINGLE_GAME_LOW;
                        break;
                    case R.id.rb_type_game_medium:
                        typeSetting = ConstantManager.TYPE_SINGLE_GAME_MEDIUM;
                        break;
                    case R.id.rb_type_game_high:
                        typeSetting = ConstantManager.TYPE_SINGLE_GAME_HIGH;
                        break;
                }

                SingleGameSetting.getInstance().setTypeSingleGame(typeSetting);
                SingleGameSetting.getInstance().setTimeSingleGame(timeSetting);

                SingleGameFragment singleGameFragment = new SingleGameFragment();
                FragmentTransaction fTrans;
                fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fragment_container, singleGameFragment);
 //               fTrans.commit();





            }
        });

        return v;
    }
}
