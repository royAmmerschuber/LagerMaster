package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Item;
import model.Model;
import model.ModelObserver;

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
            Parent root = FXMLLoader.load(getClass().getResource("/newItem.fxml"));
            Stage s=new Stage();
            s.setTitle("add Item");
            s.setScene(new Scene(root));
            s.setResizable(false);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mod=Model.getInst();
        reloadItems();
    }

    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column) {
        if(this.shelf==shelf&&this.row==row&&this.column==column){
            reloadItems();
        }

        return false;
    }

    private void reloadItems() {

    }


    @Override
    public boolean getUpdate(Integer shelf, Integer row, Integer column, String action) {
        getUpdate(shelf, row, column);
        return false;
    }
}
