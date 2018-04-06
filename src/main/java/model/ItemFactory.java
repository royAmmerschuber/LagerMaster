package model;

import java.sql.ResultSet;

public class ItemFactory {
    public static Item newItem(String name,int amount,float weight,String custom){
        ItemBasic i=new ItemBasic();
        fillItem(i,name,amount,weight);
        i.custom=custom;
        return i;
    }//♦
    public static Item newItem(String name, int amount, float weight, int clockspeed, int cores){
        ItemCpu i=new ItemCpu();
        fillItem(i,name,amount,weight);
        i.clockSpeed=clockspeed;
        i.cores=cores;
        return i;
    }//♦
    public static Item newItem(String name, int amount, float weight, float capacity, HardDriveType type){
        ItemDrive i=new ItemDrive();
        fillItem(i,name,amount,weight);
        i.capacity=capacity;
        i.type=type;
        return i;
    }//♦
    public static Item newItem(ResultSet rs) {
        try{
            Item i;
            if(rs.getString("custom")!=null){
                i = newItem(rs.getString("name"),
                        rs.getInt("amount"),
                        rs.getFloat("weight"),
                        rs.getString("custom"));
            }else if(rs.getInt("cores")!=0){
                i = newItem(rs.getString("name"),
                        rs.getInt("amount"),
                        rs.getFloat("weight"),
                        rs.getInt("clockspeed"),
                        rs.getInt("cores"));
            }else if(rs.getFloat("capacity")!=0){
                i = newItem(rs.getString("name"),
                        rs.getInt("amount"),
                        rs.getFloat("weight"),
                        rs.getFloat("capacity"),
                        HardDriveType.valueOf(rs.getString("type")));
            }else{
                return null;
            }
            i.id=rs.getInt("id");
            return i;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void fillItem(Item i,String name,int amount,float weight){
        i.name=name;
        i.amount=amount;
        i.weight=weight;
    }
}
