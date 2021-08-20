package com.example.Tripapp;

public class Trip {

    public int alarmId;
    public String tripId;
    public String title;
    public String startPoint;
    public String endPoint;
    public String tripKind;
    public double startLatitude;
    public double startLongitude;
    public double endLatitude;


    public double endLongitude;
    public String notes;
    public Boolean round;
    public String dateText;
    public String timeText;
    public int colorId;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;

    public Trip(int alarmId,
                String TripId,
                String title,
                String startPoint,
                String endPoint,
                String tripKind,
                double StartLatitude,
                double startLongitude,
                String notes,
                Boolean round,
                String dateText,
                String timeText,
                int colorId,
                int day,
                int month,
                int year,
                int hour,
                int minute) {
        this.alarmId = alarmId;
        this.tripId = TripId;
        this.title = title;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tripKind = tripKind;
        this.startLatitude = StartLatitude;
        this.startLongitude = startLongitude;
        this.notes = notes;
        this.round = round;
        this.dateText = dateText;
        this.timeText = timeText;
        this.colorId = colorId;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }


    public Trip() {
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getTripKind() {
        return tripKind;
    }

    public void setTripKind(String tripKind) {
        this.tripKind = tripKind;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getRound() {
        return round;
    }

    public void setRound(Boolean round) {
        this.round = round;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}




