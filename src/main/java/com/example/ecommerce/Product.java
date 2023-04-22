package com.example.ecommerce;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id, String name, Double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Product> getAllData(){
        String query = "SELECT id, name, price From product";
        return fetchData(query);
    }
    public static ObservableList<Product> fetchData(String query){
        ObservableList<Product> data = FXCollections.observableArrayList();
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(query);
        try {
            while(rs.next()){
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                data.addAll(product);
            }
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }
}
