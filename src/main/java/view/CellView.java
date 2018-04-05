package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Item;
import model.Model;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CellView implements Initializable {
    private static final int HEADER_HEIGHT=15;
    private static final int ROW_HEIGHT=30;
    public int row;
    public int column;
    public int shelf;
    @FXML GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void update(List<Item> items){

    }
}
