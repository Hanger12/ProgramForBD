package com.example.laba4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

public class AddPersonController {
    ToggleGroup group = new ToggleGroup();
    Connection Conn;
    int id,idD,idP;
    String name,lname,mname,P;
    String date;

    @FXML
    private RadioButton radioButtonM;



    @FXML
    private RadioButton radioButtonW;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonCanel;

    @FXML
    private Button ButtonEnter;

    @FXML
    private ImageView ImagePerson;

    @FXML
    private Label Lbirthday;

    @FXML
    private Label Lid;

    @FXML
    private Label LidD;

    @FXML
    private Label LidP;

    @FXML
    private Label Llname;

    @FXML
    private Label Lmname;

    @FXML
    private Label Lname;

    @FXML
    private Label Lp;
    @FXML
    private TextField TextBirtday;

    @FXML
    private TextField TextID;

    @FXML
    private TextField TextIDD;

    @FXML
    private TextField TextLName;

    @FXML
    private TextField TextMName;

    @FXML
    private TextField TextName;

    @FXML
    private TextField TextPODR;

    @FXML
    void ButtonCloseClick(ActionEvent event)
    {
        Stage stage = (Stage) ButtonCanel.getScene().getWindow();
        stage.close();
    }
    public void Connect(Connection connection)
    {
        Conn = connection;
    }
    @FXML
    void ButtonEnterClick(ActionEvent event)  {
        try {
            int maxId = 0,maxIDD=0,maxIDP=0,minIDD=0,minIDP=0;
            ResultSet rs;
            String MaxID = "SELECT max(idСлужащие) as MaxID FROM служащие";
            String MaxIDD="SELECT max(idДолжности) as MaxID FROM должности";
            String MaxIDP="SELECT max(idПодразделения) as MaxID FROM подразделения";
            String MinIDD = "SELECT min(idДолжности) as MinID FROM должности";
            String MinIDP ="SELECT min(idПодразделения) as MinID FROM подразделения";
            PreparedStatement Max = Conn.prepareStatement(MaxID);
            PreparedStatement MaxIdD =Conn.prepareStatement(MaxIDD);
            PreparedStatement MinIdD =Conn.prepareStatement(MinIDD);
            PreparedStatement MaxIdP =Conn.prepareStatement(MaxIDP);
            PreparedStatement MinIdP =Conn.prepareStatement(MinIDP);
            rs = Max.executeQuery();
            while (rs.next()) {
                maxId = rs.getInt("MaxID");
            }
            rs = MaxIdD.executeQuery();
            while (rs.next()) {
                maxIDD = rs.getInt("MaxID");
            }
            rs = MaxIdP.executeQuery();
            while (rs.next()) {
                maxIDP = rs.getInt("MaxID");
            }
            rs = MinIdD.executeQuery();
            while (rs.next()) {
                minIDD = rs.getInt("MinID");
            }
            rs = MinIdP.executeQuery();
            while (rs.next()) {
                minIDP = rs.getInt("MinID");
            }
            if (TextID.getText().isEmpty()) {
                id = maxId + 1;
            } else if(checkMe(TextID.getText())){
                id = Integer.parseInt(TextID.getText());
            }
            RadioButton selection = (RadioButton) group.getSelectedToggle();
            if (TextName.getText().isEmpty() || TextLName.getText().isEmpty() ||
                    TextMName.getText().isEmpty() || !selection.isSelected() ||
                    TextBirtday.getText().isEmpty() || TextIDD.getText().isEmpty() || TextPODR.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Не все поля заполнены IDСотрудника заполнять необязательно");
                alert.showAndWait();
            }
            else if(!TextName.getText().isEmpty() && !TextLName.getText().isEmpty() &&
                    !TextMName.getText().isEmpty() && selection.isSelected() &&
                    !TextBirtday.getText().isEmpty() && !TextIDD.getText().isEmpty() && !TextPODR.getText().isEmpty())
            {
                if(Integer.parseInt(TextIDD.getText())>maxIDD||Integer.parseInt(TextIDD.getText())<minIDD
                        ||Integer.parseInt(TextPODR.getText())>maxIDP||Integer.parseInt(TextPODR.getText())<minIDP)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("IDДолжности должно быть от "+minIDD+" до "+maxIDD+"\n"
                    +"IDПодразделения должно быть от "+minIDP+" до "+maxIDP);
                    alert.showAndWait();
                }
                else
                {
                    name ="'"+TextName.getText()+"'";
                    lname ="'"+TextLName.getText()+"'";
                    mname ="'"+TextMName.getText()+"'";
                    P="'"+selection.getText()+"'";
                    date = "'"+TextBirtday.getText()+"'";
                    idD = Integer.parseInt(TextIDD.getText());
                    idP = Integer.parseInt(TextPODR.getText());
                    String SQLInsert = "INSERT INTO служащие(idСлужащие, Имя, Фамилия, Отчество, `Дата Рождения`,\n" +
                            "                     IDДолжности, IDПодразделения, Пол)\n" +
                            "                     value ("+id+","+name+","+lname+","+mname+","+date+","+idD+","+idP+","+P+")";
                    PreparedStatement sq = Conn.prepareStatement(SQLInsert);
                    sq.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Сотрудник успешно добавлен");
                    alert.showAndWait();
                }

            }
        }
        catch (SQLException e )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Не удалось добавить сотрудника");
            alert.showAndWait();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Вы не указали пол");
            alert.showAndWait();
        }

    }
    public static boolean checkMe(String s) {
        boolean amIValid = false;
        try {
            Integer.parseInt(s);
            amIValid = true;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("В ID должно быть число");
            alert.showAndWait();
        }
        return amIValid;
    }
    @FXML
    void radioButtonClickM(ActionEvent event) throws MalformedURLException {
        File file = new File("src/main/resources/com/example/laba4/M.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ImagePerson.setImage(image);
    }

    @FXML
    void radioButtonClickW(ActionEvent event) throws MalformedURLException {
        File file = new File("src/main/resources/com/example/laba4/W.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ImagePerson.setImage(image);
    }
    @FXML
    void initialize()
    {
        radioButtonM.setToggleGroup(group);
        radioButtonW.setToggleGroup(group);
    }

}
