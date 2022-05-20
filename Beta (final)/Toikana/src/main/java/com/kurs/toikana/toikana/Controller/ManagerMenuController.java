package com.kurs.toikana.toikana.Controller;

import com.kurs.toikana.toikana.Methods;
import com.kurs.toikana.toikana.objects.Event;
import com.kurs.toikana.toikana.objects.Hall;
import com.kurs.toikana.toikana.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class ManagerMenuController {
    String name ;
    String restaurantPick ;
    String role ;
    String oldMenuName ;
    String selectedFood;
    private Parent parent;
    ObservableList<String> types= FXCollections.observableArrayList( "1st Meal", "2nd Meal", "Drink", "Dessert");
    ObservableList<String> forms= FXCollections.observableArrayList( "Circle", "Square", "Triangle", "Arch", "X", "Y", "H");
    ObservableList<Order> orders = FXCollections.observableArrayList(new Order(0, "","","", ""));
    ObservableList<Event> events = FXCollections.observableArrayList(new Event("", "","",""));
    ObservableList<Hall> halls = FXCollections.observableArrayList(new Hall("", "","", ""));

    Methods methods = new Methods();

    @FXML
    private void initialize() throws SQLException, IOException {
        initializeAccount();
        displayInfo();
        initializeEvents();
        initializeFoodMenu();
        initializeOrders();
        initializeHalls();

        foodTypeField.setValue("1st Meal");
        foodTypeField.setItems(types);
        arrangementFormField.setValue("Circle");
        arrangementFormField.setItems(forms);
        oldMenuName = foodMenuName.getText();
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
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.event;");
        ArrayList<Event> eventsList = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantPick)) {
                eventsList.add(new Event(resultSet.getString("type"),resultSet.getString("date"), resultSet.getString("status"), resultSet.getString("price")));
            }
        }
        Collections.reverse(eventsList);
        events = FXCollections.observableArrayList(eventsList);
        eventsName.setCellValueFactory(new PropertyValueFactory<Event,String>("name"));
        eventsDate.setCellValueFactory(new PropertyValueFactory<Event,String>("date"));
        eventsStatus.setCellValueFactory(new PropertyValueFactory<Event,String>("status"));
        eventsPay.setCellValueFactory(new PropertyValueFactory<Event,String>("pay"));

        eventsTable.setItems(events);
    }

    private void initializeHalls() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.hall;");
        ArrayList<Hall> hallsList = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantPick)) {
                hallsList.add(new Hall(resultSet.getString("name"),resultSet.getString("tablesNumber"),resultSet.getString("takenTables"), resultSet.getString("arrangementForm")));
            }
        }
        halls = FXCollections.observableArrayList(hallsList);
        hallNameColumn.setCellValueFactory(new PropertyValueFactory<Hall,String>("name"));
        tablesNumberColumn.setCellValueFactory(new PropertyValueFactory<Hall,String>("tablesNumber"));
        arrangementFormColumn.setCellValueFactory(new PropertyValueFactory<Hall,String>("arrangementForm"));

        hallTable.setItems(halls);
    }

    @FXML
    public void initializeFoodMenu() throws SQLException, FileNotFoundException {
        methods.foodMenu(restaurantPick, foodMenu);
    }

    private void initializeOrders() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.order;");
        ArrayList<Order> ordersList = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantPick) & resultSet.getString("status").equals("Waiting")) {
                ordersList.add(new Order(resultSet.getInt("id"),resultSet.getString("type"), resultSet.getString("date"), resultSet.getString("status"), ""));
            }
        }
        Collections.reverse(ordersList);
        orders = FXCollections.observableArrayList(ordersList);
        ordersID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        ordersName.setCellValueFactory(new PropertyValueFactory<Order,String>("name"));
        ordersDate.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));

        ordersTable.setItems(orders);
    }
    @FXML
    public void selectOrder() {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        if (order != null){
            orderName.setText(order.getName()+"/"+order.getDate());
        }
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
                hallsLabel.setText("Halls: "+resultSet.getString("halls"));
                description.setText("Description:\n "+resultSet.getString("description"));
                restaurantName.setText(restaurantPick+":");
                foodMenuName.setText(resultSet.getString("menuName"));

                if(  resultSet.getString("status").equals("Active")){
                    activeStatus.setSelected(true);
                    activeStatus.setText("Active");
                }
            }
        }
    }

    @FXML
    public void selectHall() {
        Hall hall = hallTable.getSelectionModel().getSelectedItem();
        if (hall != null){
            hallName.setVisible(true);
            tablesNumberField.setVisible(true);tablesNumberLabel.setVisible(true);
            takenTablesField.setVisible(true);takenTablesLabel.setVisible(true);
            arrangementFormField.setVisible(true);arrangementFormLabel.setVisible(true);
            applyHallLabel.setVisible(true);

            hallName.setText(hall.getName());
            tablesNumberField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
            tablesNumberField.getValueFactory().setValue(Integer.parseInt(hall.getTablesNumber()));
            takenTablesField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
            takenTablesField.getValueFactory().setValue(Integer.parseInt(hall.getTakenTables()));
            arrangementFormField.setValue(hall.getArrangementForm());

            hallTable.setItems(halls);
        }
    }
    @FXML
    void applyHallChanges(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.hall SET " +
                        "`tablesNumber` = '" + tablesNumberField.getValue() +"'," +
                        "`takenTables` = '"+ takenTablesField.getValue() +"'," +
                        "`arrangementForm` = '"+ arrangementFormField.getValue() +"' " +
                        "WHERE `name` = '"+hallName.getText()+"' and `restaurant` = '"+restaurantPick+"';");
        preparedStatement.executeUpdate();
        initializeHalls();
    }
    @FXML
    private void addMeal(ActionEvent event) throws SQLException, FileNotFoundException {
        methods.addFood(foodMenuName.getText(),  foodNameField,  foodPriceField, foodTypeField, restaurantPick);
        initializeFoodMenu();
    }
    @FXML
    private void statusChanges() throws SQLException {
        if(activeStatus.isSelected()){
            activeStatus.setSelected(false);
            activeStatus.setText("Inactive");
        } else {
            activeStatus.setSelected(true);
            activeStatus.setText("Active");
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`restaurant` SET " +
                        "`status` = '"+ activeStatus.getText() +"'" +
                        "WHERE (`name` = '"+restaurantPick+"');");
        preparedStatement.executeUpdate();
    }
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "login.fxml");
    }
    @FXML
    private void backAdmin(ActionEvent event) throws IOException {
        methods.switchScene(event, parent, "admin-menu.fxml");
    }
    @FXML
    public void changeMenuName(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`restaurant` SET " +
                        "`menuName` = '"+ foodMenuName.getText() +"' " +
                        "WHERE (`name` = '"+restaurantPick+"');");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.food SET " +
                        "`foodMenuName` = '"+ foodMenuName.getText() +"' " +
                        "WHERE (`foodMenuName` = '"+oldMenuName+"');");
        preparedStatement.executeUpdate();
        try {
            initializeFoodMenu();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void selectItem() {
        TreeItem<String> item = foodMenu.getSelectionModel().getSelectedItem();
        if(item != null) {
            String[] selectedFoodArray = item.getValue().split("\\|");
            selectedFood = selectedFoodArray[0].trim();
        }
    }
    @FXML
    private void deleteFood(ActionEvent event) throws SQLException, FileNotFoundException {
        methods.deleteFood(selectedFood, restaurantPick);
        initializeFoodMenu();
    }

    @FXML
    private void checkEvents() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from `jdbs-toikana`.order");
        ArrayList<String> events = new ArrayList<>();
        while (resultSet.next()) {
            if(resultSet.getString("date").equals(String.valueOf(checkDate.getValue())) & resultSet.getString("status").equals("Active") & resultSet.getString("restaurant").equals(restaurantPick)){
                events.add(resultSet.getString("type"));
            }
        }
        Collections.reverse(events);
        checkList.setItems(FXCollections.observableArrayList(events));
    }

    @FXML
    public void acceptEvent(ActionEvent event) throws SQLException, IOException {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`current_user` SET " +
                        "`orderPick` = '" + order.getId() + "' " +
                        "WHERE (`id` = '1');");

        preparedStatement.executeUpdate();
        methods.switchScene(event, parent, "new-event.fxml");
    }

    @FXML
    public void refuseEvent() throws SQLException {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.order SET " +
                        "`status` = 'Refused' " +
                        "WHERE (`id` = '"+order.getId()+"');");
        preparedStatement.executeUpdate();
        initializeOrders();
    }
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
    private ListView<String> checkList;
    @FXML
    private TextArea description;
    @FXML
    private Label tablesNumberLabel;
    @FXML
    private Label takenTablesLabel;
    @FXML
    private Button applyHallLabel;
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private Label arrangementFormLabel;
    @FXML
    private TableColumn<Event, String> eventsName;
    @FXML
    private TableColumn<Event, String> eventsDate;
    @FXML
    private TableColumn<Event, String> eventsStatus;
    @FXML
    private TableColumn<Event, String> eventsPay;
    @FXML
    private TextField foodMenuName;
    @FXML
    private Label hallsLabel;
    @FXML
    private TableColumn<Hall, String> arrangementFormColumn;
    @FXML
    private ChoiceBox<String> arrangementFormField;
    @FXML
    private Label hallName;
    @FXML
    private TableColumn<Hall, String> hallNameColumn;
    @FXML
    private TableColumn<Hall, String> tablesNumberColumn;
    @FXML
    private Spinner<Integer> tablesNumberField;
    @FXML
    private Spinner<Integer> takenTablesField;
    @FXML
    private TableView<Hall> hallTable;
    @FXML
    private RadioButton activeStatus;
    @FXML
    private Button logOutButton;
    @FXML
    private TreeView<String> foodMenu;
    @FXML
    private Label nameLabel;
    @FXML
    private Label orderName;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> ordersDate;
    @FXML
    private TableColumn<Order, String> ordersName;
    @FXML
    private TableColumn<Order, Integer> ordersID;
    @FXML
    private ImageView restaurantImage;
    @FXML
    private Label restaurantName;
    @FXML
    private Label roleLabel;

}
