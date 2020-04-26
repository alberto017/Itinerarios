package com.example.sanchez.itinerarios;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;

public class ItinerarioActivity extends AppCompatActivity {

    private FrameLayout flItinerarioActivity;

    private static final int LIST_FRAGMENT = 0;
    private static final int RESERVATION_FRAGMENT = 1;
    private static int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);

        //Enlazamos los componentes
        flItinerarioActivity = findViewById(R.id.flItinerarioActivity);

        //Inicializamos la conexion SQLite
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this,"bd_itinerario",null,1);

        //Llamamos a ListItinerarioFragment
        setFragment(new ListItinerarioFragment());
    }//onCreate

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setFragment(new ListItinerarioFragment());
            return false;
        }//if
        return super.onKeyDown(keyCode, event);
    }

    /*
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment == RESERVATION_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        }
        return super.onCreateOptionsMenu(menu);
    }
    */

    private void setFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(flItinerarioActivity.getId(),fragment);
            fragmentTransaction.commit();
    }//setFragment
}
