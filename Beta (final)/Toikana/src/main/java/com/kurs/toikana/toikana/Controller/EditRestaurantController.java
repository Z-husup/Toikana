package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class EditRestaurantController {
    String restaurantPick;
    String oldMenuName;
    String selectedFood;
    private Parent parent;
    Methods methods = new Methods();
    ObservableList<String> types = FXCollections.observableArrayList("1st Meal", "2nd Meal", "Drink", "Dessert");

    public void initialize() throws FileNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.current_user;");
        while (resultSet.next()) {
            restaurantPick = resultSet.getString("restaurantPick");
        }
        resultSet = statement.executeQuery("select * from restaurant");
        while (resultSet.next()) {

            if (resultSet.getString("name").equals(restaurantPick)) {
                restaurantNameField.setText(resultSet.getString("name"));
                foodMenuName.setText(resultSet.getString("menuName"));
                descriptionField.setText(resultSet.getString("description"));
                managerField.setValue(resultSet.getString("manager"));
                imageWayField.setText(resultSet.getString("image"));
                hallsNumberField.setText(resultSet.getString("halls"));
                addressField.setText(resultSet.getString("adress"));
                break;
            }
        }
        initializeManager();
        initializeFoodMenu();
        foodTypeField.setValue("1st Meal");
        foodTypeField.setItems(types);
        oldMenuName = foodMenuName.getText();
    }

    public void initializeFoodMenu() throws FileNotFoundException, SQLException {
        methods.foodMenu(restaurantNameField.getText(), foodMenu);
    }

    public void initializeManager() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        ArrayList<String> managers = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantPick) & resultSet.getString("type").equals("manager")) {
                managers.add(resultSet.getString("username"));
            }
        }
        ObservableList<String> managersList = FXCollections.observableArrayList(managers);
        managerField.setItems(managersList);
    }

    @FXML
    private void setFoodMenuName(ActionEvent event) {
        restaurantNameField.setDisable(true);
        foodMenuName.setDisable(true);
        addFoodButton.setDisable(false);
    }

    @FXML
    private void chooseImage(ActionEvent event) throws IOException, RuntimeException {
        methods.chooseImage(imageWayField);
    }

    @FXML
    private void addMeal(ActionEvent event) throws SQLException, FileNotFoundException {
        methods.addFood(foodMenuName.getText(),  foodNameField,  foodPriceField, foodTypeField, restaurantPick);
        initializeFoodMenu();
    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException{
        methods.switchScene(event, parent, "admin-menu.fxml");
    }

    @FXML
    private void applyChanges(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`restaurant` SET " +
                        "`name` = '" + restaurantNameField.getText() +"'," +
                        "`adress` = '"+ addressField.getText() +"'," +
                        "`halls` = '"+ hallsNumberField.getText() +"'," +
                        "`manager` = '"+ managerField.getValue() +"'," +
                        "`menuName` = '"+ foodMenuName.getText() +"'," +
                        "`image` = '"+ imageWayField.getText() +"'," +
                        "`description` = '"+ descriptionField.getText() +"'," +
                        "`status` = '"+ "Active" +"'" +
                        "WHERE (`name` = '"+restaurantPick+"');");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.food SET " +
                        "`foodMenuName` = '"+ foodMenuName.getText() +"' " +
                        "WHERE (`foodMenuName` = '"+oldMenuName+"');");
        preparedStatement.executeUpdate();

        methods.switchScene(event, parent, "admin-menu.fxml");
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
    private ContextMenu contextMenu;
    @FXML
    private TextField addressField;
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
    private ChoiceBox<String> managerField;
    @FXML
    private TreeView<String> foodMenu;
    @FXML
    private TextField restaurantNameField;

}
