package zombieSurvival;

import javafx.scene.text.Text;
import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private LinkedBlockingDeque<String> colaComedor = new LinkedBlockingDeque<>(); // Deque == Queue pero se puede insertar/extraer por el principio y por el final
    private ArrayList<CyclicBarrier> barrerasTuneles = new ArrayList<>();
    private boolean enPausa = false;
    private Random random = new Random();
    private List<Zombie> zombies = new ArrayList<>();
    private Lock cerrojoZ = new ReentrantLock();
    private static LogConfig log;


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
            tuneles.add(new Tunel(i, log));
        }
    }


    // Getters y Setters
    public ListaHilos getZonaComun() {
        return zonaComun;
    }

    public ListaHilos getZonaDescanso() {
        return zonaDescanso;
    }

    public ListaHilos getComiendo() {
        return comiendo;
    }

    public ListaHilos getColaComedorTxt() {
        return colaComedorTxt;
    }

    public ArrayList<ZonaRiesgoH> getRiesgoIzq() {
        return riesgoIzq;
    }

    public void setLogger(LogConfig log){
        this.log = log;
    }


    // Funciones RMI
    public void nuevoZombie(Zombie z) {
        cerrojoZ.lock();
        try {
            zombies.add(z);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrojoZ.unlock();
        }
    }

    public ArrayList<String> nombresZombiesMortales() {
        ArrayList<String> nombres = new ArrayList<>();
        cerrojoZ.lock();
        try {
            zombies.sort(Comparator.comparingInt(Zombie::getMuertes).reversed());
            if (!zombies.isEmpty()) {
                nombres.add(zombies.get(0).getName());
                if (zombies.size() > 1) {
                    nombres.add(zombies.get(1).getName());
                    if (zombies.size() > 2) {
                        nombres.add(zombies.get(2).getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrojoZ.unlock();
        }
        return nombres;
    }

    public ArrayList<Integer> muertesZombiesMortales() {
        ArrayList<Integer> muertes = new ArrayList<>();
        cerrojoZ.lock();
        try {
            zombies.sort(Comparator.comparingInt(Zombie::getMuertes).reversed());
            if (!zombies.isEmpty()) {
                muertes.add(zombies.get(0).getMuertes());
                if (zombies.size() > 1) {
                    muertes.add(zombies.get(1).getMuertes());
                    if (zombies.size() > 2) {
                        muertes.add(zombies.get(2).getMuertes());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrojoZ.unlock();
        }
        return muertes;
    }

    public Integer humanosEnTunel(int i) {
        Integer humanos = 0;
        humanos += esperaTuneles.get(i).getSize();
        if (tuneles.get(i).isOcupado()) {
            humanos++;
        }
        humanos += vueltaTuneles.get(i).getSize();

        return humanos;
    }

    public Integer humanosEnRiesgo(int i) {
        return riesgoIzq.get(i).getHumanos().getSize();
    }

    public Integer zombiesEnRiesgo(int i) {
        return riesgoDch.get(i).getSize();
    }


    // Métodos de impresión
    public void meterZonaComun(Humano i){
        zonaComun.meter(i);
        log.logInfo("HUMANO " + i.getName() + " -> Zona Común");
    }

    public void sacarZonaComun(Humano i){
        zonaComun.sacar(i);
    }

    public void meterZonaDescanso(Humano i) {
        zonaDescanso.meter(i);
        log.logInfo("HUMANO " + i.getName() + " -> Zona Descanso");
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


    // Métodos play / pause
    public synchronized void pausar() {
        enPausa = true;
        log.logInfo("Juego en pausa");
    }

    public synchronized void reanudar() {
        enPausa = false;
        notifyAll();  // Despierta a los hilos esperando
        log.logInfo("Juego reanudado");
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


    // Otros métodos
    public void cruzarIda(Humano i, int tunel) {
        esperarSiPausado();
        entrarEsperaTunel(i, tunel);
        esperarSiPausado();
        tuneles.get(tunel).salirAlExterior(i);
        esperarSiPausado();
        esperaTuneles.get(tunel).sacar(i);
        esperarSiPausado();
        tunelesTxt.get(tunel).meter(i);
        esperarSiPausado();
        try {
            i.dormir(1000);
        } catch (InterruptedException e) {
            System.out.println("ERROR | Clase -> Juego | Método -> cruzarIda | InterruptedException al dormir al humano");
        }
        tuneles.get(tunel).salirDelTunel();
        esperarSiPausado();
        tunelesTxt.get(tunel).sacar(i);
    }

    public void cruzarVuelta(Humano i, int tunel) {
        esperarSiPausado();
        vueltaTuneles.get(tunel).meter(i);
        esperarSiPausado();
        tuneles.get(tunel).volverDentro(i);
        esperarSiPausado();
        vueltaTuneles.get(tunel).sacar(i);
        esperarSiPausado();
        tunelesTxt.get(tunel).meter(i);
        esperarSiPausado();
        try {
            i.dormir(1000);
        } catch (InterruptedException e) {
            System.out.println("ERROR | Clase -> Juego | Método -> cruzarVuelta | InterruptedException al dormir al humano");
        }
        esperarSiPausado();
        tuneles.get(tunel).salirDelTunel();
        esperarSiPausado();
        tunelesTxt.get(tunel).sacar(i);
    }

    public void entrarEsperaTunel(Humano i, int tunel) {
        try {
            barrerasTuneles.get(tunel).await();
        } catch (Exception e) {
            System.out.println("Error | Clase -> Juego | Método -> entrarEsperaTunel");
        }
        esperarSiPausado();
        sacarZonaComun(i);
        esperarSiPausado();
        esperaTuneles.get(tunel).meter(i);
    }

    public void comer(Humano i) {
        esperarSiPausado();
        meterColaComedor(i);
        log.logInfo("HUMANO " + i.getName() + " -> Comedor");
        try {
            esperarSiPausado();
            colaComedor.take();
            esperarSiPausado();
            sacarColaComedor(i);
            String cantidadCeros;
            if (colaComedor.size() > 9999) {
                cantidadCeros = String.format("%05d", colaComedor.size());
            } else {
                cantidadCeros = String.format("%04d", colaComedor.size());
            }
            textoComida.setText(cantidadCeros);
            esperarSiPausado();
            meterComedor(i);
            i.dormir((long) (3000 + random.nextDouble() * 2000));
        } catch (InterruptedException e) {
            System.out.println("ERROR | Clase -> Juego | Método -> comer");
        }
        log.logInfo("HUMANO " + i.getName() + " -> Come");
        esperarSiPausado();
        sacarComedor(i);
    }

    public void dejarComida(Humano h) {
        try {
            colaComedor.put("Comida");
            colaComedor.put("Comida");
            log.logInfo("HUMANO "+h.getName()+" -> Deja su comida.");
            String cantidadCeros;
            if (colaComedor.size() > 9999) {
                cantidadCeros = String.format("%05d", colaComedor.size());
            } else {
                cantidadCeros = String.format("%04d", colaComedor.size());
            }
            textoComida.setText(cantidadCeros);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
