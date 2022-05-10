package com.kurs.toikana.toikana;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AdminMenuController {

    public AdminMenuController(){

    }
    String name;
    String restaurantPick;
    String role;

    @FXML
    private void initialize() throws SQLException {
        initializeAccount();
        initializeUsers();
        initializeRestaurants();

    }

    private Parent parent;
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void createNewRestaurant(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("new-restaurant.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void editRestaurant(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`current_user` SET " +
                        "`restaurantPick` = '"+ restaurantLabel.getText() + "'" +
                        "WHERE (`id` = '1');");
        preparedStatement.executeUpdate();


        Parent parent = FXMLLoader.load(getClass().getResource("edit-restaurant.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        restaurantLabel.getText();
    }

    private void initializeAccount() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.current_user;");
        while (resultSet.next()) {
            name = resultSet.getString("name");
            restaurantPick =  resultSet.getString("restaurantPick");
            role = resultSet.getString("role");
        }
        nameLabel.setText(name);
        roleLabel.setText(role);
    }

    @FXML
    private void initializeUsers() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        ArrayList<String> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(resultSet.getString("email"));
        }
        usersList.setItems(FXCollections.observableArrayList(users));
        userLabel.setText(usersList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void deleteUser(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039" );
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`user` WHERE (`email` = '"+userLabel.getText()+"');");
        preparedStatement.executeUpdate();
        initializeUsers();
    }

    @FXML
    private void initializeRestaurants() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from restaurant");
        ArrayList<String> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            restaurants.add(resultSet.getString("name"));
        }
        restaurantsList.setItems(FXCollections.observableArrayList(restaurants));
        restaurantLabel.setText(restaurantsList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void chooseRestaurants() throws SQLException {

        restaurantPick =restaurantsList.getSelectionModel().getSelectedItem();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`current_user` SET " +
                        "`restaurantPick` = '" + restaurantPick + "'" +
                        "WHERE (`id` = '1');");
        restaurantLabel.setText(restaurantPick);
        restaurantsList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){

                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("manager-menu.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
        });
    }

    @FXML
    private void deleteRestaurant(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039" );
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`restaurant` WHERE (`name` = '"+restaurantLabel.getText()+"');");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`food` WHERE (`restaurant` = '"+restaurantLabel.getText()+"');");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`event` WHERE (`restaurant` = '"+restaurantLabel.getText()+"');");
        preparedStatement.executeUpdate();
        initializeRestaurants();
    }


    @FXML
    private Button deleteRestaurantButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button editRestaurantButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Button newRestaurantButton;

    @FXML
    private Label restaurantLabel;

    @FXML
    private ListView<String> restaurantsList;

    @FXML
    private Label roleLabel;

    @FXML
    private Label userLabel;

    @FXML
    private ListView<String> usersList;



}
