package zombieSurvival;

import javafx.scene.text.Text;

import java.util.ArrayList;

public class ListaHilos {
    private ArrayList<Thread> lista = new ArrayList<>();
    private Text text;

    public ListaHilos(Text text) {
        this.text = text;
    }

    public synchronized void meter(Thread i) {
        lista.add(i);
        imprimir();
    }

    public synchronized void sacar(Thread i) {
        lista.remove(i);
        imprimir();
    }

    private synchronized void imprimir() {
        String contenido = "";

        for (int i = 0; i < lista.size(); i++) {
            contenido = contenido + lista.get(i).getName() + (" ");
        }
        text.setText(contenido);

//        String finalContenido = contenido;

//        Platform.runLater(() -> {
//            if (text != null) {
//                text.setText(finalContenido);
//            }
//        });
    }

    public int getSize() {
        return lista.size();
    }

    public Thread get(int index){
        return lista.get(index);
    }
}
