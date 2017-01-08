package dev.linhnv.loginregister1;

import java.util.Date;

/**
 * Created by DevLinhnv on 12/29/2016.
 */

public class Product {
    int id;
    String name;
    int price;
    String description;
    Date create_date;
    Date update_date;
    public Product(){

    }

    public Product(int id, String name, int price, String description, Date create_date, Date update_date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.create_date = create_date;
        this.update_date = update_date;
    }
}
