package controller.subControl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.HardDriveType;
import model.Item;
import model.ItemFactory;
import model.Model;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;

import java.net.URL;
import java.util.ResourceBundle;
@Category(TestFX.class)
public class NewItem implements Initializable {
    @FXML public ChoiceBox type;
    @FXML public TextField name;
    @FXML public TextField amount;
    @FXML public TextField weight;
    @FXML public TextField extra1;
    @FXML public TextField extra2;
    private int currentType;
    public int shelf;
    public int row;
    public int col;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        extra1.textProperty().addListener((observable, oldValue, newValue) -> {
            extra1.setText(checkExtra1(newValue));
        });
        extra2.textProperty().addListener((observable, oldValue, newValue) -> {
            extra2.setText(checkExtra2(newValue,oldValue));
        });
        weight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[+-]?([0-9]*[.])?[0-9]+")) {
                weight.setText(newValue.replaceAll("[^[+\\-\\d.]]", ""));
            }
        });
        type.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            System.out.println(observableValue+" "+number+" "+number2);
            setType(number2.intValue());
        });
        setType(0);
    }
    private void setType(int id){
        extra1.setText("");
        extra2.setText("");
        currentType=id;
        switch(id){
            case 0:{
                extra1.setPromptText("Custom");
                extra2.setPromptText("");
                extra2.setDisable(true);


            }break;
            case 1:{
                extra1.setPromptText("cores");
                extra2.setPromptText("clockspeed");
                extra2.setDisable(false);


            }break;
            case 2:{
                extra1.setPromptText("space");
                extra2.setPromptText("type");
                extra2.setDisable(false);


            }break;
        }
    }
    @FXML
    public void ok(){
        if(
            (
                !extra2.getText().equals("") ||
                currentType==0
            )&&
            (
                extra2.getText().equals("ssd")||
                extra2.getText().equals("hdd")||
                currentType!=2
            )&&
            !extra1.getText().equals("") &&
            !name.getText().equals("") &&
            !amount.getText().equals("") &&
            !weight.getText().equals("")
        ){
            Item i;
            switch(currentType){
                default:{i=ItemFactory.newItem(
                        name.getText(),
                        Integer.parseInt(amount.getText()),
                        Float.parseFloat(weight.getText()),extra1.getText());}break;
                case 1:{i=ItemFactory.newItem(
                        name.getText(),
                        Integer.parseInt(amount.getText()),
                        Float.parseFloat(weight.getText()),
                        Integer.parseInt(extra1.getText()),
                        Integer.parseInt(extra2.getText()));}break;
                case 2:{i=ItemFactory.newItem(
                        name.getText(),
                        Integer.parseInt(amount.getText()),
                        Float.parseFloat(weight.getText()),Float.parseFloat(extra1.getText()),HardDriveType.valueOf(extra2.getText()));}break;
            }
            Model.getInst().getShelf(shelf).newItem(row,col,i);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Enter all values");
            alert.setHeaderText("Warning");
            alert.setContentText("please enter all values");
            alert.showAndWait();
        }
    }
    @FXML
    public void cancel(){
        ((Stage)extra1.getScene().getWindow()).close();
    }

    private String checkExtra1(String newValue){
        switch (currentType){
            case 0:{
                return newValue;
            }
            case 1:{
                if (!newValue.matches("\\d*")) {
                    return newValue.replaceAll("[^\\d]", "");
                }
            }break;
            case 2:{
                if (!newValue.matches("[+-]?([0-9]*[.])?[0-9]+")) {
                    return newValue.replaceAll("[^[+\\-\\d.]]", "");
                }
            }break;
        }
        return newValue;
    }
    private String checkExtra2(String newValue,String oldValue){
        switch (currentType){
            case 1:{
                if (!newValue.matches("\\d*")) {
                    return newValue.replaceAll("[^\\d]", "");
                }
            }break;
            case 2:{
                if ("ssd".contains(newValue)||"hdd".contains(newValue)) {
                    return newValue;
                }else{
                    return oldValue;
                }
            }
        }
        return newValue;
    }

}
