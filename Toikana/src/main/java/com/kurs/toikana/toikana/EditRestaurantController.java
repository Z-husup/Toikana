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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class EditRestaurantController {

    String restaurantPick;

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
                adressField.setText(resultSet.getString("adress"));
                break;
            }
        }
        initializeManager();
        initializeFoodMenu();
        foodTypeField.setValue("1st Meal");
        foodTypeField.setItems(types);

    }


    public void initializeFoodMenu() throws FileNotFoundException, SQLException {
        TreeItem<String> rootItem = new TreeItem<>("Food Menu");
        TreeItem<String> branchItem1 = new TreeItem<>("1st Meal", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\1stMeal.png"), 20, 20, false, false)));
        TreeItem<String> branchItem2 = new TreeItem<>("2nd Meal", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\2ndMeal.png"), 20, 20, false, false)));
        TreeItem<String> branchItem3 = new TreeItem<>("Drinks", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\Drinks.png"), 20, 20, false, false)));
        TreeItem<String> branchItem4 = new TreeItem<>("Desserts", new ImageView(new Image(new FileInputStream("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\Desserts.png"), 20, 20, false, false)));

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from food");
        ArrayList<TreeItem<String>> food1 = new ArrayList<>();
        ArrayList<TreeItem<String>> food2 = new ArrayList<>();
        ArrayList<TreeItem<String>> food3 = new ArrayList<>();
        ArrayList<TreeItem<String>> food4 = new ArrayList<>();
        String menuName = foodMenuName.getText();
        while (resultSet.next()) {
            if (resultSet.getString("type").equals("1st Meal") & resultSet.getString("foodMenuName").equals(menuName)) {
                food1.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
            }
            if (resultSet.getString("type").equals("2nd Meal") & resultSet.getString("foodMenuName").equals(menuName)) {
                food2.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
            }
            if (resultSet.getString("type").equals("Drink") & resultSet.getString("foodMenuName").equals(menuName)) {
                food3.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
            }
            if (resultSet.getString("type").equals("Dessert") & resultSet.getString("foodMenuName").equals(menuName)) {
                food4.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price")+" som"));
            }
        }
        branchItem1.getChildren().addAll(food1);
        branchItem2.getChildren().addAll(food2);
        branchItem3.getChildren().addAll(food3);
        branchItem4.getChildren().addAll(food4);

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4);
        newMenuTree.setShowRoot(false);
        newMenuTree.setRoot(rootItem);
    }

    public void initializeManager() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        ArrayList<String> managers = new ArrayList<>();
        while (resultSet.next()) {
            if (resultSet.getString("restaurant").equals(restaurantNameField.getText())) {
                managers.add(resultSet.getString("username"));
            }
        }
        ObservableList<String> managersList = FXCollections.observableArrayList(managers);
        managerField.setItems(managersList);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("DELETE");
        contextMenu.getItems().add(item1);

    }
    @FXML
    private Button addFoodButton;

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private TextField adressField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField foodMenuName;

    @FXML
    private TextField foodNameField;

//    @FXML
//    private void selectItem(ActionEvent event) throws SQLException, FileNotFoundException {
//        if (newMenuTree.getSelectionModel().getSelectedItem()!=null) {
//            TreeItem<String> chosen = newMenuTree.getSelectionModel().getSelectedItem();
//
//            String[] chosenArray = chosen.toString().split("|||||");
//            String chosenFood = chosenArray[0].trim(); System.out.println(chosenFood);
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "DELETE FROM `jdbs-toikana`.`food` WHERE (`restaurant` = '" + restaurantPick + "' and `name` = '" + chosenFood + "');");
//            preparedStatement.executeUpdate();
//            initializeFoodMenu();
//        }
//    }
    @FXML
    private void setFoodMenuName(ActionEvent event) throws SQLException {
            restaurantNameField.setDisable(true);
            foodMenuName.setDisable(true);
            addFoodButton.setDisable(false);
    }
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
    private TreeView<String> newMenuTree;

    @FXML
    private TextField restaurantNameField;


    @FXML
    private void chooseImage(ActionEvent event) throws IOException, RuntimeException {
        FileChooser fileChooser = new FileChooser();
        File file  = fileChooser.showOpenDialog(new Stage());

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(file.getPath()));
            os = new FileOutputStream(new File("C:\\Users\\Жусуп\\Desktop\\JavaProjects\\Toikana\\src\\main\\resources\\images\\"+file.getName() ));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        imageWayField.setText("C:/Users/Жусуп/Desktop/JavaProjects/Toikana/src/main/resources/images/"+file.getName());
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
    private void switchToMenu(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(("admin-menu.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void applyChanges(ActionEvent event) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `jdbs-toikana`.`restaurant` SET " +
                        "`name` = '" + restaurantNameField.getText() +"'," +
                        "`adress` = '"+ adressField.getText() +"'," +
                        "`halls` = '"+ hallsNumberField.getText() +"'," +
                        "`manager` = '"+ managerField.getValue() +"'," +
                        "`menuName` = '"+ foodMenuName.getText() +"'," +
                        "`image` = '"+ imageWayField.getText() +"'," +
                        "`description` = '"+ descriptionField.getText() +"'," +
                        "`status` = '"+ "active" +"'" +
                        "WHERE (`name` = '"+restaurantPick+"');");
        preparedStatement.executeUpdate();

        Parent parent = FXMLLoader.load(getClass().getResource(("admin-menu.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
