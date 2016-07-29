package ru.rsdev.gamecr.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.ui.fragments.PreviewSingleGameFragment;

public class MainActivity extends SingleFragmentActivity
implements GoogleApiClient.OnConnectionFailedListener{

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private ImageView mImageView;
    private FragmentTransaction fTrans;
    private PreviewSingleGameFragment previewSingleGameFragment;

    //Сущности для работы с авторизацией
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirechatUser;


    //Временные сущности для тестирования авторизации
    private String mUsername;
    private String mPhotoUrl;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout)findViewById(R.id.navigation_drawer);

        //Авторизация
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirechatUser = mFirebaseAuth.getCurrentUser();
        if (mFirechatUser == null) {
            startActivity(new Intent(this, AuthorizationActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirechatUser.getDisplayName();
            if (mFirechatUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirechatUser.getPhotoUrl().toString();
            }
        }

        setupToolbar();
        setupDrawer();
        showPreviewSingleGameFragment();

    }

    private void showPreviewSingleGameFragment(){
        previewSingleGameFragment = new PreviewSingleGameFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fragment_container, previewSingleGameFragment);
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

                switch (item.getItemId()){
                    case R.id.single_game_menu_item:
                        showPreviewSingleGameFragment();
                        break;

                    case R.id.online_game__menu_item:
                        showSnackbar("Сетевая игра в разработке");
                        break;

                    case R.id.rating_menu_item:
                        break;

                    case R.id.rules_menu_item:
                        break;
                }


                item.setCheckable(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
