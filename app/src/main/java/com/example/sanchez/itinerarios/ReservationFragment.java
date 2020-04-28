package com.example.sanchez.itinerarios;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

import android.support.v4.app.Fragment;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment /*implements TimePickerDialog.OnTimeSetListener*/ {

    //Declaracion de variables
    private FrameLayout flReservation;

    private TextView lblArea;
    private Spinner spnArea;
    String SpinnerValue = "";

    private TextView lblDate;
    private Button btnDate;

    //Segunda forma implementando una clase externa: Esta clase externa contendra alguna confugiracion en un extends de DialogFragment
    private TextView lblStart;
    private Button btnStart;

    private TextView lblEnd;
    private Button btnEnd;

    private EditText edtRequest;
    private EditText edtDescription;

    private Button btnSave;
    private Button btnCancel;

    //Primera forma sin implemetar una clase externa
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    ConexionSQLiteHelper conexionSQLiteHelper;

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        //Enlazamos los componentes
        flReservation = getActivity().findViewById(R.id.flItinerarioActivity);
        lblArea = view.findViewById(R.id.lblArea);
        spnArea = view.findViewById(R.id.spnArea);
        lblDate = view.findViewById(R.id.lblDate);
        btnDate = view.findViewById(R.id.btnDate);
        lblStart = view.findViewById(R.id.lblStart);
        btnStart = view.findViewById(R.id.btnStart);
        lblEnd = view.findViewById(R.id.lblEnd);
        btnEnd = view.findViewById(R.id.btnEnd);
        edtRequest = view.findViewById(R.id.edtRequest);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);

        //Adaptador para llenar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_areas, android.R.layout.simple_spinner_item);
        spnArea.setAdapter(adapter);

        return view;

    }//onCreateView


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Evento del Spinner
        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                SpinnerValue = adapterView.getItemAtPosition(i + 1).toString();
                //Toast.makeText(getContext(), "SpinnerValue: " + SpinnerValue, Toast.LENGTH_SHORT).show();
            }//onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }//onNothingSelected
        });


        //Evento fecha: Segunda forma sin funcion callback
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }//onClick
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                String date = day + "/" + month + "/" + year;
                lblDate.setText(date);
            }//onDateSet
        };


        //Evento hora-inicio: Primera forma con funcion callback
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        lblStart.setText(hour + ":" + minute);
                    }//onTimeSet
                }, hour, minute, false);
                timePickerDialog.show();

            }//onClick
        });


        //Evento hora-termino: Segunda forma sin funcion callback
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute, false);
                timePickerDialog.show();
            }//onClick
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                lblEnd.setText(hour + ":" + minute);
            }//onTimeSet
        };

        //Evento onClick de btnSave
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUserSQL();
                Toast.makeText(getContext(), "¡Reservation has been saved sucessfull!", Toast.LENGTH_SHORT);
                setFragment(new ListItinerarioFragment());
            }//onClick
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setFragment(new ListItinerarioFragment());
                            }//setPositiveButton
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }//setNegativeButton
                        });
                AlertDialog title = alertDialog.create();
                title.setTitle("Alert");
                title.show();
            }//onClick
        });
    }//onViewCreated


    private void RegisterUserSQL() {
        try {
            conexionSQLiteHelper = new ConexionSQLiteHelper(getContext(), "db_itinerario", null, 1);
            SQLiteDatabase sqLiteDatabase = conexionSQLiteHelper.getWritableDatabase();

            String insert = "INSERT INTO itinerario (area,fecha,horaInicio,horaFin,solicitud,actividad) values('" + SpinnerValue + "','" + lblDate.getText().toString() + "','" + lblStart.getText().toString() + "','" + lblEnd.getText().toString() + "','" + edtRequest.getText().toString() + "','" + edtDescription.getText().toString() + "')";
            sqLiteDatabase.execSQL(insert);
            sqLiteDatabase.close();
            Toast.makeText(getContext(), "¡Insercion realizada con exito!", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(getContext(), "¡Ha ocurrido un error! :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }//catch
    }//RegisterUserSQL


/* Sin usar SQL
    private void RegisterUser() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getContext(),"db_itinerario",null,1);
        SQLiteDatabase sqLiteDatabase = conexionSQLiteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("area",lblArea.getText().toString());
        contentValues.put("fecha",lblDate.getText().toString());
        contentValues.put("horaInicio",lblStart.getText().toString());
        contentValues.put("horaFin",lblEnd.getText().toString());
        contentValues.put("solicitud",edtRequest.getText().toString());
        contentValues.put("actividad",edtDescription.getText().toString());

        Long idResultante = sqLiteDatabase.insert("db_itinerario",campoID,contentValues);
    }//RegisterUser
    */

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flReservation.getId(), fragment);
        fragmentTransaction.commit();
    }//setFragment

}//ReservationFragment
