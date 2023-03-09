package com.example.gym.entities.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gym {
    private int gymId;
    private String gymName;
    private String zaad;
    private String eDahab;
    private double fitnessCost;
    private double poxingCost;
    private double boxCost;
    private int pendingDate;
    private double maxDiscount;

    private final ObservableList<Box> vipBoxes;

    public Gym(int gymId, String gymName, double fitnessCost, double poxingCost, double boxCost, double maxDiscount, int pendingDate, String zaad, String eDahab) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.fitnessCost = fitnessCost;
        this.poxingCost = poxingCost;
        this.boxCost = boxCost;
        this.maxDiscount = maxDiscount;
        this.zaad = zaad;
        this.eDahab = eDahab;
        this.pendingDate = pendingDate;
        this.vipBoxes = FXCollections.observableArrayList();
    }

    public int getGymId() {
        return gymId;
    }

    public String getGymName() {
        return gymName;
    }

    public String getZaad() {
        return zaad;
    }

    public String geteDahab() {
        return eDahab;
    }

    public double getFitnessCost() {
        return fitnessCost;
    }

    public double getPoxingCost() {
        return poxingCost;
    }

    public double getBoxCost() {
        return boxCost;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public int getPendingDate() {
        return pendingDate;
    }

    public ObservableList<Box> getVipBoxes() {
        return vipBoxes;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public void setZaad(String zaad) {
        this.zaad = zaad;
    }

    public void seteDahab(String eDahab) {
        this.eDahab = eDahab;
    }

    public void setFitnessCost(double fitnessCost) {
        this.fitnessCost = fitnessCost;
    }

    public void setPoxingCost(double poxingCost) {
        this.poxingCost = poxingCost;
    }

    public void setBoxCost(double boxCost) {
        this.boxCost = boxCost;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setPendingDate(int pendingDate) {
        this.pendingDate = pendingDate;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "gymId=" + gymId +
                ", gymName='" + gymName + '\'' +
                ", zaad='" + zaad + '\'' +
                ", eDahab='" + eDahab + '\'' +
                ", fitnessCost=" + fitnessCost +
                ", poxingCost=" + poxingCost +
                ", boxCost=" + boxCost +
                ", pendingDate=" + pendingDate +
                ", maxDiscount=" + maxDiscount +
                ", vipBoxes=" + vipBoxes +
                '}';
    }
}
