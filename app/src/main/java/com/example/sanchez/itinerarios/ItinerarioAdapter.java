package com.example.sanchez.itinerarios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanchez.itinerarios.model.Itinerario;

import java.util.ArrayList;
import java.util.List;

public class ItinerarioAdapter extends RecyclerView.Adapter<ItinerarioAdapter.ItinerarioViewHolder> implements View.OnClickListener {

    private ArrayList<Itinerario> itinerarioArrayList;

    //Declaramos escuchador de onClickListener
    private View.OnClickListener onClickListener;

    //Constructor
    public ItinerarioAdapter(ArrayList<Itinerario> itinerarioArrayList) {
        this.itinerarioArrayList = itinerarioArrayList;
    }//ItinerarioAdapter


    @NonNull
    @Override
    public ItinerarioAdapter.ItinerarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_itinerario,null,false);
        //Agregamos escuchador
        view.setOnClickListener(this);
        return new ItinerarioViewHolder(view);
    }//onCreateViewHolder


    @Override
    public void onBindViewHolder(@NonNull ItinerarioAdapter.ItinerarioViewHolder itinerarioViewHolder, int i) {
        itinerarioViewHolder.lblNoItinerario.setText(String.valueOf(itinerarioArrayList.get(i).getId()));
        itinerarioViewHolder.lblArea.setText(itinerarioArrayList.get(i).getArea());
        itinerarioViewHolder.lblFecha.setText(itinerarioArrayList.get(i).getFecha());
        itinerarioViewHolder.lblHoraInicio.setText(itinerarioArrayList.get(i).getHoraInicio());
        itinerarioViewHolder.lblHoraFin.setText(itinerarioArrayList.get(i).getHoraFin());
        itinerarioViewHolder.lblSolicitado.setText(itinerarioArrayList.get(i).getSolicitud());
        itinerarioViewHolder.lblActividad.setText(itinerarioArrayList.get(i).getActividad());
    }//onBindViewHolder


    //Obtiene el tamaño del arreglo
    @Override
    public int getItemCount() {
        return itinerarioArrayList.size();
    }//getItemCount

    //Asignamos el escuchador al evento onClick
    @Override
    public void onClick(View view) {
        if(onClickListener != null){
            onClickListener.onClick(view);
        }//if
    }//onClick

    //Este sera el metodo al que llamaremos en el fragmento
    public void setOnClickListener (View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }//setOnCliclListener


    public class ItinerarioViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNoItinerario;
        private TextView lblArea;
        private TextView lblFecha;
        private TextView lblHoraInicio;
        private TextView lblHoraFin;
        private TextView lblSolicitado;
        private TextView lblActividad;

        public ItinerarioViewHolder(@NonNull View itemView) {
            super(itemView);

            lblNoItinerario = itemView.findViewById(R.id.lblNoItinerario);
            lblArea = itemView.findViewById(R.id.lblItemArea);
            lblFecha = itemView.findViewById(R.id.lblItemFecha);
            lblHoraInicio = itemView.findViewById(R.id.lblItemHoraInicio);
            lblHoraFin = itemView.findViewById(R.id.lblItemHoraFin);
            lblSolicitado = itemView.findViewById(R.id.lblItemSolicitado);
            lblActividad = itemView.findViewById(R.id.lblItemActividad);
        }//ItinerarioViewHolder
    }//ItinerarioViewHolder

}//ItinerarioAdapter
