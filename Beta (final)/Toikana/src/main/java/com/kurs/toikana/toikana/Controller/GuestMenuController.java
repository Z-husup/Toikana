package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import com.kurs.toikana.toikana.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class GuestMenuController{
    String name;
    String restaurantPick;
    String role;

    private Parent parent;

    Methods methods = new Methods();

    ObservableList<Order> orders = FXCollections.observableArrayList(new Order(0, "","","",""));

    @FXML
    private void initialize() throws SQLException {
        initializeAccount();
        initializeRestaurants();
        initializeOrders();
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

    private void initializeOrders() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.order;");
        ArrayList<Order> ordersList = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("client").equals(name)) {
                ordersList.add(new Order(resultSet.getInt("id"),resultSet.getString("type"), resultSet.getString("date"), resultSet.getString("status"), resultSet.getString("pay")));
            }
        }
        Collections.reverse(ordersList);
        orders = FXCollections.observableArrayList(ordersList);
        nameTable.setCellValueFactory(new PropertyValueFactory<Order,String>("name"));
        dateTable.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
        payTable.setCellValueFactory(new PropertyValueFactory<Order,Integer>("pay"));
        statusTable.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
        myOrdersTable.setItems(orders);
    }
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "login.fxml");
    }
    @FXML
    private void initializeRestaurants() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from restaurant");
        ArrayList<String> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("status").equals("Active")) {
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
            try {
                if (restaurantPick!=null) {
                    methods.switchScene(event, parent, "restaurant-pick.fxml");
                }
                } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });
    }

    @FXML
    private Label nameLabel;
    @FXML
    private ListView<String> restaurantsList;
    @FXML
    private Label roleLabel;
    @FXML
    private TableColumn<Order, String> dateTable;
    @FXML
    private TableView<Order> myOrdersTable;
    @FXML
    private TableColumn<Order, String> nameTable;
    @FXML
    private TableColumn<Order, Integer> payTable;
    @FXML
    private TableColumn<Order, String> statusTable;

}
