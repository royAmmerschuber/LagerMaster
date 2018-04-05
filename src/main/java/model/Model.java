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
        reload();
    }

    public void newShelf(Shelf s) {
        addShelf(s);
        MyDatabaseController db=MyDatabaseController.getInstance();
        db.query(
                "insert into shelf" +
                    "(name, rows, columns) " +
                "VALUES " +
                    "(?,?,?)",
                s.name,
                s.getHeight(),
                s.getWidth()
        );
    }
    public void addShelf(Shelf s){
        shelfs.add(s);
        sendUpdate(shelfs.indexOf(s),null,null);
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
        shelfs=MyDatabaseController.getInstance().getShelfs();
        sendUpdate(null,null,null);
    }

    public void deleteShelf(int index) {
        MyDatabaseController.getInstance().deleteShelf(index);
        shelfs.remove(index);
    }
}
