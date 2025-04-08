package zombieSurvival;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ListaHilos {
    ArrayList<Thread> lista = new ArrayList<>();
    Text text;

    public ListaHilos(Text text) {
        this.text = text;
    }

    public synchronized void meter(Individuo i) {
        lista.add(i);
        imprimir();
    }

    public synchronized void sacar(Individuo i) {
        lista.remove(i);
        imprimir();
    }

    private void imprimir() {
        String contenido = "";

        for (int i = 0; i < lista.size(); i++) {
            contenido = contenido + lista.get(i).getName() + (" ");
        }

        String finalContenido = contenido;

        Platform.runLater(() -> {
            if (text != null) {
                text.setText(finalContenido);
            }
        });
    }
}
