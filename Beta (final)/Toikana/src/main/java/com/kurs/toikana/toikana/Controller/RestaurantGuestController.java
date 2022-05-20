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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;

public class RestaurantGuestController {
    String name;
    String restaurantPick;
    String role;
    private Parent parent;
    Methods methods = new Methods();
    ObservableList<String> types= FXCollections.observableArrayList( "Wedding", "Kuran", "Birthday", "Toi", "Corporate", "Individual");
    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        displayInfo();
        initializeFoodMenu();

        eventType.setValue("Wedding");
        eventType.setItems(types);
    }
    private void initializeAccount() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.current_user;");
        while (resultSet.next()) {
            name = resultSet.getString("name");
            restaurantPick = resultSet.getString("restaurantPick");
            role = resultSet.getString("role");
        }
    }

    @FXML
    private void initializeFoodMenu() throws SQLException, FileNotFoundException {
        methods.foodMenu(restaurantPick, foodMenu);
    }

    @FXML
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
                halls.setText("Address: "+resultSet.getString("halls"));
                description.setText("Description:\n "+resultSet.getString("description"));
                restaurantName.setText(restaurantPick+":");
                foodMenuName.setText(resultSet.getString("menuName"));
            }
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "guest-menu.fxml");
    }

    @FXML
    public void order() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO `jdbs-toikana`.`order` (`id`,`client`,`restaurant`,`type`,`description`,`date`,`status`,`pay`) VALUES(" +
                        "  '" + "0" +
                        "','" + name +
                        "','" + restaurantPick +
                        "','" + eventType.getValue() +
                        "','" + orderInfoField.getText() +
                        "','" + datePicker.getValue() +
                        "','" + "Waiting" +
                        "','" + "0" +
                        "');" );
        preparedStatement.executeUpdate();

        orderDoneLabel.setVisible(true);
        orderButton.setDisable(true);
    }

    @FXML
    private Label address;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea description;
    @FXML
    private ChoiceBox<String> eventType;
    @FXML
    private TreeView<String> foodMenu;
    @FXML
    private Label foodMenuName;
    @FXML
    private Label halls;
    @FXML
    private Button orderButton;
    @FXML
    private Label orderDoneLabel;
    @FXML
    private TextArea orderInfoField;
    @FXML
    private ImageView restaurantImage;
    @FXML
    private Label restaurantName;
}
