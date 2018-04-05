package controller.subControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Model;
import model.Shelf;

import java.net.URL;
import java.util.ResourceBundle;

public class NewShelfContr implements Initializable{
    @FXML public TextField txtName;
    @FXML public TextField txtRows;
    @FXML public TextField txtColumns;

    @FXML
    public void ok(){
        Shelf s=new Shelf(
                Integer.parseInt(txtRows.getText()),
                Integer.parseInt(txtColumns.getText()),
                txtName.getText(),
                -1
        );
        Model.getInst().newShelf(s);
    }
    @FXML
    public void cancel(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // force the field to be numeric only
        txtRows.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRows.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtColumns.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtColumns.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
