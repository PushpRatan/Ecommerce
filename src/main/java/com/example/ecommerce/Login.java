package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {
    public Customer customerLogin(String username, String password){
         String loginQuery = "Select * From customer Where name = '" + username + "' And password = '"+password + "'";
         DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try {
            if(rs.next()){
                return new Customer (rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("mobile"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
