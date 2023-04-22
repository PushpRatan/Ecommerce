package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer, Product product){
        String groupOrderId = "select max(group_order_id) +1 id From orders";
        DbConnection conn = new DbConnection();
        try {
            ResultSet rs = conn.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder = "Insert Into orders(group_order_id, customerId, productId)" +
                        " Values("+rs.getInt("id")+", "+customer.getId()+", "+product.getId()+")";
                return conn.updateDB(placeOrder)!=0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static int placeCartOrder(Customer customer, ObservableList<Product> productList){
        String groupOrderId = "select max(group_order_id) +1  id From orders";
        DbConnection conn = new DbConnection();
        int count = 0;
        try {
            ResultSet rs = conn.getQueryTable(groupOrderId);
            if(rs.next()){
                for(Product product: productList){
                    String placeOrder = "Insert Into orders(group_order_id, customerId, productId)" +
                            " Values("+rs.getInt("id")+", "+customer.getId()+", "+product.getId()+")";
                    count += conn.updateDB(placeOrder);
                }
                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }



}
