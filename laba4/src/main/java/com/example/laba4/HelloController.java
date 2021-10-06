package com.example.laba4;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class HelloController {
    Connection conn;
    String Login,Password,URL;
    @FXML
    private Button ButtonEnter;
    @FXML
    private Button ButtonClose;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginDB;

    @FXML
    private PasswordField PasswordDB;

    @FXML
    private TextField URLDB;
    @FXML
    void ButtonClick2(ActionEvent event)
    {
        Stage stage = (Stage) ButtonClose.getScene().getWindow();
        stage.close();
    }


    @FXML
    void ButtonClick(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        Login = LoginDB.getText();
        Password = PasswordDB.getText();
        URL = URLDB.getText();
        if(!Login.isEmpty()||!Password.isEmpty()||!URL.isEmpty()){
            try  {
                conn = DriverManager.getConnection(URL, Login, Password);
                if(!conn.isClosed())
                {
                    alert.setHeaderText(null);
                    alert.setContentText("Подключение к БД состоялось");
                    alert.showAndWait();
                    if(!alert.isShowing())
                    {
                        Stage stage = (Stage) ButtonEnter.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("MainForm.fxml"));
                        Parent root1 = fxmlLoader.load();
                        stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("MainForm");
                        stage.setScene(new Scene(root1));
                        MainFormController MainForm = fxmlLoader.getController(); //получаем контроллер для второй формы
                        MainForm.Connect(conn); // передаем необходимые параметры
                        stage.show();
                    }
                }
            } catch (SQLException | IOException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Произошла ошибка подключение к бд не состоялось");
                alert.showAndWait();
            }

        }
        }

    @FXML
    void initialize()
    {

    }
}
