package zombieSurvival.distribuida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainCliente extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Crear la ventana principal (mainStage)
        Stage remoteStage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainCliente.class.getResource("zombiesRemoto.fxml"));
        Parent root = loader.load(); // Cargar el FXML
        remoteStage.setTitle("Zombies Survival - Remoto");
        remoteStage.setScene(new Scene(root));
        remoteStage.setResizable(false);
        remoteStage.show();


        try {
            InterfazRMI informacion = (InterfazRMI) Naming.lookup("//127.0.0.1/Informacion");
            for (int i = 0; i < 3; i++) {
                Integer humanoRefugio = informacion.humanosEnRefugio();
                System.out.println(humanoRefugio);
            }
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Error | Clase -> MainCliente | Método -> main | Excepcion en la localización del objeto distribuido");
        }
    }
}
