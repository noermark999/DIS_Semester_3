package opgavePassword_F2023.passwordOpgave;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;

public class Gui extends Application {
    Button btnLogin, btnOpret, btnscene2;
    Label lblbrugernavn, lblPassword, lblBesked;
    Label lblscene2, lblInfoBruger;
    GridPane pane1, pane2;
    Scene scene1, scene2;
    Stage thestage;
    private PasswordField password = new PasswordField();
    private static TextField brugernavn = new TextField();
    private Connection minConnection = DriverManager.getConnection(
            "jdbc:sqlserver://localhost:1433;encrypt=true;trustserverCertificate=true;databaseName=Lek21;user=sa;password=reallyStrongPwd123;");

    public Gui() throws SQLException {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        thestage = primaryStage;

        btnLogin = new Button("Log in");
        btnOpret = new Button("Opret");
        btnscene2 = new Button("Tilbage til log in");
        btnLogin.setOnAction(e -> ButtonClicked(e));
        btnOpret.setOnAction(e -> ButtonClicked(e));
        btnscene2.setOnAction(e -> ButtonClicked(e));
        lblbrugernavn = new Label("Navn");
        lblPassword = new Label("Password");
        lblBesked = new Label("Hello World");

        lblscene2 = new Label("Du er nu logget ind");
        lblInfoBruger = new Label("Bruger info");

        pane1 = new GridPane();
        pane2 = new GridPane();
        pane1.setVgap(10);
        pane2.setVgap(10);

        pane1.setStyle("-fx-background-color: yellow;-fx-padding: 10px;");
        pane2.setStyle("-fx-background-color: lightgreen;-fx-padding: 10px;");

        pane1.setPadding(new Insets(5));
        pane1.setHgap(10);
        pane1.setVgap(10);

        pane1.add(lblbrugernavn, 0, 0);
        pane1.add(brugernavn, 0, 1, 2, 1);
        pane1.add(lblPassword, 0, 2);
        pane1.add(password, 0, 3, 2, 1);
        pane1.add(btnLogin, 0, 4);
        pane1.add(btnOpret, 1, 4);
        pane1.add(lblBesked, 0, 5, 2, 1);

        pane2.setPadding(new Insets(5));
        pane2.setHgap(10);
        pane2.setVgap(10);

        pane2.add(lblInfoBruger, 0, 0);
        pane2.add(btnscene2, 0, 1);

        scene1 = new Scene(pane1, 200, 200);
        scene2 = new Scene(pane2, 200, 200);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            try {
                String sql = "select * from Users where USERNAME = ?";
                PreparedStatement statement = minConnection.prepareStatement(sql);
                statement.clearParameters();

                statement.setString(1, brugernavn.getText());

                ResultSet res = statement.executeQuery();

                if (res.next()) {
                    byte[] salt = res.getBytes("salt");
                    String hashedEnteredPassword = hashPassword(password.getText(), salt);

                    // Sammenlign hashede adgangskoder
                    if (hashedEnteredPassword.equals(res.getString("password"))) {
                        // Brugernavn og adgangskode er korrekte
                        thestage.setScene(scene2);
                    } else {
                        // Adgangskoden er forkert
                        lblBesked.setText("Forkert adgangskode");
                    }
                } else {
                    // Brugeren blev ikke fundet i databasen
                    lblBesked.setText("Brugeren blev ikke fundet");
                }

            } catch (SQLException ex) {
                lblBesked.setText("Fejl under login");
                System.out.println(ex.getMessage());
            }


        } else if (e.getSource() == btnOpret) {
            try {
                // Generer en tilfældig salt
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);

                // Hash adgangskoden med saltet
                String hashedPassword = hashPassword(password.getText(), salt);

                // Gem brugeren i databasen med brugernavn, hash og salt
                String sql = "INSERT INTO Users VALUES (?, ?, ?)";
                PreparedStatement statement = minConnection.prepareStatement(sql);
                statement.clearParameters();

                statement.setString(1, brugernavn.getText());
                statement.setString(2, hashedPassword);
                statement.setBytes(3, salt);

                statement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "Errorcode: " + ex.getErrorCode() + ", " + ex.getSQLState());
                if (ex.getErrorCode() == 2627) {
                    lblBesked.setText("Brugernavn findes allerede!");
                }
            }
            password.clear();
            brugernavn.clear();
        } else {
            lblBesked.setText("");
            password.clear();
            brugernavn.clear();
            thestage.setScene(scene1);

        }
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Tilføj saltet til adgangskoden og hash
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());

            // Konverter til en hexadecimal streng
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02X", b));
            }
            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not available.", e);
        }
    }

}
