package com.kurs.toikana.toikana.objects;

import javafx.scene.control.TableColumn;

public class Food extends TableColumn<Food, Object> {
    String name, price;
    String amount;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Food(String name, String price, String amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
