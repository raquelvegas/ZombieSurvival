package zombieSurvival;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


        // Generamos un nuevo hilo mediante un pool de hilos para que se encargue de actualizar la información en tiepo real
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                boolean cambio = true;
                InterfazRMI informacion = (InterfazRMI) Naming.lookup("//192.168.157.1/Informacion");
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
                    ArrayList<String> Nzombies = informacion.nombresZombiesLetales();
                    ArrayList<Integer> Mzombies = informacion.muertesZombiesLetales();
                    if (!Nzombies.isEmpty()) {
                        String zom1 = Nzombies.getFirst();
                        Integer Mzom1 = Mzombies.getFirst();
                        String ms1 = zom1 + " - " + Mzom1 + " muertes";
                        controladorRemoto.setMuertes1(ms1);
                        if (Nzombies.size() > 1) {
                            String zom2 = Nzombies.get(1);
                            Integer Mzom2 = Mzombies.get(1);
                            String ms2 = zom2 + " - " + Mzom2 + " muertes";
                            controladorRemoto.setMuertes2(ms2);
                            if (Nzombies.size() > 2) {
                                String zom3 = Nzombies.get(2);
                                Integer Mzom3 = Mzombies.get(2);
                                String ms3 = zom3 + " - " + Mzom3 + " muertes";
                                controladorRemoto.setMuertes3(ms3);
                            }
                        }
                    }

                    // Pausar
                    if (controladorRemoto.isCambio()) {
                        if (controladorRemoto.isPausado()) {
                            informacion.play_pause(true);
                        } else {
                            informacion.play_pause(false);
                        }
                        controladorRemoto.cambio();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
