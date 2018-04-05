package model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelObservable {
    protected List<ModelObserver> observers=new ArrayList<>();
    public void addObserver(ModelObserver o){
        observers.add(o);
    }
    public void removeObserver(ModelObserver o){
        observers.remove(o);
    }

    public abstract void sendUpdate(Integer shelf, Integer row, Integer column, String action);

    public abstract void sendUpdate(Integer shelf, Integer row, Integer column);

}
