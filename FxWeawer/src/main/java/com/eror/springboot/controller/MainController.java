package com.eror.springboot.controller;

import com.eror.springboot.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView("MainController.fxml")
public class MainController {

    private final String greeting;
    private final FxWeaver fxWeaver;
    private final FxControllerAndView<DialogController, VBox> dialog;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;

    @Autowired
    private UserService userService;

    @FXML
    public Label label;
    @FXML
    public Button helloButton;
    @FXML
    public Button openSimpleDialogButton;
    @FXML
    public Button openTiledDialogButton;

    public MainController(@Value("${spring.application.demo.greeting}") String greeting,
                          FxWeaver fxWeaver,
                          FxControllerAndView<DialogController, VBox> dialog) {
        this.greeting = greeting;
        this.fxWeaver = fxWeaver;
        this.dialog = dialog;
    }



    public String getPassword() {
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    @FXML
    public void initialize() {
        helloButton.setOnAction(
                actionEvent -> this.label.setText(greeting)
        );
/*
        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show()
        );
*/
        openSimpleDialogButton.setOnAction(
                actionEvent -> {
                    dialog.getController().setName("Marko Eror");
                    dialog.getController().show();
                }

        );
        loginButton.setOnAction(
                actionEvent -> {
                    if(userService.authenticate(getUsername(), getPassword())){

                        FxControllerAndView<TiledDialogController, VBox> tiledDialog =
                                fxWeaver.load(TiledDialogController.class);
                        tiledDialog.getView().ifPresent(
                                v -> {
                                    Label label = new Label();
                                    label.setText("Dynamically added Label");
                                    v.getChildren().add(label);
                                }
                        );
                        tiledDialog.getController().show();


                    }else{
                        lblLogin.setText("Login Failed.");
                    }

                }
        );
        openTiledDialogButton.setOnAction(
                actionEvent -> {
                    FxControllerAndView<TiledDialogController, VBox> tiledDialog =
                            fxWeaver.load(TiledDialogController.class);
                    tiledDialog.getView().ifPresent(
                            v -> {
                                Label label = new Label();
                                label.setText("Dynamically added Label");
                                v.getChildren().add(label);
                            }
                    );
                    tiledDialog.getController().show();
                }
        );
    }

}
