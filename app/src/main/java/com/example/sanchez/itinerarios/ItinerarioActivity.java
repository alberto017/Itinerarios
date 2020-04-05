package com.example.sanchez.itinerarios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;

public class ItinerarioActivity extends AppCompatActivity {

    private FrameLayout flItinerarioActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);
        flItinerarioActivity = findViewById(R.id.flItinerarioActivity);

        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this,"bd_itinerario",null,1);

        setFragment(new ListItinerarioFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flItinerarioActivity.getId(),fragment);
        fragmentTransaction.commit();
    }
}
