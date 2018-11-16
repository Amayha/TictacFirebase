package com.amayha.dmi.tictacfirebase;

public class SeleccionJugador {
    private String uid;
    private String Seleccionado;

    public SeleccionJugador() {
    }

    public SeleccionJugador(String uid, String seleccionado) {
        this.uid = uid;
        this.Seleccionado = seleccionado;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSeleccionado() {
        return Seleccionado;
    }

    public void setSeleccionado(String seleccionado) {
        this.Seleccionado = seleccionado;
    }
}
