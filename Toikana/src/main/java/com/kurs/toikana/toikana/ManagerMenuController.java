package com.kurs.toikana.toikana;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;

public class ManagerMenuController {

    String name;
    String restaurantPick;
    String role;

    ObservableList<String> types= FXCollections.observableArrayList( "1st Meal", "2nd Meal", "Drink", "Dessert");

    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        displayInfo();
        initializeEvents();
        initializeFoodMenu();

        foodTypeField.setValue("1st Meal");
        foodTypeField.setItems(types);
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

        if (role.equals("admin")){
            logOutButton.setVisible(false);
            backAdminButton.setVisible(true);
        }
    }

    private void initializeEvents() throws SQLException {
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
                mealsMenu.setShowRoot(false);
                mealsMenu.setRoot(rootItem);
            }
        }
        nameLabel.setText(name);
        roleLabel.setText(role);
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
            }
        }
    }

    @FXML
    private void addMeal(ActionEvent event) throws SQLException, FileNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO food (`foodMenuName`,`name`,`price`,`type`,`restaurant`) VALUES(" +
                        "  '" + foodMenuName.getText() +
                        "','" + foodNameField.getText() +
                        "','" + foodPriceField.getText() +
                        "','" + foodTypeField.getValue() +
                        "','" + restaurantPick +
                        "' )");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        initializeFoodMenu();
    }

    @FXML
    private void statusChanges() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = null;
        if (activeStatus.isSelected()) {
            inactiveStatus.setSelected(false);
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO restaurant (`status`) VALUES('active');");
        }
        else if (inactiveStatus.isSelected()) {
            activeStatus.setSelected(false);
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO restaurant (`status`) VALUES('inactive');");
        }
        preparedStatement.executeUpdate();
    }

    @FXML
    private Button accepEvent;

    @FXML
    private Button backAdminButton;

    @FXML
    private TextField foodNameField;

    @FXML
    private Label address;

    @FXML
    private TextField foodPriceField;

    @FXML
    private ChoiceBox<String> foodTypeField;
    @FXML
    private Label address1;

    @FXML
    private DatePicker checkDate;

    @FXML
    private ListView<?> checkList;

    @FXML
    private Label description;

    @FXML
    private Label eventName1;

    @FXML
    private TableColumn<String, String> eventsDate;

    @FXML
    private TableView<?> eventsList;

    @FXML
    private TableColumn<String, String> eventsName;

    @FXML
    private TableColumn<String, String> eventsStatus;

    @FXML
    private Label foodMenuName;

    @FXML
    private Label halls;

    @FXML
    private RadioButton activeStatus, inactiveStatus;

    @FXML
    private Button logOutButton;



    @FXML
    private TreeView<String> mealsMenu;

    @FXML
    private Label nameLabel;

    @FXML
    private Label orderName;

    @FXML
    private TableView<String> orders;

    @FXML
    private TableColumn<String, String> ordersDate;

    @FXML
    private TableColumn<String, String> ordersName;

    @FXML
    private Button refuseEvent;

    @FXML
    private ImageView restaurantImage;

    @FXML
    private Label restaurantName;

    @FXML
    private Label roleLabel;

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void backAdmin(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("admin-menu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
