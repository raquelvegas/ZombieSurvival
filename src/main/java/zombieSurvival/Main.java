package zombieSurvival;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger log = LogConfig.getLogger();
    Stage mainStage = new Stage();
    ControladorNuevaInterfaz controller = new ControladorNuevaInterfaz();
    Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Configuración de la imagen del logo de carga
        ImageView logoImageView = new ImageView(new Image(getClass().getResource("/zombieIcon.png").toExternalForm()));
        logoImageView.setFitWidth(400);
        logoImageView.setFitHeight(400);
        logoImageView.setOpacity(0); // Empieza invisible

        // Barra de progreso
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);

        // Crear el VBox para mostrar logo y barra de progreso
        VBox vBox = new VBox();
        vBox.getChildren().addAll(logoImageView, progressBar);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Crear un StackPane para contener el VBox
        StackPane root1 = new StackPane();
        root1.getChildren().add(vBox);
        root1.setStyle("-fx-background-color: black;");

        // Configurar la escena de la ventana de carga
        Scene scene = new Scene(root1, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cargando...");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false); // Asegurarse de que la ventana no esté maximizada
        primaryStage.setOnCloseRequest(event -> event.consume()); // Desactiva el cierre de la ventana

        // Crear la ventana principal (mainStage)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazFinal.fxml"));
        Parent root = loader.load(); // Cargar el FXML
        controller = loader.getController(); // Obtener el controlador
        mainStage.setTitle("Zombies Survival");
        mainStage.setScene(new Scene(root));
        mainStage.setResizable(false);

        // Configurar la alerta de salida para la ventana principal
        mainStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar salida");
            alert.setHeaderText("Estás a punto de abandonar «Zombie Survial». ¿Estás seguro?");
            alert.setContentText("Si sales, perderás la simulación actual.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                log.info("FIN DE LA EJECUCIÓN");
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        });

        // Mostrar la ventana de carga
        primaryStage.show();

        // Animación para la pantalla de carga
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(logoImageView.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(logoImageView.opacityProperty(), 1)), // Hacer que el logo sea visible
                new KeyFrame(Duration.seconds(3), new KeyValue(progressBar.progressProperty(), 1)), // Llenar la barra de progreso al 100%
                new KeyFrame(Duration.seconds(3), event -> {
                    // Cerrar la ventana de carga y mostrar la ventana principal
                    primaryStage.close(); // Cierra la ventana de carga
                    mainStage.show(); // Muestra la ventana principal

                    // Aquí se inicia la simulación después de que la carga haya terminado
                    controller.reproducirCancion(controller.getCancionActual());
                    iniciarSimulacion();
                })
        );

        timeline.setCycleCount(1); // La animación solo se ejecuta una vez
        timeline.play(); // Reproducir la animación
    }

    // Método que inicia la simulación después de la pantalla de carga
    private void iniciarSimulacion() {
        log.info("INICIO DE LA EJECUCIÓN");
        // Inicialización de la simulación, solo después de que la pantalla de carga haya terminado
        new Thread(() -> {
            // Crear un zombie
            for (int i = 0; i < 1; i++) {
                Zombie z = new Zombie(controller.getJuego(), i);
                z.start();
            }

            // Crear los humanos
            for (int i = 1; i < 1111; i++) {
                Humano ind = new Humano(controller.getJuego(), i);
                ind.start();
                try {
                    Thread.sleep((long) (0.5 + (1.5 * random.nextDouble())) * 1000); // medio segundo
                } catch (InterruptedException e) {
                    System.out.println("ERROR | Clase -> Main | Método -> iniciarSimulación");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
