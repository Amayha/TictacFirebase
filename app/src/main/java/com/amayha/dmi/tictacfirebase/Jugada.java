package com.amayha.dmi.tictacfirebase;

public class Jugada {
    private String uid;
    private int x;
    private int y;
    private String quienSoy;

    public Jugada() {
    }

    public Jugada(String uid, int y, int x) {
        this.uid = uid;
        this.x = x;
        this.y = y;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
