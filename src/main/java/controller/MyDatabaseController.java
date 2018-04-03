package main.java.controller;

import java.sql.DriverManager;

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
                "Create table Shelf(" +
                "   id int not null PRIMARY KEY AUTO_INCREMENT," +
                "   name VARCHAR(64) NOT NULL ," +
                "   rows int not null," +
                "   columns int not NULL " +
                ")"
        );
        query(
                "Create table Item(" +
                "   id int not null PRIMARY KEY AUTO_INCREMENT," +
                "   shelfFK int not null," +
                "   name VARCHAR(64) not null," +
                "   weight float not null," +
                "   amount int not null," +
                "   row int not null," +
                "   `column` int not null" +
                ")"
        );
        query(
                "Create table ItemBasic(" +
                    "itemFK int PRIMARY KEY NOT NULL ," +
                    "custom TEXT not null" +
                ")"
        );
        query(
                "create table ItemDrive(" +
                    "itemFK int PRIMARY KEY NOT NULL ," +
                    "capacity FLOAT NOT NULL ," +
                    "type ENUM('ssd','hdd') NOT NULL " +
                ")"
        );
        query(
                "create table ItemCpu(" +
                    "itemFK int NOT NULL PRIMARY KEY ," +
                    "cores int NOT NULL ," +
                    "clockspeed int NOT NULL " +
                ")"
        );
    }
}
