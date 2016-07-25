package ru.rsdev.gamecr.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.ui.fragments.SingleGameFragment;

public class MainActivity extends SingleFragmentActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private ImageView mImageView;

    private FragmentTransaction fTrans;
    private SingleGameFragment previuFragment;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout)findViewById(R.id.navigation_drawer);


        setupToolbar();
        setupDrawer();


        //frag
        previuFragment = new SingleGameFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fragment_container, previuFragment);
        fTrans.commit();


    }


    private void setupToolbar(){

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_list_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setCheckable(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        //Avatar
        //mImageView = (ImageView)findViewById(R.id.drawer_avatar);
        mImageView = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.drawer_avatar);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_0);
        RoundedBitmapDrawable rBD = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        rBD.setCornerRadius(Math.max(bitmap.getHeight(),bitmap.getWidth())/2.0f);
        mImageView.setImageDrawable(rBD);



    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer!=null && mNavigationDrawer.isDrawerVisible(GravityCompat.START)){
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);



        }
        return super.onOptionsItemSelected(item);
    }




    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

}
