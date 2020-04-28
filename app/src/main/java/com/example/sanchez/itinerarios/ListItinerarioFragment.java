package com.example.sanchez.itinerarios;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;
import com.example.sanchez.itinerarios.model.Itinerario;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItinerarioFragment extends Fragment {

    //Declaracion de variables
    private RecyclerView rvItinerario;
    private FloatingActionButton fbAddReservation;
    private FrameLayout flListItinerario;
    private ArrayList<Itinerario> itinerarioArrayList;
    private ConexionSQLiteHelper conexionSQLiteHelper;
    private ItinerarioAdapter itinerarioAdapter = null;
    String valueDate = "";


    public ListItinerarioFragment() {
        // Required empty public constructor
    }//ListItinerarioFragment

    //Este metodo se ejecutara primero dentro del fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Pendiente
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }//onCreate

    //Inflamos nuestro menu dentro del activity
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.about_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }//onCreateOptionsMenu

    //Seleccion del item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSearch:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Seleccione la fecha del itinerario")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Calendar calendar = Calendar.getInstance();
                                final int year = calendar.get(Calendar.YEAR);
                                final int month = calendar.get(Calendar.MONTH);
                                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                        //month = month + 1;
                                        Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                                        String date = day + "/" + (month + 1) + "/" + year;
                                        buscarItinerario(new Itinerario(),date);
                                        //Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
                                    }//onDateSet
                                },year, month, day);
                                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                datePickerDialog.show();
                            }//onClick
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }//onClick
                        });
                AlertDialog title = alertDialog.create();
                title.setTitle("Search Itinerario");
                title.show();

                break;
            case R.id.itemExit:
                Toast.makeText(getContext(), "¡Ha salido de la aplicacion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemAbout:
                Toast.makeText(getContext(), "¡Ventana desarrolladores no disponible", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemOtros:
                Toast.makeText(getContext(), "¡Ventana otros no disponible", Toast.LENGTH_SHORT).show();
                break;
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    //Referenciamos todas nuestras variables
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_itinerario, container, false);

        rvItinerario = view.findViewById(R.id.rvItinerario);
        fbAddReservation = view.findViewById(R.id.fbAddReservation);
        flListItinerario = getActivity().findViewById(R.id.flItinerarioActivity);

        //Instanciamos nuestra conexion
        conexionSQLiteHelper = new ConexionSQLiteHelper(getContext(), "db_itinerario", null, 1);
        //Inicializamos nuestro arreglo
        itinerarioArrayList = new ArrayList<>();
        //Asignamos nuestro LayoutManager
        rvItinerario.setLayoutManager(new LinearLayoutManager(getContext()));

        //Metodo de consulta a la BD
        consultaItinerarioSQL();

        //Creamos el objeto Adapter y le mandamos el parametro del arreglo obtenid en la consulta
        itinerarioAdapter = new ItinerarioAdapter(itinerarioArrayList);
        rvItinerario.setAdapter(itinerarioAdapter);

        return view;
    }//onCreateView

    //Metodo consultaItinerarioSQL
    private void consultaItinerarioSQL() {
        SQLiteDatabase sqLiteDatabase = conexionSQLiteHelper.getReadableDatabase();
        Itinerario itinerario = null;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from itinerario", null);
        while (cursor.moveToNext()) {
            itinerario = new Itinerario();
            itinerario.setId(cursor.getInt(0));
            itinerario.setArea(cursor.getString(1));
            itinerario.setFecha(cursor.getString(2));
            itinerario.setHoraInicio(cursor.getString(3));
            itinerario.setHoraFin(cursor.getString(4));
            itinerario.setSolicitud(cursor.getString(5));
            itinerario.setActividad(cursor.getString(6));

            itinerarioArrayList.add(itinerario);
        }//while
    }//consultaPersonasSQL

    //Declaramos nuestros metodos
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Implementamos evento que estaba en el adapter por la 2da forma
        itinerarioAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itinerarioDetail = new Intent(getContext(), ItinerarioDetail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("noSolicitudDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getId());
                bundle.putString("areaDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getArea());
                bundle.putString("fechaDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getFecha());
                bundle.putString("horaInicioDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getHoraInicio());
                bundle.putString("horaFinDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getHoraFin());
                bundle.putString("solicitudDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getSolicitud());
                bundle.putString("actividadDetail", itinerarioArrayList.get(rvItinerario.getChildAdapterPosition(view)).getActividad());

                itinerarioDetail.putExtras(bundle);
                startActivity(itinerarioDetail);
            }//onClick
        });

        //Evento onClick de FloattingButton
        fbAddReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ReservationFragment());
            }//onClick
        });
    }//onViewCreated

    //Busqueda por fecha
    public void buscarItinerario(Itinerario itinerario, String registro) {
        SQLiteDatabase sqLiteDatabase = conexionSQLiteHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM itinerario WHERE'" + registro + "'", null);
        if (cursor.moveToNext()) {
            while (cursor.moveToNext()) {
                itinerario.setArea(cursor.getString(1));
                itinerario.setFecha(cursor.getString(2));
                itinerario.setHoraInicio(cursor.getString(3));
                itinerario.setHoraFin(cursor.getString(4));
                itinerario.setSolicitud(cursor.getString(5));
                itinerario.setActividad(cursor.getString(6));
            }//while
        }//if
    }//buscarItinerario

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flListItinerario.getId(), fragment);
        fragmentTransaction.commit();
    }//setFragment

}//LastItinerarioFragment
