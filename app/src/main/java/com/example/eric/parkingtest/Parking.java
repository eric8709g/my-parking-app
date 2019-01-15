package com.example.eric.parkingtest;

public class Parking {
    private String pArea;
    private String pName;
    private String pServiceTime;
    private String pAddress;
    private double pTW97X,pTW97Y;

    public Parking() {
    }

    public Parking(String pArea, String pName, String pServiceTime, String pAddress, double pTW97X, double pTW97Y) {
        this.pArea = pArea;
        this.pName = pName;
        this.pServiceTime = pServiceTime;
        this.pAddress = pAddress;
        this.pTW97X = pTW97X;
        this.pTW97Y = pTW97Y;

    }

    public String getpArea() {
        return pArea;
    }

    public String getpName() {
        return pName;
    }

    public String getpServiceTime() {
        return pServiceTime;
    }

    public String getpAddress() {
        return pAddress;
    }

    public double getpTW97X() {
        return pTW97X;
    }

    public double getpTW97Y() {
        return pTW97Y;
    }

    public void setpArea(String pArea) {
        this.pArea = pArea;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpServiceTime(String pServiceTime) {
        this.pServiceTime = pServiceTime;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public void setpTW97X(double pTW97X) {
        this.pTW97X = pTW97X;
    }

    public void setpTW97Y(double pTW97Y) {
        this.pTW97Y = pTW97Y;
    }
}
