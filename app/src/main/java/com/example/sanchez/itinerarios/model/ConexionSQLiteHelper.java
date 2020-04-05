package com.example.sanchez.itinerarios.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    //
    final String CREAR_TABLA_ITINERARIO = "CREATE TABLE ITINERARIO(id INTEGER, area TEXT, fecha TEXT, horaInicio TEXT, horaFin TEXT, solicitud TEXT, actividad TEXT)";

    //Creamos la base da datos
    public ConexionSQLiteHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }//ConexionSQLiteHelper

    //Creamos las tablas correspondientes
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_ITINERARIO);
    }//onCreate

    //Verifica si existe una version antigua asi mismo refresca a una version nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS itinerario");
        onCreate(db);
    }//onUpgrade
}
