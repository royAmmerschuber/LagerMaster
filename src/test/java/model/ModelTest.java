package model;

import controller.MyDatabaseController;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {


    @BeforeEach
    void setUp() {
        MyDatabaseController db=MyDatabaseController.getInst();
        db.query(
                "drop TABLE if exists shelf,item,itemdrive,itembasic,itemcpu;"
        );
        db.seed();
        Model.getInst().reload();

    }
    @AfterAll
    static void tearDown(){
        MyDatabaseController db=MyDatabaseController.getInst();
        db.query(
                "drop TABLE if exists shelf,item,itemdrive,itembasic,itemcpu;"
        );
    }

    @Test
    void reload() {
        MyDatabaseController db=MyDatabaseController.getInst();
        Model m=Model.getInst();
        db.insertShelf(new Shelf(5,2,"testing1",-1));
        m.reload();
        Shelf s=m.getShelf(0);
        assertEquals(1,s.id);
        assertEquals("testing1",s.name);
        assertEquals(5,s.getHeight());
        assertEquals(2,s.getWidth());
    }
    @Test
    void newShelf() {
        MyDatabaseController db=MyDatabaseController.getInst();
        Model m=Model.getInst();
        m.newShelf(new Shelf(4,7,"testint1",-1));
        m.newShelf(new Shelf(6,3,"test836",-1));
        List<Shelf> shelfs=db.getShelfs();
        Shelf s1=m.getShelf(0);
        Shelf s2=shelfs.get(0);
        assertEquals(s1.id,s2.id);
        assertEquals(s1.name,s2.name);
        assertEquals(s1.getHeight(),s2.getHeight());
        assertEquals(s1.getWidth(),s2.getWidth());
        s1=m.getShelf(1);
        s2=shelfs.get(1);
        assertEquals(s1.id,s2.id);
        assertEquals(s1.name,s2.name);
        assertEquals(s1.getHeight(),s2.getHeight());
        assertEquals(s1.getWidth(),s2.getWidth());
    }
    @Test
    void deleteShelf() {
        MyDatabaseController db=MyDatabaseController.getInst();
        Model m=Model.getInst();
        m.newShelf(new Shelf(4,7,"testint1",-1));
        m.newShelf(new Shelf(6,3,"test836",-1));
        m.deleteShelf(0);
        try{
            m.getShelf(1);
            assertEquals(1,0);
        }catch (Exception e){
            assertEquals(1,1);
        }
        //<editor-fold desc="test">
        Shelf s1=m.getShelf(0);
        Shelf s2=db.getShelfs().get(0);
        assertEquals(s1.id,s2.id);
        assertEquals(s1.name,s2.name);
        assertEquals(s1.getHeight(),s2.getHeight());
        assertEquals(s1.getWidth(),s2.getWidth());
        //</editor-fold>
    }

    @Test
    void newItem(){
        MyDatabaseController db=MyDatabaseController.getInst();
        Model m=Model.getInst();
        m.newShelf(new Shelf(4,7,"testint1",-1));
        Shelf s=m.getShelf(0);
        s.newItem(2,6,ItemFactory.newItem("test2",44,2.374f,84,3));
        s.newItem(3,4,ItemFactory.newItem("test3",41,2.374f,"blafdfdaka"));
        s.newItem(1,5,ItemFactory.newItem("test4",93,2.374f,612.937f,HardDriveType.ssd));
        s.newItem(2,6,ItemFactory.newItem("test1",4,2.374f,"blafdka"));
        s.newItem(0,4,ItemFactory.newItem("test5",9,2.374f,12,387));
        Item i1,i2;
        //<editor-fold desc="test">
        i1=s.getItems(2,6).get(0);
        i2=db.getItems(s.id,2,6).get(0);
        assertEquals(i1.amount,i2.amount);
        assertEquals(i1.weight,i2.weight);
        assertEquals(i1.name,i2.name);
        assertEquals(i1.id,i2.id);
        assertEquals(((ItemCpu)i1).cores,((ItemCpu)i2).cores);
        assertEquals(((ItemCpu)i1).clockSpeed,((ItemCpu)i2).clockSpeed);
        //</editor-fold>
        //<editor-fold desc="test">
        i1=s.getItems(3,4).get(0);
        i2=db.getItems(s.id,3,4).get(0);
        assertEquals(i1.amount,i2.amount);
        assertEquals(i1.weight,i2.weight);
        assertEquals(i1.name,i2.name);
        assertEquals(i1.id,i2.id);
        assertEquals(((ItemBasic)i1).custom,((ItemBasic)i2).custom);
        //</editor-fold>
        //<editor-fold desc="test">
        i1=s.getItems(1,5).get(0);
        i2=db.getItems(s.id,1,5).get(0);
        assertEquals(i1.amount,i2.amount);
        assertEquals(i1.weight,i2.weight);
        assertEquals(i1.name,i2.name);
        assertEquals(i1.id,i2.id);
        assertEquals(((ItemDrive)i1).capacity,((ItemDrive)i2).capacity);
        assertEquals(((ItemDrive)i1).type.toString(),((ItemDrive)i2).type.toString());
        //</editor-fold>
        //<editor-fold desc="test">
        i1=s.getItems(2,6).get(1);
        i2=db.getItems(s.id,2,6).get(1);
        assertEquals(i1.amount,i2.amount);
        assertEquals(i1.weight,i2.weight);
        assertEquals(i1.name,i2.name);
        assertEquals(i1.id,i2.id);
        assertEquals(((ItemBasic)i1).custom,((ItemBasic)i2).custom);
        //</editor-fold>
        //<editor-fold desc="test">
        i1=s.getItems(0,4).get(0);
        i2=db.getItems(s.id,0,4).get(0);
        assertEquals(i1.amount,i2.amount);
        assertEquals(i1.weight,i2.weight);
        assertEquals(i1.name,i2.name);
        assertEquals(i1.id,i2.id);
        assertEquals(((ItemCpu)i1).cores,((ItemCpu)i2).cores);
        assertEquals(((ItemCpu)i1).clockSpeed,((ItemCpu)i2).clockSpeed);
        //</editor-fold>
    }
    @Test
    void removeItem(){
        MyDatabaseController db=MyDatabaseController.getInst();
        Model m=Model.getInst();
        m.newShelf(new Shelf(4,7,"testint1",-1));
        Shelf s=m.getShelf(0);
        s.newItem(2,6,ItemFactory.newItem("test1",44,2.374f,84,3));
        s.newItem(3,4,ItemFactory.newItem("test2",41,2.374f,"blafdfdaka"));
        s.newItem(1,5,ItemFactory.newItem("test3",93,2.374f,612.937f,HardDriveType.ssd));
        s.newItem(1,5,ItemFactory.newItem("test4",93,2.374f,612.937f,HardDriveType.ssd));
        s.newItem(1,5,ItemFactory.newItem("test5",93,2.374f,612.937f,HardDriveType.ssd));
        s.newItem(2,6,ItemFactory.newItem("test6",4,2.374f,"blafdka"));
        s.delete(1,5,s.getItems(1,5).get(1));

        s.deleteAll(2,6);
        s.deleteAll(3,4);


        assertEquals(s.getItems(1,5).size(),2);
        assertEquals(db.getItems(s.id,1,5).size(),2);

        assertEquals(s.getItems(2,4).size(),0);
        assertEquals(db.getItems(s.id,3,4).size(),0);

        assertEquals(s.getItems(2,4).size(),0);
        assertEquals(db.getItems(s.id,3,4).size(),0);


    }

}