package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDatabaseControllerTest {
    @BeforeEach
    void setUp(){
        MyDatabaseController db=MyDatabaseController.getInst();
        db.query("drop table shelf,item,itemdrive,itembasic,itemcpu;");
        db.seed();
        db.query("insert into shelf(name, rows, columns) VALUES (?,?,?)",
                "name",
                84,
                2
        );
        db.query(
                "insert into item(shelfFK, name, weight, amount, row, `column`) VALUES " +
                        "(?,?,?,?,?,?),(?,?,?,?,?,?),(?,?,?,?,?,?)",
                1,"paul1",37.97,32,47,2,
                1,"paul2",3.97,132,47,1,
                1,"paul3",7.97,2,42,0
        );
    }


    @Test
    void getShelfs() {

    }

    @Test
    void deleteShelf() {
    }

    @Test
    void insertItem() {
    }

    @Test
    void getItems() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void deleteItems() {
    }

    @Test
    void insertShelf() {
    }
}