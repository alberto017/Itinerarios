package com.example.sanchez.itinerarios;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sanchez.itinerarios.model.ConexionSQLiteHelper;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class ItinerarioDetail extends AppCompatActivity {

    private TextView lblDetailNoSolicitud;
    private TextView lblDetailArea;
    private Spinner spnDetailArea;
    String SpinnerDetailValue = "";
    private TextView lblDetailDate;
    private Button btnDetailDate;
    private TextView lblDetailStart;
    private Button btnDetailStart;
    private TextView lblDetailEnd;
    private Button btnDetailEnd;
    private EditText edtDetailRequest;
    private EditText edtDetailDescription;
    private Button btnDetailEliminar;
    private Button btnDetailActualizar;

    //Primera forma sin implemetar una clase externa
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    ConexionSQLiteHelper conexionSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario_detail);

        //Enlazamos los componentes
        lblDetailNoSolicitud = findViewById(R.id.lblDetailNoSolicitud);
        lblDetailArea = findViewById(R.id.lblArea);
        spnDetailArea = findViewById(R.id.spnArea);
        lblDetailDate = findViewById(R.id.lblDate);
        btnDetailDate = findViewById(R.id.btnDate);
        lblDetailStart = findViewById(R.id.lblStart);
        btnDetailStart = findViewById(R.id.btnStart);
        lblDetailEnd = findViewById(R.id.lblEnd);
        btnDetailEnd = findViewById(R.id.btnEnd);
        edtDetailRequest = findViewById(R.id.edtRequest);
        edtDetailDescription = findViewById(R.id.edtDescription);
        btnDetailEliminar = findViewById(R.id.btnDetailEliminar);
        btnDetailActualizar = findViewById(R.id.btnDetailActualizar);

        //Cargar datos
        cargarDatos();

        //Adaptador para llenar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.combo_areas, android.R.layout.simple_spinner_item);
        spnDetailArea.setAdapter(adapter);


        spnDetailArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lblDetailArea.setText(adapterView.getItemAtPosition(i).toString());
                //SpinnerDetailValue = adapterView.getItemAtPosition(i).toString();
            }//onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }//onNothingSelected
        });

        //Evento fecha: Segunda forma sin funcion callback
        btnDetailDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ItinerarioDetail.this,
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
                lblDetailDate.setText(date);
            }//onDateSet
        };


        //Evento hora-inicio: Primera forma con funcion callback
        btnDetailStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ItinerarioDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        lblDetailStart.setText(hour + ":" + minute);
                    }//onTimeSet
                }, hour, minute, false);
                timePickerDialog.show();
            }//onClick
        });


        //Evento hora-termino: Segunda forma sin funcion callback
        btnDetailEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ItinerarioDetail.this, timeSetListener, hour, minute, false);
                timePickerDialog.show();
            }//onClick
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                lblDetailEnd.setText(hour + ":" + minute);
            }//onTimeSet
        };


        //Evento onClick de btnSave
        btnDetailActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarItinerario();
                Toast.makeText(ItinerarioDetail.this, "¡Registro actualizado", Toast.LENGTH_SHORT).show();
            }//onClick
        });

        btnDetailEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarItinerario();
                Toast.makeText(ItinerarioDetail.this, "¡Registro eliminado", Toast.LENGTH_SHORT).show();
            }//onClick
        });
    }//onCreate

    private void actualizarItinerario() {
        SQLiteDatabase sqLiteDatabase = conexionSQLiteHelper.getWritableDatabase();

    }//actualizarItinerario

    private void eliminarItinerario() {

    }//actualizarItinerario


    private void cargarDatos() {
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            lblDetailNoSolicitud.setText(String.valueOf(bundle.getInt("noSolicitudDetail")));
            lblDetailArea.setText(bundle.getString("areaDetail"));
            lblDetailDate.setText(bundle.getString("fechaDetail"));
            lblDetailStart.setText(bundle.getString("horaInicioDetail"));
            lblDetailEnd.setText(bundle.getString("horaFinDetail"));
            edtDetailRequest.setText(bundle.getString("solicitudDetail"));
            edtDetailDescription.setText(bundle.getString("actividadDetail"));
        }//if
    }//cargarDatos

}//ItinerarioDetail
