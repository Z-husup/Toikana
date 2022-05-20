package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import com.kurs.toikana.toikana.objects.Food;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewEventController  {

    String name;
    String restaurantPick;
    String orderPick;
    String role;
    String selectedFood;
    String selectedPrice;
    int sum = 0;
    private  Parent parent;

    Methods methods = new Methods();
    ObservableList<Food> food = FXCollections.observableArrayList(new Food("","",""));
    ArrayList<Food> foodList = new ArrayList<>();

    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        displayInfo();
        initializeFoodMenu();
        additionalServiceField.setText("0");

    }

    private void initializeAccount() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.current_user;");
        while (resultSet.next()) {
            name = resultSet.getString("name");
            restaurantPick =  resultSet.getString("restaurantPick");
            role = resultSet.getString("role");
            orderPick = resultSet.getString("orderPick");
        }
    }
    @FXML
    private void initializeFoodMenu() throws SQLException, FileNotFoundException {
        methods.foodMenu(restaurantPick, foodMenu);
    }
    @FXML
    public void displayInfo() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.order");
        while (resultSet.next()) {
            if (orderPick.equals(resultSet.getString("id"))){
                eventType.setValue(resultSet.getString("type"));
                clientName.setText(resultSet.getString("client"));
                orderInfo.setText(resultSet.getString("description"));
                datePicker.setValue(LocalDate.parse(resultSet.getString("date")));
            }
            if (eventType.getValue()!=null){
                if (eventType.getValue().equals("Wedding") || eventType.getValue().equals("Toi") || eventType.getValue().equals("Kuran")){
                    depositField.setText("15000");
                }
                if (eventType.getValue().equals("Birthday") || eventType.getValue().equals("Corporate") || eventType.getValue().equals("Individual")){
                    depositField.setText("5000");
                }
            }
        }
    }
    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("manager-menu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void initializeAdditionalService() {
        if (additionalServiceChoice.isSelected()){
            additionalServiceField.setVisible(true);
            additionalServiceLabel.setVisible(true);
        }
        else{
            additionalServiceField.setVisible(false);
            additionalServiceLabel.setVisible(false);
        }
    }

    @FXML
    public void selectItem() {
        TreeItem<String> item = foodMenu.getSelectionModel().getSelectedItem();
        if(item.getValue().contains("|")) {
            String[] selectedFoodArray = item.getValue().split("\\|");
            selectedFood = selectedFoodArray[0].trim();
            chosenFoodField.setText(selectedFood);
            String[] priceArray = selectedFoodArray[1].split(" ");
            selectedPrice = priceArray[3].trim();
        }
    }

    @FXML
    public void addFood() {
        foodList.add(new Food(selectedFood,selectedPrice,amountOfFood.getText()));
        food = FXCollections.observableArrayList(foodList);
        eventFoodColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("name"));
        eventFoodPriceColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("price"));
        eventFoodAmountColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("amount"));
        eventFoodMenu.setItems(food);
        sum += Integer.parseInt(selectedPrice) * Integer.parseInt(amountOfFood.getText());
        sumLabel.setText(String.valueOf(sum));
    }

    @FXML
    private void createNewEvent(ActionEvent event) throws IOException, SQLException {
        sum +=  Integer.parseInt(serviceField.getText()) +
                Integer.parseInt(additionalServiceField.getText()) +
                Integer.parseInt(depositField.getText());
        sum += (int) (sum * (Double.parseDouble(serviceField.getText()) / 100));

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO event (`id`,`restaurant`,`manager`,`date`,`type`,`guestNumber`,`price`,`status`) VALUES(0," +
                        "  '" + restaurantPick +
                        "','" + name +
                        "','" + datePicker.getValue() +
                        "','" + eventType.getValue() +
                        "','" + guestNumber.getText() +
                        "','" + sum +
                        "','" + "Planned" +
                        "' );");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.order SET " +
                        "`status` = '" + "Active" + "'," +
                        "`pay` = '" + sum + "' " +
                        "WHERE (`id` = '"+orderPick+"');");
        preparedStatement.executeUpdate();

        methods.switchScene(event, parent, "manager-menu.fxml");
    }
    @FXML
    private CheckBox additionalServiceChoice;
    @FXML
    private TextField additionalServiceField;
    @FXML
    private Label additionalServiceLabel;
    @FXML
    private TextField amountOfFood;

    @FXML
    private Button backButton;

    @FXML
    private Label chosenFoodField;
    @FXML
    private Label sumLabel;
    @FXML
    private TextField clientName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button createEventButton;

    @FXML
    private TableView<Food> eventFoodMenu;

    @FXML
    private TableColumn<Food, String> eventFoodAmountColumn;

    @FXML
    private TableColumn<Food, String> eventFoodColumn;
    @FXML
    private TextField depositField;
    @FXML
    private TableColumn<Food, String> eventFoodPriceColumn;
    @FXML
    private ChoiceBox<String> eventType;

    @FXML
    private TreeView<String> foodMenu;

    @FXML
    private TextField guestNumber;

    @FXML
    private TextArea orderInfo;
    @FXML
    private TextField serviceField;

}
