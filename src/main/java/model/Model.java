package model;

import controller.MyDatabaseController;

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
        db.query(
                "insert into shelf" +
                    "(name, rows, columns) " +
                "VALUES " +
                    "(?,?,?)",
                s.name,
                s.getHeight(),
                s.getHeight()
        );
    }
    public Shelf getShelf(int index){
        return shelfs.get(index);
    }
    public int countShelfs(){
        return shelfs.size();
    }

    @Override
    public void sendUpdate(Integer shelf, Integer row, Integer column) {
        for (ModelObserver o:observers) {
            o.getUpdate(shelf,row,column);
        }
    }

    public void reload() {

    }
}
