package main.java.controller;

import java.sql.*;

public abstract class DatabaseController {
    //actual functionality
    protected Connection con;

    public ResultSet query(String query, Object... param){
        try {
            PreparedStatement p=con.prepareStatement(query);
            for (int i=0;i<param.length;i++) {
                p.setObject(i+1,param[i]);
            }
            try{
                return p.executeQuery();

            }catch(Exception e){
                p.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void seed();
}
