package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Item;
import model.Model;
import model.ModelObserver;
import model.Shelf;
import java.util.ArrayList;
import java.util.List;

public class ViewController implements ModelObserver {
    //Constants
    private static final int CELL_WIDTH=150;
    private static final int CELL_HEIGHT=70;

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
    private List<CellView> cellViews;
    private Accordion accord;
    private Model model;

    protected ViewController(Accordion accord) {
        this.accord = accord;
        model=Model.getInst();
        cellViews=new ArrayList<>();
    }

    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column) {
        if(shelf!=null){
            if(row==null&&column==null) {
                if (accord.getPanes().size() > shelf) {
                    updateShelf(shelf);
                } else {
                    addShelf(accord.getPanes().size());
                }
                return true;
            }else if(row!=null&&column!=null){
                updateCell(shelf,row,column);
                return true;
            }
        }else{
            accord.getPanes().clear();
            for(int i=0;i<model.countShelfs();i++){
                addShelf(i);
            }
        }
        return false;
    }

    private void updateCell(Integer shelf, Integer row, Integer column){
        updateCell(
                model.getShelf(shelf),
                (GridPane)((ScrollPane)accord.getPanes().get(shelf).getContent().lookup("#scroll")).getContent().lookup("#grid"),
                shelf,
                row,
                column
        );
    }
    private void updateCell(Shelf shelf,GridPane grid,Integer shelfId, Integer row,Integer column){
        List<Item> items=shelf.getItems(row,column);
        Button cell=(Button) getNodeFromGridPane(grid,column,row);
        if(cell==null){
            try {
                cell = FXMLLoader.load(getClass().getResource("/mainParts/cell.fxml"));
                cell.setOnAction(event -> {
                    try{
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("/CellView.fxml"));
                        Parent root = loader.load();
                        Stage s=new Stage();
                        s.setTitle("shelf"+shelf.name+",Col:"+column+",Row:"+row);
                        s.setScene(new Scene(root));
                        s.show();
                        int id=cellViews.size();
                        s.setOnCloseRequest(event1 -> {
                            s.close();
                            cellViews.remove(id);
                        });
                        CellView c=loader.getController();
                        c.column=column;
                        c.row=row;
                        c.shelf=shelfId;
                        cellViews.add(c);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                grid.add(cell,column,row);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(items.size()==0){
            cell.setText("Empty");

        }else if(items.size()==1){
            Item i=items.get(0);
            cell.setText("name: "+i.name+
                    "\namount: "+i.amount+
                    "\ntype: "+i.getClass().getName().split("Item")[1]
            );
        }else{
            int weight=0;
            int amount=0;
            String type1;
            String type2=items.get(0).getClass().getName();
            for (Item i:items) {
                amount+=i.amount;
                weight+=i.weight*i.amount;
                type1=i.getClass().getName();
                type2=type1==type2?type1:"multiple";
            }
            cell.setText("amount: "+amount+
                    "\ntype:"+type2+
                    "\nweight:"+weight
            );
        }
    }

    private void updateShelf(Integer index) {
        Shelf s=model.getShelf(index);
        TitledPane p=accord.getPanes().get(index);
        GridPane g=(GridPane)((ScrollPane)p.getContent().lookup("#scroll")).getContent().lookup("#grid");
        p.setText(s.name);
        for(int i=0;i<s.getHeight();i++){
            for(int j=0;j<s.getWidth();j++){
                updateCell(s,g,index,i,j);
            }
        }
    }

    private void addShelf(int index) {
        try{
            Shelf shelf=model.getShelf(index);
            TitledPane p=FXMLLoader.load(getClass().getResource("/mainParts/Shelf.fxml"));
            accord.getPanes().add(p);
            Button delete=(Button)p.getContent().lookup("delete");
            delete.setOnAction(event -> {
                model.deleteShelf(index);
            });
            GridPane g=(GridPane)((ScrollPane)p.getContent().lookup("#scroll")).getContent().lookup("#grid");
            ColumnConstraints cc=new ColumnConstraints(CELL_WIDTH,CELL_WIDTH,CELL_WIDTH,Priority.SOMETIMES,HPos.CENTER,false);
            RowConstraints rc=new RowConstraints(Double.NEGATIVE_INFINITY,CELL_HEIGHT,CELL_HEIGHT,Priority.SOMETIMES,VPos.TOP,false);
            for(int i=0;i<shelf.getWidth();i++)
                g.getColumnConstraints().add(cc);
            for(int i=0;i<shelf.getHeight();i++)
                g.getRowConstraints().add(rc);
            updateShelf(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {

            if (!(node instanceof Group)&&GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}
