package main.java.view;

import javafx.scene.control.Accordion;
import main.java.model.ModelObserver;

public class ViewController implements ModelObserver {
    //singleton stuff
    private static ViewController instance;
    public static ViewController getInst(Accordion accord){
        if(instance==null){
            instance=new ViewController(accord);
        }
        return instance;
    }
    public static ViewController getInst(){
        return instance;
    }
    //other stuff
    Accordion accord;

    protected ViewController(Accordion accord) {
        this.accord = accord;
    }

    @Override
    public boolean getUpdate(int shelf, int row, int column) {
        return false;
    }
}
