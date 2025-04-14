package zombieSurvival;

import javafx.scene.text.Text;
import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class Juego {
    private ListaHilos zonaComun;
    private ListaHilos zonaDescanso;
    private ListaHilos comiendo;
    private ListaHilos colaComedorTxt;
    private Text textoComida;
    private ArrayList<ListaHilos> esperaTuneles;
    private ArrayList<ListaHilos> tunelesTxt;
    private ArrayList<Tunel> tuneles = new ArrayList<>();
    private ArrayList<ListaHilos> vueltaTuneles;
    private ArrayList<ZonaRiesgoH> riesgoIzq;
    private ArrayList<ListaHilos> riesgoDch;
    private LinkedBlockingDeque<String> colaComedor = new LinkedBlockingDeque<>();
    private ArrayList<CyclicBarrier> barrerasTuneles = new ArrayList<>();
    private boolean enPausa = false;
    private Random random = new Random();
    private static final Logger log = LogConfig.getLogger();


    public Juego(ListaHilos zonaComun, ListaHilos zonaDescanso, ListaHilos colaComedor, ListaHilos comiendo, Text textoComida, ArrayList<ListaHilos> esperaTuneles, ArrayList<ListaHilos> tunelesTxt, ArrayList<ListaHilos> tunelesVuelta, ArrayList<ZonaRiesgoH> riesgoIzq, ArrayList<ListaHilos> riesgoDch) {
        this.zonaComun = zonaComun;
        this.zonaDescanso = zonaDescanso;
        this.colaComedorTxt = colaComedor;
        this.comiendo = comiendo;
        this.textoComida = textoComida;
        this.esperaTuneles = esperaTuneles;
        this.tunelesTxt = tunelesTxt;
        this.vueltaTuneles = tunelesVuelta;
        this.riesgoIzq = riesgoIzq;
        this.riesgoDch = riesgoDch;

        // Inicializamos las barreras de los túneles y los túneles
        for (int i = 0; i < 4; i++) {
            barrerasTuneles.add(new CyclicBarrier(3));
            tuneles.add(new Tunel(i));
        }
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
        comiendo.meter(i);
    }

    public void sacarComedor(Humano i) {
        comiendo.sacar(i);
    }

    public void meterColaComedor(Humano i) {
        colaComedorTxt.meter(i);
    }

    public void sacarColaComedor(Humano i) {
        colaComedorTxt.sacar(i);
    }

    public void cruzarIda(Humano i, int tunel) {
        entrarEsperaTunel(i, tunel);
        tuneles.get(tunel).salirAlExterior(i);
        esperaTuneles.get(tunel).sacar(i);
        tunelesTxt.get(tunel).meter(i);
        try {
            i.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error | Clase -> Juego | Método -> cruzarIda");
        }
        tuneles.get(tunel).salirDelTunel();
        tunelesTxt.get(tunel).sacar(i);
    }

    public void cruzarVuelta(Humano i, int tunel) {
        vueltaTuneles.get(tunel).meter(i);
        tuneles.get(tunel).volverDentro(i);
        vueltaTuneles.get(tunel).sacar(i);
        esperaTuneles.get(tunel).sacar(i);
        tunelesTxt.get(tunel).meter(i);
        try {
            i.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error | Clase -> Juego | Método -> cruzarIda");
        }
        tuneles.get(tunel).salirDelTunel();
        tunelesTxt.get(tunel).sacar(i);
    }

    public void entrarEsperaTunel(Humano i, int tunel) {
        try {
            barrerasTuneles.get(tunel).await();
        } catch (Exception e) {
            System.out.println("Error | Clase -> Juego | Método -> entrarEsperaTunel");
        }
        sacarZonaComun(i);
        esperaTuneles.get(tunel).meter(i);
    }

    public void comer(Humano i) {
        meterColaComedor(i);
        log.info("HUMANO " + i.getName() + " -> Comedor");
        try {
            colaComedor.take();
            sacarColaComedor(i);
            String cantidadCeros = String.format("%04d", colaComedor.size());
            textoComida.setText(cantidadCeros);
            meterComedor(i);
            i.sleep((long) (3000 + random.nextDouble() * 2000));
        } catch (InterruptedException e) {
            System.out.println("ERROR | Clase -> Juego | Método -> comer");
        }
        log.info("HUMANO " + i.getName() + " -> Come");
        sacarComedor(i);
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

    public synchronized void pausar() {
        enPausa = true;
        log.info("Juego en pausa");
    }

    public synchronized void reanudar() {
        enPausa = false;
        notifyAll();  // Despierta a los hilos esperando
        log.info("Juego reanudado");
    }

    public synchronized void esperarSiPausado() {
        while (enPausa) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Respeta la interrupción
            }
        }
    }

    public boolean estaEnPausa() {
        return enPausa;
    }
}
