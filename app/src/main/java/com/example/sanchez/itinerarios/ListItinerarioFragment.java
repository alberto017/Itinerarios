package com.example.sanchez.itinerarios;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;
import com.example.sanchez.itinerarios.model.Itinerario;

import java.sql.SQLInput;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItinerarioFragment extends Fragment {

    private RecyclerView rvItinerario;
    private FloatingActionButton fbAddReservation;
    private FrameLayout flListItinerario;

    private ArrayList<Itinerario> itinerarioArrayList;
    private ConexionSQLiteHelper conexionSQLiteHelper;

    private ItinerarioAdapter itinerarioAdapter = null;


    public ListItinerarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_itinerario, container, false);

        //Enlazamos los componentes
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

}
