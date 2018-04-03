package main.java.model;

import main.java.controller.MyDatabaseController;

import java.util.ArrayList;
import java.util.List;

public class Model extends ModelObservable {
    //Singleton Stuff
    private static Model instance;
    public static Model getInst(){
        if(instance==null){
            instance=new Model();
        }
        return instance;
    }
    //Other Stuff

    List<Shelf> shelfs;
    protected Model() {
        shelfs=new ArrayList<>();
    }

    public void addShelf(Shelf s) {
        shelfs.add(s);
        sendUpdate(shelfs.indexOf(s),null,null);
        MyDatabaseController db=MyDatabaseController.getInstance();
        db.query("insert into shelf()");
    }


    @Override
    public void sendUpdate(Integer shelf, Integer row, Integer column) {
        for (ModelObserver o:observers) {
            o.getUpdate(shelf,row,column);
        }
    }
}
