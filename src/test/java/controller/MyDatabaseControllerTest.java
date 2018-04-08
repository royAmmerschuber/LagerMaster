package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyDatabaseControllerTest {
    private static MyDatabaseController db;
    @BeforeEach
    void setUp(){
        db=MyDatabaseController.getInst();
        db.query("drop table shelf,itemdrive,itembasic,itemcpu,item;");
        db.seed();
        db.query("insert into shelf(name, rows, columns) VALUES (?,?,?),(?,?,?)",
                "name",84,2,
                "shalf",3,20
        );
        db.query(
                "insert into item(shelfFK, name, weight, amount, row, `column`) VALUES " +
                        "(1,?,?,?,?,?),(1,?,?,?,?,?),(1,?,?,?,?,?),(1,?,?,?,?,?)",
                "paul1",37.97,32, 47,2,
                "paul1",37.97,32,         47,2,
                "paul2",3.97,132,         47,1,
                "paul3",7.97,2,           42,0
        );
        db.query("insert into itemdrive(itemFK, capacity, type) VALUES (?,?,?),(?,?,?)",
                1,27.8,"ssd",
                3,98,"hdd"
        );
        db.query("insert into itembasic(itemFK, custom) VALUES (?,?),(?,?)",
                2,"paul2",
                4,"paul4"
        );
    }

    //Shelfs
    @Test
    void insertShelf() throws SQLException {
        db.insertShelf(new Shelf(5,9,"ashelf",-1));
        ResultSet rs=db.query("select id, name, rows, columns from shelf");
        boolean b=false;
        while(rs.next()){
            if(rs.getString("name").equals("ashelf")&&
                    rs.getInt("rows")==5&&
                    rs.getInt("columns")==9)
                b=true;
        }
        assertTrue(b);
    }

    @Test
    void getShelfs() throws SQLException {
        List<Shelf> s=db.getShelfs();
        ResultSet rs=db.query("select * from shelf");
        int i=0;
        while(rs.next()){
            assertEquals(rs.getInt("id"),s.get(i).id);
            assertEquals(rs.getString("name"),s.get(i).name);
            assertEquals(rs.getInt("columns"),s.get(i).getWidth());
            assertEquals(rs.getInt("rows"),s.get(i).getHeight());
            i++;
        }


    }

    @Test
    void deleteShelf() throws SQLException {
        db.deleteShelf(Model.getInst().getShelf(0).id);
        ResultSet rs=db.query("select * from shelf");
        rs.next();
        assertFalse(rs.next());

    }

    //Items
    @Test
    void insertItemBasic() throws SQLException {
        db.insertItem(
                Model.getInst().getShelf(0).id,
                4,
                9,
                ItemFactory.newItem("it",48,3.1f,"blabal")
        );
        ResultSet rs=db.query(""+
                "select * " +
                "from item as i " +
                    "right join itembasic as b on b.itemFK=i.id"
        );
        boolean b=false;
        while(rs.next()){
            if(rs.getString("name").equals("it") &&
                    rs.getInt("row")==4&&
                    rs.getInt("column")==9&&
                    rs.getInt("amount")==48&&
                    rs.getFloat("weight")==3.1f&&
                    rs.getString("custom").equals("blabal")
            )b=true;
        }
        assertTrue(b);

    }
    @Test
    void insertItemCPU() throws SQLException {
        db.insertItem(
                Model.getInst().getShelf(0).id,
                4,
                9,
                ItemFactory.newItem("it",48,3.1f,4839,1)
        );
        ResultSet rs=db.query(""+
                "select * " +
                "from item as i " +
                "right join itemcpu as b on b.itemFK=i.id"
        );
        boolean b=false;
        while(rs.next()){
            if(rs.getString("name").equals("it") &&
                    rs.getInt("row")==4&&
                    rs.getInt("column")==9&&
                    rs.getInt("amount")==48&&
                    rs.getFloat("weight")==3.1f&&
                    rs.getInt("cores")==1&&
                    rs.getInt("clockspeed")==4839
                    )b=true;
        }
        assertTrue(b);
    }
    @Test
    void insertItemDrive() throws SQLException {
        db.insertItem(
                Model.getInst().getShelf(0).id,
                4,
                9,
                ItemFactory.newItem("it",48,3.1f,37.02f,HardDriveType.ssd)
        );
        ResultSet rs=db.query(""+
                "select * " +
                "from item as i " +
                "right join itemdrive as b on b.itemFK=i.id"
        );
        boolean b=false;
        while(rs.next()){
            if(rs.getString("name").equals("it") &&
                    rs.getInt("row")==4&&
                    rs.getInt("column")==9&&
                    rs.getInt("amount")==48&&
                    rs.getFloat("weight")==3.1f&&
                    rs.getFloat("capacity")==37.02f&&
                    rs.getString("type").equals("ssd")
                    )b=true;
        }
        assertTrue(b);
    }

    @Test
    void getItems() throws SQLException {
        List<Item> li=db.getItems(Model.getInst().getShelf(0).id,47,2);
        ResultSet rs=db.query("" +
                "select id, shelfFK, name, weight, amount, row, `column`, custom, capacity, type, c.itemFK, cores, clockspeed " +
                "from item as i " +
                    "left join itembasic as b on b.itemFK=i.id " +
                    "left join itemdrive as d on d.itemFK=i.id " +
                    "left join itemcpu as c on c.itemFK=i.id " +
                "where row=47 and" +
                    " `column`=2"
        );
        rs.next();
        int i=0;
        //<editor-fold desc="test">
        assertEquals(rs.getString("name"),li.get(i).name);
        assertEquals(rs.getInt("amount"),li.get(i).amount);
        assertEquals(rs.getFloat("weight"),li.get(i).weight);
        assertEquals(rs.getFloat("capacity"),((ItemDrive)li.get(i)).capacity);
        assertEquals(rs.getString("type"),((ItemDrive)li.get(i)).type.toString());
        //</editor-fold>
        rs.next();
        i++;
        //<editor-fold desc="test">
        assertEquals(rs.getString("name"),li.get(i).name);
        assertEquals(rs.getInt("amount"),li.get(i).amount);
        assertEquals(rs.getFloat("weight"),li.get(i).weight);
        assertEquals(rs.getString("custom"),((ItemBasic)li.get(i)).custom);
        //</editor-fold>

        assertFalse(rs.next());
    }

    @Test
    void deleteItem() throws SQLException {
        db.deleteItem(Model.getInst().getShelf(0).getItems(47,1).get(0).id);
        assertFalse(db.query("select * from item where row=47 and `column`=1").next());
    }
    @Test
    void deleteItems() throws SQLException {
        db.deleteItems(1,47,2);
        assertFalse(db.query("select * from item where row=47 and `column`=2").next());
    }

}