package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
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
        ListaHilos zonaDescanso = new ListaHilos(descansoText);

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


        ListaHilos riesgo0Izq = new ListaHilos(riesgo0IzqText);
        ListaHilos riesgo1Izq = new ListaHilos(riesgo1IzqText);
        ListaHilos riesgo2Izq = new ListaHilos(riesgo2IzqText);
        ListaHilos riesgo3Izq = new ListaHilos(riesgo3IzqText);

        ZonaRiesgoH riesgoIzq0 = new ZonaRiesgoH(riesgo0Izq);
        ZonaRiesgoH riesgoIzq1 = new ZonaRiesgoH(riesgo1Izq);
        ZonaRiesgoH riesgoIzq2 = new ZonaRiesgoH(riesgo2Izq);
        ZonaRiesgoH riesgoIzq3 = new ZonaRiesgoH(riesgo3Izq);


        ArrayList<ZonaRiesgoH> riesgoIzq = new ArrayList<>();
        riesgoIzq.addAll(Arrays.asList(riesgoIzq0, riesgoIzq1, riesgoIzq2, riesgoIzq3));




        ListaHilos riesgo0Dch = new ListaHilos(riesgo0DchText);
        ListaHilos riesgo1Dch = new ListaHilos(riesgo1DchText);
        ListaHilos riesgo2Dch = new ListaHilos(riesgo2DchText);
        ListaHilos riesgo3Dch = new ListaHilos(riesgo3DchText);

        ArrayList<ListaHilos> riesgoDch = new ArrayList<>();
        riesgoDch.addAll(Arrays.asList(riesgo0Dch, riesgo1Dch, riesgo2Dch, riesgo3Dch));



        Juego juego = new Juego(zonaComun, zonaDescanso, esperaTuneles, riesgoIzq, riesgoDch);


        // Inicialización de la simulación
        new Thread(() -> {
            for (int i = 0; i < 1; i++) {
                Zombie z = new Zombie(juego, i);
                z.start();
            }

            for (int i = 1; i < 6; i++) {
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
