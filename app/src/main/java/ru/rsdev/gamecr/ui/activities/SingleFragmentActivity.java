package ru.rsdev.gamecr.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}