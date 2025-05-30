package zombieSurvival;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class MainCliente extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Crear la ventana principal (mainStage)
        Stage remoteStage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainCliente.class.getResource("zombiesRemoto.fxml"));
        Parent root = loader.load(); // Cargar el FXML
        ControladorRemoto controladorRemoto = loader.getController();
        remoteStage.setTitle("Zombies Survival - Remoto");
        remoteStage.setScene(new Scene(root));
        remoteStage.setResizable(false);
        remoteStage.show();
        controladorRemoto.setStart(true);
        controladorRemoto.getErrorInfo().setVisible(false);

        remoteStage.setOnCloseRequest(event -> {
                LogConfig.logInfo("FIN DE LA EJECUCIÓN DEL CLIENTE");
                Platform.exit();
                System.exit(0);
        });


        // Generamos un nuevo hilo mediante un pool de hilos para que se encargue de actualizar la información en tiepo real
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                String ipAddress = ip.getHostAddress();
                // Tomamos la ip del ordenador local
                InterfazRMI informacion = (InterfazRMI) Naming.lookup("//" + ipAddress + "/Informacion");

                while (controladorRemoto.isStart()) {
                    // Refugio
                    Integer humanosRefugio = informacion.humanosEnRefugio();
                    controladorRemoto.sethRefugio(String.valueOf(humanosRefugio));

                    // Túneles
                    Integer humanosT1 = informacion.humanosTunel1();
                    controladorRemoto.setT1(String.valueOf(humanosT1));
                    Integer humanosT2 = informacion.humanosTunel2();
                    controladorRemoto.setT2(String.valueOf(humanosT2));
                    Integer humanosT3 = informacion.humanosTunel3();
                    controladorRemoto.setT3(String.valueOf(humanosT3));
                    Integer humanosT4 = informacion.humanosTunel4();
                    controladorRemoto.setT4(String.valueOf(humanosT4));

                    // Riesgo humanos
                    Integer humanosR1 = informacion.humanosRiesgo1();
                    controladorRemoto.sethR1(String.valueOf(humanosR1));
                    Integer humanosR2 = informacion.humanosRiesgo2();
                    controladorRemoto.sethR2(String.valueOf(humanosR2));
                    Integer humanosR3 = informacion.humanosRiesgo3();
                    controladorRemoto.sethR3(String.valueOf(humanosR3));
                    Integer humanosR4 = informacion.humanosRiesgo4();
                    controladorRemoto.sethR4(String.valueOf(humanosR4));

                    // Riesgo zombies
                    Integer zombiesR1 = informacion.zombisRiesgo1();
                    controladorRemoto.setzR1(String.valueOf(zombiesR1));
                    Integer zombiesR2 = informacion.zombisRiesgo2();
                    controladorRemoto.setzR2(String.valueOf(zombiesR2));
                    Integer zombiesR3 = informacion.zombisRiesgo3();
                    controladorRemoto.setzR3(String.valueOf(zombiesR3));
                    Integer zombiesR4 = informacion.zombisRiesgo4();
                    controladorRemoto.setzR4(String.valueOf(zombiesR4));

                    // Zombies mortales
                    ArrayList<String> zombies = informacion.zombiesLetales();
                    if (!zombies.isEmpty()) {
                        controladorRemoto.setMuertes1(zombies.get(0));
                        if (zombies.size() > 1) {
                            controladorRemoto.setMuertes2(zombies.get(1));
                            if (zombies.size() > 2) {
                                controladorRemoto.setMuertes3(zombies.get(2));
                            }
                        }
                    }

                    // Pausar
                    if (controladorRemoto.tocaCambio()) {
                        boolean hecho = informacion.play_pause();
                        /***
                         * Si no se lleva a cabo la acción (solo ocurrirá cuando se quiera reanudar el juego con la
                         * pantalla de información abierta) se lanza un mensaje de error
                         */

                        if (!hecho && !controladorRemoto.getErrorInfo().isVisible()) {
                            new Thread(() -> {
                                Platform.runLater(() -> controladorRemoto.getErrorInfo().setVisible(true));
                                try {
                                    sleep(2000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Platform.runLater(() -> controladorRemoto.getErrorInfo().setVisible(false));
                            }).start();
                        }
                        controladorRemoto.cambio();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args){
        launch();
    }

}
