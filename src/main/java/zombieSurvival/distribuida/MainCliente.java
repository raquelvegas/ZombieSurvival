package zombieSurvival.distribuida;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainCliente {
    public static void main(String[] args) {
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
