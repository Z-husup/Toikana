package com.kurs.toikana.toikana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;

public class WaiterMenuController {
    String name;
    String restaurantPick;
    String role;

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
        ResultSet resultSet = statement.executeQuery("select * from event");
        ArrayList<String> events = new ArrayList<>();
        while (resultSet.next()) {
            events.add(resultSet.getString("name"));
        }
    }

    private void initializeFoodMenu() throws SQLException, FileNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from food");

        while (resultSet.next()) {
            if(restaurantPick.equals(resultSet.getString("restaurant"))){
                TreeItem<String> rootItem = new TreeItem<>("Food Menu");
                TreeItem<String> branchItem1 = new TreeItem<>("1st Meal", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\1stMeal.png"), 20, 20, false, false)));
                TreeItem<String> branchItem2 = new TreeItem<>("2nd Meal", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\2ndMeal.png"), 20, 20, false, false)));
                TreeItem<String> branchItem3 = new TreeItem<>("Drinks", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\Drinks.png"), 20, 20, false, false)));
                TreeItem<String> branchItem4 = new TreeItem<>("Desserts", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\Desserts.png"), 20, 20, false, false)));

                ArrayList<TreeItem<String>> food1 = new ArrayList<>();
                ArrayList<TreeItem<String>> food2 = new ArrayList<>();
                ArrayList<TreeItem<String>> food3 = new ArrayList<>();
                ArrayList<TreeItem<String>> food4 = new ArrayList<>();
                String menuName = restaurantPick;

                resultSet = statement.executeQuery("select * from food");
                while (resultSet.next()) {
                    if (resultSet.getString("type").equals("1st Meal") & resultSet.getString("restaurant").equals(menuName)) {
                        food1.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
                    }
                    if (resultSet.getString("type").equals("2nd Meal") & resultSet.getString("restaurant").equals(menuName)) {
                        food2.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
                    }
                    if (resultSet.getString("type").equals("Drink") & resultSet.getString("restaurant").equals(menuName)) {
                        food3.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
                    }
                    if (resultSet.getString("type").equals("Dessert") & resultSet.getString("restaurant").equals(menuName)) {
                        food4.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
                    }
                }
                branchItem1.getChildren().addAll(food1);
                branchItem2.getChildren().addAll(food2);
                branchItem3.getChildren().addAll(food3);
                branchItem4.getChildren().addAll(food4);

                rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4);
                foodMenu.setShowRoot(false);
                foodMenu.setRoot(rootItem);
            }
        }
        nameLabel.setText(name);
        roleLabel.setText(role);
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
                halls.setText("Address: "+resultSet.getString("halls"));
                description.setText("Description:\n "+resultSet.getString("description"));
                restaurantName.setText(restaurantPick+":");
            }
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
    private Label address;

    @FXML
    private Label description;

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
