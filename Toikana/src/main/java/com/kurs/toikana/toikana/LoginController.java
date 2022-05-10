package com.kurs.toikana.toikana;

import com.opencsv.exceptions.CsvValidationException;
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

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
public class LoginController {
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

    private Parent parent;

    @FXML
    private void switchToRegistration(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

            parent = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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

                if (restaurantField==null) {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE `jdbs-toikana`.`current_user` SET " +
                                    "`name` = '" + usernameField.getText() + "'," +
                                    "`role` = '" + accountTypeField.getValue() + "'" +
                                    "WHERE (`id` = '1');");
                }
                else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE `jdbs-toikana`.`current_user` SET " +
                                    "`name` = '" + usernameField.getText() + "'," +
                                    "`role` = '" + accountTypeField.getValue() + "'," +
                                    "`restaurantPick` = '" + restaurantField.getValue() + "'" +
                                    "WHERE (`id` = '1');");
                }
                preparedStatement.executeUpdate();


                usernameField.getText();
                    emailField.getText();
                    accountTypeField.getValue();
                    resultSet.getString("restaurant");
                    Parent parent = FXMLLoader.load(getClass().getResource(resultSet.getString("type") + "-menu.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
            }
            notFoundText.setVisible(true);
        }
    }