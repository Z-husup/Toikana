package com.kurs.toikana.toikana.objects;

public class EventFoodMenu {
    String food, price, amount;

    public String getFood() {return food;}

    public void setFood(String food) {
        this.food = food;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public EventFoodMenu(String food, String price, String amount) {
        this.food = food;
        this.price = price;
        this.amount = amount;
    }
}
