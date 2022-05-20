package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
public class LoginController {
    private Parent parent;
    Methods methods = new Methods();
    ObservableList<String> roles = FXCollections.observableArrayList("admin", "manager", "waiter", "guest");
    @FXML
    private void initialize() {
        accountTypeField.setValue("admin");
        accountTypeField.setItems(roles);
    }
    @FXML
    private void initialize1() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from restaurant");
        ArrayList<String> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            restaurants.add(resultSet.getString("name"));
        }
        ObservableList<String> restaurantsList = FXCollections.observableArrayList(restaurants);
        restaurantField.setItems(restaurantsList);
    }
    @FXML
    private void initialize2() {
        if (accountTypeField.getValue().equals("manager") || accountTypeField.getValue().equals("waiter")) {
            restaurantField.setVisible(true);
            restaurantText.setVisible(true);
        } else {
            restaurantField.setVisible(false);
            restaurantField.setValue("");
        }
    }
    @FXML
    private void switchToRegistration(ActionEvent event) throws IOException {
        methods.switchScene(event,parent, "registration.fxml");
    }
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        ArrayList<String> emails = new ArrayList<>();
        while (resultSet.next()) {
            emails.add(resultSet.getString("email"));
        }
        if (emails.contains(emailField.getText())) {
            alreadyExists.setText("This email already exists");
            alreadyExists.setVisible(true);
        } else if (!emailField.getText().contains("@")){
            alreadyExists.setText("Email must contain @    .com");
            alreadyExists.setVisible(true);
        } else if (passwordField.getText().length()<8){
            alreadyExists.setText("Password must be longer than - 7");
            alreadyExists.setVisible(true);
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (`iduser`,`username`,`email`,`password`,`type`,`restaurant`) VALUES(0," +
                            "  '" + usernameField.getText() +
                            "','" + emailField.getText() +
                            "','" + passwordField.getText() +
                            "','" + accountTypeField.getValue() +
                            "','" + restaurantField.getValue() +
                            "' )");
            preparedStatement.executeUpdate();

            methods.switchScene(event, parent, "login.fxml");
        }
    }
    @FXML
    private void switchToMenu(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        while (resultSet.next()) {
            if (usernameField.getText().equals(resultSet.getString("username"))
                    & passwordField.getText().equals(resultSet.getString("password"))
                    & emailField.getText().equals(resultSet.getString("email"))
                    & accountTypeField.getValue().equals(resultSet.getString("type"))) {
                PreparedStatement preparedStatement;

                preparedStatement = connection.prepareStatement(
                            "UPDATE `jdbs-toikana`.`current_user` SET " +
                                    "`name` = '" + usernameField.getText() + "'," +
                                    "`role` = '" + accountTypeField.getValue() + "'," +
                                    "`restaurantPick` = '" + resultSet.getString("restaurant") + "'" +
                                    "WHERE (`id` = '1');");
                preparedStatement.executeUpdate();

                LocalDate date = LocalDate.now();
                preparedStatement = connection.prepareStatement(
                        "UPDATE `jdbs-toikana`.order SET " +
                                "`status` = '" + "Fisnished" + "' " +
                                "WHERE `date` < '"+date+"';");
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(
                        "UPDATE `jdbs-toikana`.event SET " +
                                "`status` = '" + "Fisnished" + "' " +
                                "WHERE `date` < '"+date+"';");
                preparedStatement.executeUpdate();


                usernameField.getText();
                emailField.getText();
                accountTypeField.getValue();
                resultSet.getString("restaurant");

                methods.switchScene(event, parent, resultSet.getString("type") + "-menu.fxml");
            }
        }
        notFoundText.setVisible(true);
    }

    @FXML
    private ChoiceBox<String> accountTypeField;
    @FXML
    private ChoiceBox<String> restaurantField;
    @FXML
    private Label alreadyExists;
    @FXML
    private TextField emailField;
    @FXML
    private Label restaurantText;
    @FXML
    private Label notFoundText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    }