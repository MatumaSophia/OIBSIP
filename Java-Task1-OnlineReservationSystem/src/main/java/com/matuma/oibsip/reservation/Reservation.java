package com.matuma.oibsip.reservation;

import java.time.LocalDate;

/**
 * Represents a single train reservation record.
 */
public class Reservation {

    private String pnr;
    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private LocalDate journeyDate;
    private String sourceStation;
    private String destinationStation;

    public Reservation() {
    }

    public Reservation(String pnr, String passengerName, String trainNumber, String trainName,
                       String classType, LocalDate journeyDate,
                       String sourceStation, String destinationStation) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    // Getters and setters

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr +
                "\nPassenger: " + passengerName +
                "\nTrain: " + trainNumber + " - " + trainName +
                "\nClass: " + classType +
                "\nDate: " + journeyDate +
                "\nRoute: " + sourceStation + " → " + destinationStation;
    }
}