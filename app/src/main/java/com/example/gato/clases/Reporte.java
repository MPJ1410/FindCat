package com.example.gato.clases;

import java.io.Serializable;
import java.util.Date;

public class Reporte implements Serializable {
    private int id;
    private String evidencia;
    private String tipoIncidente;
    private String descripcion;
    private Date FechaHora;
    private String linkUbicacion;
    private String cliente;

    public Reporte() {
    }

    public String getTipoIncidente() {
        return tipoIncidente;
    }

    public void setTipoIncidente(String tipoIncidente) {
        this.tipoIncidente = tipoIncidente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaHora() {
        return FechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        FechaHora = fechaHora;
    }

    public String getLinkUbicacion() {
        return linkUbicacion;
    }

    public void setLinkUbicacion(String linkUbicacion) {
        this.linkUbicacion = linkUbicacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }



    public Reporte(int id, String evidencia, String descripcion, String tipoIncidente, Date fechaHora, String linkUbicacion, String cliente) {
        this.id = id;
        this.evidencia = evidencia;
        this.descripcion = descripcion;
        this.tipoIncidente = tipoIncidente;
        FechaHora = fechaHora;
        this.linkUbicacion = linkUbicacion;
        this.cliente = cliente;
    }




}
