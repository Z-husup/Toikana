package com.kurs.toikana.toikana.objects;

public class Order {
    int id;
    String pay;
    String name,date,status;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
    public Order(int id, String name, String date , String status, String pay){
        this.id = id;
        this.name = name;
        this.date = date;
        this.status = status;
        this.pay = pay;
    }
}
