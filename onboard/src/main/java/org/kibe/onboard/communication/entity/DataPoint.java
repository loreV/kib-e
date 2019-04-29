package org.kibe.onboard.communication.entity;

public class DataPoint {

    private int m;
    private int l;
    private double h;

    private double t;

    public DataPoint(int m, int l, double h, double t) {
        this.m = m;
        this.l = l;
        this.h = h;
        this.t = t;
    }

    public int getM() {
        return m;
    }

    public int getL() {
        return l;
    }

    public double getH() {
        return h;
    }

    public double getT() {
        return t;
    }
}
