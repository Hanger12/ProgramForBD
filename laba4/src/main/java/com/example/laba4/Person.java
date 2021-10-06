package com.example.laba4;
public class Person
{
    private int idC;
    private String nameC;
    private String nameM;
    private int zarplata;
    private int idP;
    private String nameP;
    private String country;
    private int discharge;


    public Person(int idC, String nameC, String nameM, int zarplata, int idP, String nameP, String country, int discharge)
    {
        this.idC=idC;
        this.nameC=nameC;
        this.nameM = nameM;
        this.zarplata= zarplata;
        this.idP=idP;
        this.nameP=nameP;
        this.country = country;
        this.discharge = discharge;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public void setNameM(String nameM) {
        this.nameM = nameM;
    }

    public void setZarplata(int zarplata) {
        this.zarplata = zarplata;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDischarge(int discharge) {
        this.discharge = discharge;
    }

    public int getIdC() {
        return idC;
    }

    public String getNameC() {
        return nameC;
    }

    public String getNameM() {
        return nameM;
    }

    public int getZarplata() {
        return zarplata;
    }

    public int getIdP() {
        return idP;
    }

    public String getNameP() {
        return nameP;
    }

    public String getCountry() {
        return country;
    }

    public int getDischarge() {
        return discharge;
    }
}
