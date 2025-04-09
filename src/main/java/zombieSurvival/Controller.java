package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private Random random = new Random();

    @FXML
    private Text
            descansoText, comedorText, comidaCount, zonaComunText,

            tunel0IzqText, tunel0MidText, tunel0DchText,
            tunel1IzqText, tunel1MidText, tunel1DchText,
            tunel2IzqText, tunel2MidText, tunel2DchText,
            tunel3IzqText, tunel3MidText, tunel3DchText,

            riesgo0IzqText, riesgo0DchText,
            riesgo1IzqText, riesgo1DchText,
            riesgo2IzqText, riesgo2DchText,
            riesgo3IzqText, riesgo3DchText;


    @FXML
    public void initialize(){
        // Inicialización de las zonas

        ListaHilos zonaComun = new ListaHilos(zonaComunText);

        ListaHilos tunel0Izq = new ListaHilos(tunel0IzqText);
        ListaHilos tunel0Mid = new ListaHilos(tunel0MidText);
        ListaHilos tunel0Dch = new ListaHilos(tunel0DchText);
        ListaHilos tunel1Izq = new ListaHilos(tunel1IzqText);
        ListaHilos tunel1Mid = new ListaHilos(tunel1MidText);
        ListaHilos tunel1Dch = new ListaHilos(tunel1DchText);
        ListaHilos tunel2Izq = new ListaHilos(tunel2IzqText);
        ListaHilos tunel2Mid = new ListaHilos(tunel2MidText);
        ListaHilos tunel2Dch = new ListaHilos(tunel2DchText);
        ListaHilos tunel3Izq = new ListaHilos(tunel3IzqText);
        ListaHilos tunel3Mid = new ListaHilos(tunel3MidText);
        ListaHilos tunel3Dch = new ListaHilos(tunel3DchText);

        ArrayList<ListaHilos> esperaTuneles = new ArrayList<>();
        esperaTuneles.add(tunel0Izq);
        esperaTuneles.add(tunel1Izq);
        esperaTuneles.add(tunel2Izq);
        esperaTuneles.add(tunel3Izq);

        Juego juego = new Juego(zonaComun, esperaTuneles);


        // Inicialización de la simulación
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Humano ind = new Humano(juego, i);
                ind.start();
                try {
                    Thread.sleep((long) (0.5 + (1.5 * random.nextDouble())) * 1000); // medio segundo
                } catch (InterruptedException e) {
                }
            }
        }).start();

    }
}
