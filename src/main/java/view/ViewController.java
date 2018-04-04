package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import model.Model;
import model.ModelObserver;
import model.Shelf;

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
    private Accordion accord;
    private Model model;

    protected ViewController(Accordion accord) {
        this.accord = accord;
        model=Model.getInst();
    }

    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column) {
        if(shelf!=null&&row==null&&column==null){
            if(accord.getPanes().size()>shelf){
                updateShelf(shelf);
            }else{
                addShelf();
            }
        }else{

        }
        return false;
    }

    private void updateShelf(Integer index) {
        Shelf s=model.getShelf(index);
        TitledPane p=accord.getPanes().get(index);
        GridPane g=(GridPane)((ScrollPane)p.getContent().lookup("#scroll")).getContent().lookup("#grid");
        p.setText(s.name);

        g.setGridLinesVisible(true);

    }

    private void addShelf() {
        try{
            Shelf shelf=model.getShelf(model.countShelfs()-1);
            TitledPane p=FXMLLoader.load(getClass().getResource("/mainParts/Shelf.fxml"));
            accord.getPanes().add(p);
            GridPane g=(GridPane)((ScrollPane)p.getContent().lookup("#scroll")).getContent().lookup("#grid");
            ColumnConstraints cc=new ColumnConstraints(120,120,120,Priority.SOMETIMES,HPos.CENTER,false);
            RowConstraints rc=new RowConstraints(Double.NEGATIVE_INFINITY,45,45,Priority.SOMETIMES,VPos.TOP,false);
            for(int i=0;i<shelf.getWidth();i++)
                g.getColumnConstraints().add(cc);
            for(int i=0;i<shelf.getHeight();i++)
                g.getRowConstraints().add(rc);
            updateShelf(model.countShelfs()-1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
