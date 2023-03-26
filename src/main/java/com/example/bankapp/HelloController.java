package com.example.bankapp;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button btnhpLogin;
    @FXML
    private PasswordField txtLpassword;

    @FXML
    private TextField txtLusername;
    @FXML
    private Button btnSignup;

    @FXML
    private TextField txtsuname;

    @FXML
    private TextField txtsupassword;


    @FXML
    private Button btnLogin;
    @FXML
    private Text lblwelcome;


    @FXML
    private Button btncontinue;

    @FXML
    private Label lblSignUp;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    public HelloController() throws SQLException {
    }

    @FXML
    void onContinue(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 682);
        Stage stage = (Stage) btnhpLogin.getScene().getWindow();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onSignUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 682);
        Stage stage = (Stage) btnhpLogin.getScene().getWindow();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void onSignUPlg(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 682);
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onTypePassword(ActionEvent event) {

    }

    @FXML
    void onTypeUsername(ActionEvent event) {

    }

    @FXML
    void onSignupr(ActionEvent event) throws IOException {
//        txtsuname.getText();
//        txtsupassword.getText();

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement checksifUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "admin", "Elorm17$.");
            checksifUserExist = connection.prepareStatement("SELECT * FROM Login WHERE Username = ?");
            checksifUserExist.setString(1, txtsuname.getText().toString());
            resultSet = checksifUserExist.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Username already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exist");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO Login (Username, Password) VALUES (?, ?)");
                psInsert.setString(1, txtsuname.getText().toString());
                psInsert.setString(2, txtsupassword.getText().toString());
                psInsert.executeUpdate();

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1100, 682);
                Stage stage = (Stage) btnSignup.getScene().getWindow();
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (checksifUserExist != null) {
                try {
                    checksifUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void onLogin(ActionEvent event) throws IOException {
        String txtusername = txtLusername.getText().toString();
        String txtpassword = txtLpassword.getText().toString();
        Connection connection = null;
        PreparedStatement pscheckifuserexist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "admin", "Elorm17$.");
            pscheckifuserexist = connection.prepareStatement("SELECT Password FROM Login WHERE Username = ?");
            pscheckifuserexist.setString(1, txtusername);
            resultSet = pscheckifuserexist.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("username not in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("wrong username");
                alert.show();
            } else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("Password");
                    if (retrievedPassword.equals(txtpassword)){
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();

                        stage = (Stage) btnLogin.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 1100, 682);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("wrong username or password");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
