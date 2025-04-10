package zombieSurvival;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger log = LogConfig.getLogger();

    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info(" INICIO DE LA EJECUCIÓN ");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("zombieSurvival.fxml")));
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("interfazFinal.fxml")));
        primaryStage.setTitle("Zombies Survival");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}