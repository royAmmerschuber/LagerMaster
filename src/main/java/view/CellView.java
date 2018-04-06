package view;

import controller.subControl.NewItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CellView implements Initializable, ModelObserver {
    private static final int HEADER_HEIGHT=15;
    private static final int ROW_HEIGHT=30;
    private Model mod;
    public int row;
    public int column;
    public int shelf;
    @FXML GridPane grid;

    @FXML
    public void add(){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/newItem.fxml"));
            Parent root = loader.load();
            Stage s=new Stage();
            s.setTitle("add Item");
            s.setScene(new Scene(root));
            s.setResizable(false);
            s.show();
            NewItem c=loader.getController();
            c.shelf=shelf;
            c.row=row;
            c.col=column;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mod=Model.getInst();
    }

    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column) {
        if(this.shelf==shelf&&this.row==row&&this.column==column){
            reloadItems();
        }

        return false;
    }

    public void reloadItems() {
        List<Item> items=mod.getShelf(shelf).getItems(row,column);
        AnchorPane ap=(AnchorPane) grid.getParent();
        ap.getChildren().remove(grid);
        try{
            grid=FXMLLoader.load(getClass().getResource("/mainParts/CellViewTable.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        ap.getChildren().add(grid);
        ((Button)grid.lookup("#deleteAll")).setOnAction(event -> {
            deleteAll();
        });
        for (Item i:items) {
            grid.getRowConstraints().add(new RowConstraints(35,35,35,Priority.NEVER,VPos.TOP,false));
            Label l=new Label(i.name);
            grid.add(l,0,grid.getRowConstraints().size()-1);
            l=new Label(Integer.toString(i.amount));
            grid.add(l,2,grid.getRowConstraints().size()-1);
            l=new Label(Float.toString(i.weight));
            grid.add(l,3,grid.getRowConstraints().size()-1);
            String type=i.getClass().getName().split("Item")[1];
            l=new Label(type);
            grid.add(l,1,grid.getRowConstraints().size()-1);
            String other;
            switch (type){
                default:{
                    other=((ItemBasic)i).custom;
                }break;
                case "Cpu":{
                    other="cores:"+((ItemCpu)i).cores+"\nclockspeed:"+((ItemCpu)i).clockSpeed;
                }break;
                case "Drive":{
                    other="capacity:"+((ItemDrive)i).capacity+"\ntype:"+((ItemDrive)i).type;
                }break;
            }
            l=new Label(other);
            grid.add(l,4,grid.getRowConstraints().size()-1);
            Button b=new Button("delete");
            b.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "are you sure?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    mod.getShelf(shelf).delete(row,column,i);
                }
            });
            grid.add(b,5,grid.getRowConstraints().size()-1);
        }
    }
    @FXML
    public void deleteAll(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "are you sure?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            mod.getShelf(shelf).deleteAll(row,column);
        }
    }

    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column, String action) {
        getUpdate(shelf, row, column);
        return false;
    }
}
