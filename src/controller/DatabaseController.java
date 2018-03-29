package controller;

import java.sql.*;

public class DatabaseController {
    //Singleton stuff
    private static DatabaseController instance;
    public static DatabaseController getDatabase(){
        if(instance==null){
            instance=new DatabaseController();
        }
        return instance;
    }


    //actual functionality
    private static final String DB_URL="jdbc:mysql://localhost/326";
    private static final String USER="root";
    private static final String PASS="";
    private Connection con;

    private DatabaseController(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query, Object... param){
        try {
            PreparedStatement p=con.prepareStatement(query);
            for (int i=0;i<param.length;i++) {
                p.setObject(i+1,param[i]);
            }
            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
