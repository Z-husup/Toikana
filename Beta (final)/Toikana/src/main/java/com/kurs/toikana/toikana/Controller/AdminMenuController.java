package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import com.kurs.toikana.toikana.objects.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AdminMenuController {

    String name;
    String restaurantPick;
    String role;


    private Parent parent;

    ObservableList<User> users = FXCollections.observableArrayList(new User("", "","", ""));

    Methods methods = new Methods();

    @FXML
    private void initialize() throws SQLException {
        initializeAccount();
        initializeUsers();
        initializeRestaurants();

        restaurantsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                restaurantLabel.setText(restaurantsList.getSelectionModel().getSelectedItem());

                deleteRestaurantButton.setDisable(false);
                editRestaurantButton.setDisable(false);
            }
        });
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "login.fxml");
    }
    @FXML
    private void createNewRestaurant(ActionEvent event) throws IOException{
        methods.switchScene(event, parent, "new-restaurant.fxml");
    }

    @FXML
    private void editRestaurant(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`current_user` SET " +
                        "`restaurantPick` = '"+ restaurantLabel.getText() + "'" +
                        "WHERE (`id` = '1');");
        preparedStatement.executeUpdate();

        methods.switchScene(event, parent, "edit-restaurant.fxml");
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
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.user;");
        ArrayList<User> usersList = new ArrayList<>();
        while (resultSet.next()) {
                usersList.add(new User(resultSet.getString("username"),resultSet.getString("email"),resultSet.getString("type"), resultSet.getString("restaurant")));
        }
        users = FXCollections.observableArrayList(usersList);
        usersNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<User,String>("restaurant"));

        usersTable.setItems(users);
    }

    @FXML
    public void selectUser() {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null){
            userLabel.setText(user.getEmail());
        }
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
        deleteRestaurantButton.setDisable(true);
        editRestaurantButton.setDisable(true);
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
        preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`hall` WHERE (`restaurant` = '"+restaurantLabel.getText()+"');");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.user WHERE (`restaurant` = '"+restaurantLabel.getText()+"');");
        preparedStatement.executeUpdate();
        initializeRestaurants();
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
                try {
                    if (restaurantPick!=null) {
                        methods.switchScene(event, parent, "manager-menu.fxml");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> usersNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> restaurantColumn;

}
