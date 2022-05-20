package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import com.kurs.toikana.toikana.objects.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class WaiterMenuController {
    String name;
    String restaurantPick;
    String role;
    private Parent parent;
    Methods methods = new Methods();
    ObservableList<Event> events = FXCollections.observableArrayList(new Event("", "","",""));

    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        displayInfo();
        initializeEvents();
        initializeFoodMenu();
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
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.event;");
        ArrayList<Event> eventsList = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantPick)) {
                eventsList.add(new Event(resultSet.getString("type"),resultSet.getString("date"), resultSet.getString("status"),
                        resultSet.getString("price")));
            }
        }
        Collections.reverse(eventsList);
        events = FXCollections.observableArrayList(eventsList);
        eventsName.setCellValueFactory(new PropertyValueFactory<Event,String>("name"));
        eventsDate.setCellValueFactory(new PropertyValueFactory<Event,String>("date"));
        eventsStatus.setCellValueFactory(new PropertyValueFactory<Event,String>("status"));
        eventsTable.setItems(events);
    }

    public void initializeFoodMenu() throws SQLException, FileNotFoundException {
        methods.foodMenu(restaurantPick, foodMenu);
    }

    public void displayInfo() throws SQLException, MalformedURLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from restaurant");
        while (resultSet.next()) {
            if (restaurantPick.equals(resultSet.getString("name"))){
                File file = new File(resultSet.getString("image"));
                Image image = new Image(file.toURI().toURL().toString());
                restaurantImage.setImage(image);
                address.setText("Address: "+resultSet.getString("adress"));
                halls.setText("Halls: "+resultSet.getString("halls"));
                description.setText("Description:\n "+resultSet.getString("description"));
                restaurantName.setText(restaurantPick+":");
                foodMenuName.setText(resultSet.getString("menuName"));
            }
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException, SQLException {
        methods.switchScene(event, parent,"login.fxml");
    }


    @FXML
    private TableColumn<Event, String> eventsDate;
    @FXML
    private TableColumn<Event, String> eventsName;
    @FXML
    private TableColumn<Event, String> eventsStatus;
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private Label address;
    @FXML
    private TextArea description;
    @FXML
    private TreeView<String> foodMenu;
    @FXML
    private Label foodMenuName;
    @FXML
    private Label halls;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView restaurantImage;
    @FXML
    private Label restaurantName;
    @FXML
    private Label roleLabel;

}
