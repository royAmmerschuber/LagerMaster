package controller;

import model.Shelf;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseController extends DatabaseController {
    private static final String DB_URL="jdbc:mysql://localhost/326";
    private static final String USER="root";
    private static final String PASS="";
    private static MyDatabaseController instance;
    public static MyDatabaseController getInstance(){
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
                    "custom TEXT not null" +
                ")"
        );
        query(
                "create table if not exists ItemDrive(" +
                    "itemFK int PRIMARY KEY NOT NULL ," +
                    "capacity FLOAT NOT NULL ," +
                    "type ENUM('ssd','hdd') NOT NULL " +
                ")"
        );
        query(
                "create table if not exists ItemCpu(" +
                    "itemFK int NOT NULL PRIMARY KEY ," +
                    "cores int NOT NULL ," +
                    "clockspeed int NOT NULL " +
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
}
