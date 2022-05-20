package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class NewRestaurantController {

    String selectedFood;
    String selectedPrice;
    private Parent parent;
    Methods methods = new Methods();
    ObservableList<String> types= FXCollections.observableArrayList( "1st Meal", "2nd Meal", "Drink", "Dessert");

    public void initialize() throws FileNotFoundException, SQLException {
        initializeFoodMenu();
        foodTypeField.setValue("1st Meal");
        foodTypeField.setItems(types);
    }


    public void initializeFoodMenu() throws FileNotFoundException, SQLException {
        methods.foodMenu(restaurantNameField.getText(), foodMenu);
    }

    @FXML
    private void setFoodMenuName(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from food");
        ArrayList<String> restaurants = new ArrayList<>();

        while (resultSet.next()) {
            restaurants.add(resultSet.getString("restaurant"));
        }
        if (restaurants.contains(restaurantNameField.getText())) {
            restaurantNameField.setText("This restaurant already exists");
        }
        else {
            restaurantNameField.setDisable(true);
            foodMenuName.setDisable(true);
            addFoodButton.setDisable(false);
        }
    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "admin-menu.fxml");
    }

    @FXML
    private void menuNameAction(KeyEvent key) {
            foodMenuRestaurant.setText(restaurantNameField.getText());
    }

    @FXML
    private void createNewRestaurant(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO `jdbs-toikana`.restaurant (`idrestaurant`,`name`,`adress`,`halls`,`manager`,`menuName`,`image`,`description`,`status`) VALUES(0," +
                        "  '" + restaurantNameField.getText() +
                        "','" + addressField.getText() +
                        "','" + hallsNumberField.getText() +
                        "','" + "" +
                        "','" + foodMenuRestaurant.getText() + ": " + foodMenuName.getText() +
                        "','" + imageWayField.getText() +
                        "','" + descriptionField.getText() +
                        "','" + "Active" +
                        "' );");
        preparedStatement.executeUpdate();
        String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for (int i = 0; i<Integer.parseInt(hallsNumberField.getText()); i ++){
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO `jdbs-toikana`.hall (`id`,`name`,`restaurant`,`tablesNumber`,`takenTables`,`arrangementForm`) VALUES(0," +
                            "  '" + alphabet[i] +
                            "','" + restaurantNameField.getText() +
                            "','" + "0" +
                            "','" + "0" +
                            "','" + "" +
                            "' );");
            preparedStatement.executeUpdate();
        }

        methods.switchScene(event, parent, "admin-menu.fxml");
    }

    @FXML
    private void chooseImage(ActionEvent event) throws IOException, RuntimeException {
        methods.chooseImage(imageWayField);
    }

    @FXML
    private void addMeal(ActionEvent event) throws SQLException, FileNotFoundException {
        methods.addFood(restaurantNameField.getText() + ": " + foodMenuName.getText(),  foodNameField,  foodPriceField, foodTypeField, restaurantNameField.getText());
        initializeFoodMenu();
    }

    public void selectItem() {
        TreeItem<String> item = foodMenu.getSelectionModel().getSelectedItem();
        if(item != null) {
            String[] selectedFoodArray = item.getValue().split("\\|");
            selectedFood = selectedFoodArray[0].trim();
        }
    }

    @FXML
    private void deleteFood(ActionEvent event) throws SQLException, FileNotFoundException {
        methods.deleteFood(selectedFood, restaurantNameField.getText());
        initializeFoodMenu();
    }

    @FXML
    private Button addFoodButton;
    @FXML
    private TextField addressField;
    @FXML
    private Button createRestaurantButton;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField foodMenuName;
    @FXML
    private TextField foodNameField;
    @FXML
    private TextField foodPriceField;
    @FXML
    private TextField imageWayField;
    @FXML
    private Label foodMenuRestaurant;
    @FXML
    private ChoiceBox<String> foodTypeField;
    @FXML
    private TextField hallsNumberField;
    @FXML
    private TreeView<String> foodMenu;
    @FXML
    private TextField restaurantNameField;

}

