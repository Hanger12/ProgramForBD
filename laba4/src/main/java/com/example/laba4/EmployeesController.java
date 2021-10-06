package com.example.laba4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EmployeesController {
    int idC;
    String name;
    String lname;
    String mname;
    Date date;
    String P;
    String named;
    int discharge;
    int salary;
    String namep;
    String country;

    public void setIdC(int IdC) {
        idC = IdC;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setLname(String Lname) {
        lname = Lname;
    }

    public void setMname(String Mname) {
        mname = Mname;
    }

    public void setDate(Date Date) {
        date = Date;
    }

    public void setP(String p)
    {
        P = p;
    }

    public void setNamed(String Named) {
        named = Named;
    }

    public void setDischarge(int Discharge) {
        discharge = Discharge;
    }

    public void setSalary(int Salary) {
        salary = Salary;
    }

    public void setNamep(String Namep) {
        namep = Namep;
    }

    public void setCountry(String Country) {
        country = Country;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonCanel;

    @FXML
    private ImageView ImagePerson;

    @FXML
    private Label Lbirthday;

    @FXML
    private Label Lcountry;

    @FXML
    private Label Ldischarge;

    @FXML
    private Label Lid;

    @FXML
    private Label Llname;

    @FXML
    private Label Lmname;

    @FXML
    private Label Lname;

    @FXML
    private Label Lnamed;

    @FXML
    private Label Lnamep;

    @FXML
    private Label Lp;

    @FXML
    private Label Lzarplata;

    @FXML
    void ButtonEnterClick(ActionEvent event) throws MalformedURLException {

        if(P.equals("Ж"))
        {
            File file = new File("src/main/resources/com/example/laba4/W.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            ImagePerson.setImage(image);

        }
        else if(P.equals("М"))
        {
            File file = new File("src/main/resources/com/example/laba4/M.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            ImagePerson.setImage(image);
        }
        else if(P.equals("Т"))
        {
            File file = new File("src/main/resources/com/example/laba4/ONO.jpg");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            ImagePerson.setImage(image);
            System.out.println(P);
        }
        Lid.setText("IDСотрудника: "+idC);
        Lname.setText("Имя: "+name);
        Llname.setText("Фамилия: "+lname);
        Lmname.setText("Отчество: "+mname);
        Lbirthday.setText("Дата Рождения: "+date);
        Lp.setText("Пол: "+P);
        Lzarplata.setText("Зарплата: "+salary+" Руб");
        Ldischarge.setText("Разряд ЕТС: "+discharge);
        Lnamed.setText("Наименование Должности: "+named);
        Lnamep.setText("Название Подразделения: "+namep);
        Lcountry.setText("Город Нахождения Отдела: "+country);

    }

    @FXML
    void ButtonOKClick(ActionEvent event) {
        Stage stage = (Stage) ButtonCanel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize()
    {

    }

}
