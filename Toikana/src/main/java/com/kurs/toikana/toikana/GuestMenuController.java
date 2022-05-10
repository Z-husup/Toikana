package com.kurs.toikana.toikana;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GuestMenuController {

    public GuestMenuController() {

    }
    String name;
    String restaurantPick;
    String role;

    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        initializeRestaurants();
        initializeEvents();
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

    private void initializeEvents() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from event");
        ArrayList<String> events = new ArrayList<>();
        while (resultSet.next()) {
            events.add(resultSet.getString("name"));
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException, SQLException {
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initializeRestaurants() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from restaurant");
        ArrayList<String> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("status").equals("active")) {
                restaurants.add(resultSet.getString("name"));
            }
        }
        restaurantsList.setItems(FXCollections.observableArrayList(restaurants));
    }

    @FXML
    private void chooseRestaurants() throws SQLException {

        restaurantPick =restaurantsList.getSelectionModel().getSelectedItem();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`current_user` SET " +
                        "`restaurantPick` = '" + restaurantPick + "'" +
                        "WHERE (`id` = '1');");
        preparedStatement.executeUpdate();

        restaurantsList.setOnMouseClicked((MouseEvent event) -> {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){

            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("restaurant-pick.fxml"));
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
    private Label nameLabel;

    @FXML
    private ListView<String> restaurantsList;

    @FXML
    private Label roleLabel;








}
