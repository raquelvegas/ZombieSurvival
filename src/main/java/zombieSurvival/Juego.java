package zombieSurvival;

import javafx.scene.text.Text;
import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class Juego {
    private ListaHilos zonaComun;
    private ListaHilos zonaDescanso;
    private ListaHilos comedor;
    private Text textoComida;
    private ArrayList<ListaHilos> esperaTuneles;
    private ArrayList<ZonaRiesgoH> riesgoIzq;
    private ArrayList<ListaHilos> riesgoDch;
    private LinkedBlockingDeque colaComedor = new LinkedBlockingDeque();
    private static final Logger log = LogConfig.getLogger();


    public Juego(ListaHilos zonaComun, ListaHilos zonaDescanso, ListaHilos comedor, Text textoComida, ArrayList<ListaHilos> esperaTuneles, ArrayList<ZonaRiesgoH> riesgoIzq, ArrayList<ListaHilos> riesgoDch) {
        this.zonaComun = zonaComun;
        this.zonaDescanso = zonaDescanso;
        this.comedor = comedor;
        this.textoComida = textoComida;
        this.esperaTuneles = esperaTuneles;
        this.riesgoIzq = riesgoIzq;
        this.riesgoDch = riesgoDch;
    }

    public void meterZonaComun(Humano i){
        zonaComun.meter(i);
        log.info("HUMANO " + i.getName() + " -> Zona Común");
    }

    public void sacarZonaComun(Humano i){
        zonaComun.sacar(i);
    }

    public void meterZonaDescanso(Humano i) {
        zonaDescanso.meter(i);
        log.info("HUMANO " + i.getName() + " -> Zona Descanso");
    }

    public void sacarZonaDescanso(Humano i) {
        zonaDescanso.sacar(i);
    }

    public void meterZonaRiesgoIzq(Humano h, int zona){
        riesgoIzq.get(zona).añadirHumano(h);
    }

    public void sacarZonaRiesgoIzq(Humano h, int zona) {
        riesgoIzq.get(zona).eliminarHumano(h);
    }

    public void meterZonaRiesgoDch(Zombie z, int zona) {
        riesgoDch.get(zona).meter(z);
    }

    public void sacarZonaRiesgoDch(Zombie z, int zona) {
        riesgoDch.get(zona).sacar(z);
    }

    public void meterComedor(Humano i) {
        comedor.meter(i);
    }

    public void sacarComedor(Humano i) {
        comedor.sacar(i);
    }

    public void esperarTunel(Humano i, int tunel){
        esperaTuneles.get(tunel).meter(i);
    }

    public void comer(Humano i) {
        meterComedor(i);
        log.info("HUMANO " + i.getName() + " -> Comedor");
        try {
            colaComedor.take();
            String cantidadCeros = String.format("%04d", colaComedor.size());
            textoComida.setText(cantidadCeros);
        } catch (InterruptedException e) {
            System.out.println("ERROR | Clase -> Juego | Método -> comer");
        }
        sacarComedor(i);
        log.info("HUMANO " + i.getName() + " -> Come");
    }

    public void dejarComida() {
        try {
            colaComedor.put("Comida");
            colaComedor.put("Comida");
            String cantidadCeros = String.format("%04d", colaComedor.size());
            textoComida.setText(cantidadCeros);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ZonaRiesgoH> getRiesgoIzq() {
        return riesgoIzq;
    }
}
