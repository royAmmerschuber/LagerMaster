package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.stage.Stage;
import model.*;
import view.ViewController;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable{

    Model mod;
    ViewController view;

    @FXML public Accordion accord;

    @FXML @Override
    public void initialize(URL location, ResourceBundle resources) {
        mod=Model.getInst();
        view=ViewController.getInst(accord);
        mod.addObserver(view);
        mod.addShelf(new Shelf(5,2,"paul",-7));
    }

    @FXML
    public void addShelf(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/newShelf.fxml"));
            Stage s=new Stage();
            s.setTitle("add Shelf");
            s.setScene(new Scene(root, 164, 123));
            s.setResizable(false);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void reloadData(){
        Model.getInst().reload();
    }
}
