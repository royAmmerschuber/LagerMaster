package controller.subControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
        if(!txtRows.getText().equals("") && !txtName.getText().equals("") && !txtColumns.getText().equals("")){

            Shelf s=new Shelf(
                    Integer.parseInt(txtRows.getText()),
                    Integer.parseInt(txtColumns.getText()),
                    txtName.getText(),
                    -1
            );
            Model.getInst().newShelf(s);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("not filled out correctly");
            alert.setHeaderText("please fill everything out");
            alert.setContentText("okay?");

            alert.showAndWait();
        }

    }
    @FXML
    public void cancel(){
        ((Stage)txtColumns.getScene().getWindow()).close();
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
