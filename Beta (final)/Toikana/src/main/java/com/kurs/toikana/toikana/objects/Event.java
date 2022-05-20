package com.kurs.toikana.toikana.objects;

public class Event {
    String name;
    String date;
    String status;
    String pay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Event(String name, String date, String status, String pay){
        this.name = name;
        this.date = date;
        this.status = status;
        this.pay = pay;
    }
}
