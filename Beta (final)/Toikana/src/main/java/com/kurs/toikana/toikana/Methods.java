package com.kurs.toikana.toikana;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

// some repeating methods
public class Methods {

    public static void chooseImage(TextField imageWayField) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file  = fileChooser.showOpenDialog(new Stage());

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(file.getPath()));
            os = new FileOutputStream(new File("src\\main\\resources\\images\\"+file.getName() ));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        imageWayField.setText("src/main/resources/images/"+file.getName());
    }
    public static void addFood(String foodMenuName, TextField foodNameField, TextField foodPriceField,
                               ChoiceBox<String> foodTypeField, String restaurantPick) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO food (`foodMenuName`,`name`,`price`,`type`,`restaurant`) VALUES(" +
                        "  '" + foodMenuName +
                        "','" + foodNameField.getText() +
                        "','" + foodPriceField.getText() +
                        "','" + foodTypeField.getValue() +
                        "','" + restaurantPick +
                        "' )");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public  void deleteFood(String selectedFood, String restaurantNameField) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039" );
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM `jdbs-toikana`.`food` WHERE `name` = '"+selectedFood+"' and `restaurant` = '"+
                        restaurantNameField+"'");
        preparedStatement.executeUpdate();
    }

    public void switchScene(ActionEvent event, Object root, String fxml) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene((Parent) parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene(MouseEvent event, Parent root, String fxml) throws IOException {
        //on Mouse event
        Parent parent = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene((Parent) parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void foodMenu(String restaurantPick, TreeView<String> foodMenu) throws SQLException, FileNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbs-toikana", "root", "00390039");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from food");

        while (resultSet.next()) {
            if (restaurantPick.equals(resultSet.getString("restaurant"))) {
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
                        food1.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price") + " som"));
                    }
                    if (resultSet.getString("type").equals("2nd Meal") & resultSet.getString("restaurant").equals(menuName)) {
                        food2.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price") + " som"));
                    }
                    if (resultSet.getString("type").equals("Drink") & resultSet.getString("restaurant").equals(menuName)) {
                        food3.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price") + " som"));
                    }
                    if (resultSet.getString("type").equals("Dessert") & resultSet.getString("restaurant").equals(menuName)) {
                        food4.add(new TreeItem<>(resultSet.getString("name") + "    |   " + resultSet.getString("price") + " som"));
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
    }


}
