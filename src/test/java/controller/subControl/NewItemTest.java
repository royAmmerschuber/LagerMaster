package controller.subControl;


import controller.MyDatabaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class NewItemTest extends ApplicationTest{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader(NewItem.class.getResource("/newItem.fxml"));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        NewItem c=loader.getController();
        c.shelf=0;
        c.row=3;
        c.col=1;
    }

    @BeforeEach
    void setUp(){
        MyDatabaseController db=MyDatabaseController.getInst();
        db.query("drop table shelf,item,itemdrive,itembasic,itemcpu");
        db.seed();
        Model m=Model.getInst();
        m.reload();
        m.newShelf(new Shelf(5,2,"shelft1",-1));

    }
    @AfterEach
    void tearDown() throws Exception{
        try {

            FxToolkit.hideStage();
            release(new KeyCode[]{});
            release(new MouseButton[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@Test
    void okBasic(){
        clickOn("#name");
        write("testname1");
        clickOn("#amount");
        write("4m7?");
        clickOn("#weight");
        write("3bafefa9.afdkfa276faf");
        clickOn("#extra1");
        write("testable extras written by me");
        clickOn("#ok");
        ItemBasic i=(ItemBasic) Model.getInst().getShelf(0).getItems(5,2).get(0);
        assertEquals(i.custom,"testable extras written by me");
        assertEquals(i.name,"testname1");
        assertEquals(i.weight,39.276);
        assertEquals(i.amount,47);
    }
    //@Test
    void okCPU(){
        clickOn("#type");
        clickOn("#cpu");
        clickOn("#name");
        write("testname1");
        clickOn("#amount");
        write("4m7?");
        clickOn("#weight");
        write("3bafefa9.afdkfa276faf");
        clickOn("#extra1");
        write("678");
        clickOn("#extra2");
        write("668");
        clickOn("#ok");
        ItemCpu i=(ItemCpu) Model.getInst().getShelf(0).getItems(5,2).get(0);
        assertEquals(i.cores,678);
        assertEquals(i.clockSpeed,668);
        assertEquals(i.name,"testname1");
        assertEquals(i.weight,39.276);
        assertEquals(i.amount,47);
    }
    //@Test
    void okDrive(){
        clickOn("#type");
        clickOn("#cpu");
        clickOn("#name");
        write("testname1");
        clickOn("#amount");
        write("4m7?");
        clickOn("#weight");
        write("3bafefa9.afdkfa276faf");
        clickOn("#extra1");
        write("67.8");
        clickOn("#extra2");
        write("ssd");
        clickOn("#ok");
        ItemDrive i=(ItemDrive) Model.getInst().getShelf(0).getItems(5,2).get(0);
        assertEquals(i.capacity,67.8);
        assertEquals(i.type.toString(),"ssd");
        assertEquals(i.name,"testname1");
        assertEquals(i.weight,39.276);
        assertEquals(i.amount,47);
    }
}