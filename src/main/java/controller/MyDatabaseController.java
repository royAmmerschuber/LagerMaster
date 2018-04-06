package controller;

import model.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseController extends DatabaseController {
    private static final String DB_URL="jdbc:mysql://localhost/326";
    private static final String USER="root";
    private static final String PASS="";
    private static MyDatabaseController instance;
    public static MyDatabaseController getInst(){
        if(instance==null){
            instance=new MyDatabaseController();
        }
        return instance;
    }

    private MyDatabaseController(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seed() {
        query(
                "Create table if not exists Shelf(" +
                    "id int not null PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(64) NOT NULL ," +
                    "rows int not null," +
                    "columns int not NULL " +
                ")"
        );
        query(
                "Create table if not exists Item(" +
                    "id int not null PRIMARY KEY AUTO_INCREMENT," +
                    "shelfFK int not null," +
                    "name VARCHAR(64) not null," +
                    "weight float not null," +
                    "amount int not null," +
                    "row int not null," +
                    "`column` int not null" +
                ")"
        );
        query(
                "Create table if not exists ItemBasic(" +
                    "itemFK int PRIMARY KEY NOT NULL ," +
                    "custom TEXT not null," +
                    "FOREIGN KEY (`itemFK`) REFERENCES `item`(`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")"
        );
        query(
                "create table if not exists ItemDrive(" +
                    "itemFK int PRIMARY KEY NOT NULL ," +
                    "capacity FLOAT NOT NULL ," +
                    "type ENUM('ssd','hdd') NOT NULL, " +
                    "FOREIGN KEY (`itemFK`) REFERENCES `item`(`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")"
        );
        query(
                "create table if not exists ItemCpu(" +
                    "itemFK int NOT NULL PRIMARY KEY ," +
                    "cores int NOT NULL ," +
                    "clockspeed int NOT NULL," +
                    "FOREIGN KEY (`itemFK`) REFERENCES `item`(`id`) ON DELETE CASCADE ON UPDATE CASCADE " +
                ")"
        );
    }

    public List<Shelf> getShelfs() {
        List<Shelf> shelves=new ArrayList<>();
        ResultSet rs=query("select * from shelf");
        try{
        while(rs.next()){

            shelves.add(new Shelf(
                    rs.getInt("rows"),
                    rs.getInt("columns"),
                    rs.getString("name"),
                    rs.getInt("id")
            ));

        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return shelves;
    }
    public void deleteShelf(int index) {
        query("delete from shelf where id=?",index);
    }

    public int insertItem(int dbShelf,int row, int col, Item item){
        query("insert into item(shelfFK, name, weight, amount, row, `column`) VALUES (?,?,?,?,?,?);",
                dbShelf,
                item.name,
                item.weight,
                item.amount,
                row,
                col
        );
        ResultSet rs=query("select LAST_INSERT_ID()");

        try {
            rs.next();
            switch(item.getClass().getSimpleName()){
                case "ItemBasic":{
                    ItemBasic i=(ItemBasic)item;
                    query("insert into itembasic(itemFK, custom) VALUES (?,?)",
                            rs.getInt(1),
                            i.custom
                    );
                }break;
                case "ItemCpu":{
                    ItemCpu i=(ItemCpu)item;
                    query("insert into itemcpu(itemFK, cores, clockspeed) VALUES (?,?,?)",
                            rs.getInt(1),
                            i.cores,
                            i.clockSpeed);
                }break;
                case "ItemDrive":{
                    ItemDrive i=(ItemDrive)item;
                    query("insert into itemdrive(itemFK, capacity,type) VALUES (?,?,?)",
                            rs.getInt(1),
                            i.capacity,
                            i.type.toString());
                }break;

            }
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Item> getItems(int dbShelf, int row, int col) {
        ResultSet rs=query(
                "select * from item as i " +
                        "left join itemdrive as d on i.id=d.itemFK " +
                        "left join itemcpu as c on i.id=c.itemFK " +
                        "left join itembasic as b on i.id=b.itemFK " +
                "where shelfFK=? and " +
                        "row=? and " +
                        "`column`=?;",
                dbShelf,
                row,
                col
        );
        List<Item> items=new ArrayList<>();
        try {
            while (rs.next()) {
                items.add(ItemFactory.newItem(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    public void deleteItem(int dbId) {
        query("delete from item where id=?;",
                dbId
        );

    }
    public void deleteItems(int row,int col){
        query("delete from item where row=? and `column`=?;",
                row,col
        );
    }
}
