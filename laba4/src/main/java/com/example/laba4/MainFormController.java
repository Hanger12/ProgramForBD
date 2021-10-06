package com.example.laba4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFormController {
    int ID;
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

    ObservableList<Person> List = FXCollections.observableArrayList();
    Connection Conn;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Person> TableWiew;

    @FXML
    private TableColumn<Person, String> COUNTRY;

    @FXML
    private TableColumn<Person, Integer> DISCHARGE;

    @FXML
    private TableColumn<Person, Integer> IDCOTR;

    @FXML
    private TableColumn<Person, Integer> IDPODR;

    @FXML
    private TableColumn<Person, String> NAMECOTR;

    @FXML
    private TableColumn<Person, String> NAMEMENEGR;

    @FXML
    private TableColumn<Person, String> NAMEPODR;
    @FXML
    private TableColumn<Person, Integer> ZARPLATA;
    @FXML
    void ClearTable(ActionEvent event) {
        TableWiew.getItems().clear();
    }
    @FXML
    void AddPerson(ActionEvent event) throws IOException {
        FXMLLoader XLoader = new FXMLLoader(HelloController.class.getResource("AddPerson.fxml"));
        Parent root1 = XLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("AddEmployees");
        stage.setScene(new Scene(root1));
        AddPersonController AddPerson= XLoader.getController();
        AddPerson.Connect(Conn);
        stage.show();

    }
    @FXML
    private TextField SearchID;

    @FXML
    void DeletePerson(ActionEvent event) throws SQLException {
        if(SearchID.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Для удаления сотрудника введите ID в поле поиска\n");
            alert.showAndWait();
        }
        else if(checkMe(SearchID.getText()))
        {
            ID = Integer.parseInt(SearchID.getText());
            ResultSet rs;
            int maxId=0,minId =0;
            String MaxID = "SELECT max(idСлужащие) as MaxID FROM служащие";
            String MinID ="SELECT min(idСлужащие) as MinID FROM служащие";
            PreparedStatement Max = Conn.prepareStatement(MaxID);
            PreparedStatement Min = Conn.prepareStatement(MinID);
            rs = Max.executeQuery();
            while (rs.next())
            {
                maxId = rs.getInt("MaxID");
            }
            rs = Min.executeQuery();
            while (rs.next())
            {
                minId = rs.getInt("MinID");
            }
            if(ID>maxId ||ID<minId)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Сотрудник не найден");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("DeletePerson");
                alert.setHeaderText("Вы точно хотите удалить Строку");
                alert.setContentText("IdСотрудника = "+ID);
                ButtonType Yes = new ButtonType("ДА");
                ButtonType No = new ButtonType("НЕТ");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(Yes,No);
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get()==Yes)
                {
                    try {
                        String Delete ="DELETE from служащие WHERE idСлужащие = "+ID;
                        PreparedStatement sq = Conn.prepareStatement(Delete);
                        sq.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Сотрудник Удален");
                        alert.showAndWait();
                    }
                    catch (SQLException e)
                    {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Не Удалось Удалить");
                        alert.showAndWait();
                    }

                }
            }

        }
    }

    @FXML
    void SearchButton(ActionEvent event) throws SQLException, IOException {
        if(SearchID.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Вы не ввели ID");
            alert.showAndWait();
        }
        else if(checkMe(SearchID.getText()))
        {
            ID = Integer.parseInt(SearchID.getText());
            ResultSet rs;
            int maxId=0,minId =0;
            String MaxID = "SELECT max(idСлужащие) as MaxID FROM служащие";
            String MinID ="SELECT min(idСлужащие) as MinID FROM служащие";
            PreparedStatement Max = Conn.prepareStatement(MaxID);
            PreparedStatement Min = Conn.prepareStatement(MinID);
            rs = Max.executeQuery();
            while (rs.next())
            {
                maxId = rs.getInt("MaxID");
            }
            rs = Min.executeQuery();
            while (rs.next())
            {
                minId = rs.getInt("MinID");
            }
            if(ID>maxId ||ID<minId)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Сотрудник не найден");
                alert.showAndWait();
            }
            else
            {
                String Select ="SELECT с.idСлужащие, с.Имя,с.Фамилия,с.Отчество,\n" +
                        "       с.`Дата Рождения`,с.Пол,д.Наименование,д.IDРазряд_ЕТС,\n" +
                        "       ре.`Базовый оклад`,п.Название,\n" +
                        "       п.`Город нахождения отдела`\n" +
                        "from служащие с\n" +
                        "         join должности д on д.idДолжности = с.IDДолжности\n" +
                        "         join подразделения п on п.idПодразделения = с.IDПодразделения\n" +
                        "         join разряд_етс ре on ре.idРазрядЕТС = д.IDРазряд_ЕТС\n"+
                        "WHERE idСлужащие = "+ID;
                PreparedStatement sq = Conn.prepareStatement(Select);
                ResultSet sql = sq.executeQuery();
                while (sql.next())
                {
                    idC = sql.getInt("idСлужащие");
                    name =sql.getString("Имя");
                    lname = sql.getString("Фамилия");
                    mname =sql.getString("Отчество");
                    date = sql.getDate("Дата Рождения");
                    P = sql.getString("Пол");
                    named =sql.getString("Наименование");
                    discharge = sql.getInt("IDРазряд_ЕТС");
                    salary =sql.getInt("Базовый оклад");
                    namep =sql.getString("Название");
                    country =sql.getString("Город нахождения отдела");

                }
                FXMLLoader Loader = new FXMLLoader(HelloController.class.getResource("employees.fxml"));
                Parent root1 = Loader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Employees");
                stage.setScene(new Scene(root1));
                EmployeesController Employees = Loader.getController(); //получаем контроллер для второй формы
                Employees.setIdC(idC);
                Employees.setName(name);
                Employees.setLname(lname);
                Employees.setMname(mname);
                Employees.setDate(date);
                Employees.setP(P);
                Employees.setNamed(named);
                Employees.setDischarge(discharge);
                Employees.setSalary(salary);
                Employees.setNamep(namep);
                Employees.setCountry(country);
                stage.show();
            }
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
    public void Connect(Connection connection)
    {
        Conn = connection;
    }
    @FXML
    void initialize()
    {

    }
    @FXML
    void ButtonClickAllPerson(ActionEvent event) throws SQLException
    {
        String sqlSelectAllPersons = "SELECT с.idСлужащие, с.Имя,п.`Менеджер отдела`,\n" +
                "       ре.`Базовый оклад`,п.idПодразделения,п.Название,\n" +
                "       п.`Город нахождения отдела`,д.IDРазряд_ЕТС\n" +
                "from служащие с\n" +
                "         join должности д on д.idДолжности = с.IDДолжности\n" +
                "         join подразделения п on п.idПодразделения = с.IDПодразделения\n" +
                "         join разряд_етс ре on ре.idРазрядЕТС = д.IDРазряд_ЕТС\n";
        PreparedStatement ps = Conn.prepareStatement(sqlSelectAllPersons);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int idC = rs.getInt("idСлужащие");
            String nameC = rs.getString("Имя");
            String nameM = rs.getString("Менеджер отдела");
            int zarplata = rs.getInt("Базовый оклад");
            int idP = rs.getInt("idПодразделения");
            String nameP = rs.getString("Название");
            String country = rs.getString("Город нахождения отдела");
            int discharge = rs.getInt("IDРазряд_ЕТС");
            List.add(new Person(idC,nameC,nameM,zarplata,idP,nameP,country,discharge));
        }
        IDCOTR.setCellValueFactory(new PropertyValueFactory<Person, Integer>("idC"));
        NAMECOTR.setCellValueFactory(new PropertyValueFactory<Person, String>("nameC"));
        NAMEMENEGR.setCellValueFactory(new PropertyValueFactory<Person, String>("nameM"));
        ZARPLATA.setCellValueFactory(new PropertyValueFactory<Person, Integer>("zarplata"));
        IDPODR.setCellValueFactory(new PropertyValueFactory<Person, Integer>("idP"));
        NAMEPODR.setCellValueFactory(new PropertyValueFactory<Person, String>("nameP"));
        COUNTRY.setCellValueFactory(new PropertyValueFactory<Person, String>("country"));
        DISCHARGE.setCellValueFactory(new PropertyValueFactory<Person, Integer>("discharge"));
        TableWiew.setItems(List);
    }

}

