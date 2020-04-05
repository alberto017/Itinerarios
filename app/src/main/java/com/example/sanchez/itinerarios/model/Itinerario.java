package com.example.sanchez.itinerarios.model;

public class Itinerario {

    private Integer id;
    private String area;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String solicitud;
    private String actividad;

    public Itinerario() {

    }

    public Itinerario(Integer id, String area, String fecha, String horaInicio, String horaFin, String solicitud, String actividad) {
        this.id = id;
        this.area = area;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.solicitud = solicitud;
        this.actividad = actividad;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
