package controller;

import java.sql.*;

public abstract class DatabaseController {
    //actual functionality
    protected Connection con;
    private static final boolean DEBUG =true;
    public ResultSet query(String query, Object... param){
        try {
            PreparedStatement p=con.prepareStatement(query);
            for (int i=0;i<param.length;i++) {
                p.setObject(i+1,param[i]);
            }
            try{
                ResultSet rs=p.executeQuery();
                if(DEBUG) System.out.println("dataMod: "+query);
                return rs;

            }catch(Exception e){
                if(DEBUG) System.out.println("structMod: "+query);
                p.execute();
            }
        } catch (SQLException e) {
            if(DEBUG) System.out.println("queryFailed");
            e.printStackTrace();
        }
        return null;
    }

    public abstract void seed();
}
