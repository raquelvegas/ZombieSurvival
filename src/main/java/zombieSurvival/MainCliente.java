package zombieSurvival;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
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


        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                String equipo = InetAddress.getLocalHost().getHostAddress();
                InterfazRMI informacion = (InterfazRMI) Naming.lookup("//" + equipo + "/Informacion");
                while (controladorRemoto.isStart()) {
                    Integer humanosRefugio = informacion.humanosEnRefugio();
                    controladorRemoto.sethRefugio(String.valueOf(humanosRefugio));
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Error | Clase -> MainCliente | Método -> main | Excepcion en la localización del objeto distribuido");            }
        });
    }
}
