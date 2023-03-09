package com.example.gym.entities.service;

import com.example.gym.entities.main.Payments;
import com.jfoenix.controls.JFXButton;

public class Pending {
    private int pendingId;
    private String pendingDate;
    private final int daysRemain;
    private Payments payment;
    private boolean isPending;
    private JFXButton unPend;

    public Pending(int pendingId, String pendingDate, int daysRemain, Payments payment, boolean isPending) {
        this.pendingId = pendingId;
        this.pendingDate = pendingDate;
        this.daysRemain = daysRemain;
        this.payment = payment;
        this.isPending = isPending;
        this.unPend = new JFXButton("unpend");
        unPend.setStyle("-fx-background-color: #1e6e66;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");
    }

    public Pending(int daysRemain) {
        this.daysRemain = daysRemain;
    }

    public int getPendingId() {
        return pendingId;
    }

    public String getPendingDate() {
        return pendingDate;
    }

    public int getDaysRemain() {
        return daysRemain;
    }

    public Payments getPayment() {
        return payment;
    }

    public boolean isPending() {
        return isPending;
    }

    public JFXButton getUnPend() {
        return unPend;
    }

    @Override
    public String toString() {
        return "Pending{" + "pendingId=" + pendingId + ", pendingDate='" + pendingDate + '\'' + ", daysRemain=" + daysRemain + ", payment=" + payment + ", isPending=" + isPending + '}';
    }
}

