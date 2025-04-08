package zombieSurvival;

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
        StringBuilder contenido = new StringBuilder();
        if (lista.size() >= 2) {
            for (int i = 0; i < lista.size() - 1; i++) {
                contenido.append(lista.get(i).getName()).append(" - ");
            }
        } else if (lista.size() == 1) {
            contenido.append(lista.getLast().getName());
        }
        text.setText(contenido.toString());
    }
}
