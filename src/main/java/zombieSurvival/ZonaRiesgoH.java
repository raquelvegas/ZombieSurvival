package zombieSurvival;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaRiesgoH {
    private ListaHilos humanos;
    private Lock cerrojoAdd = new ReentrantLock();
    private ArrayList<Humano> humanosDisponibles = new ArrayList<>();
    private Lock cerrojo = new ReentrantLock();
    private Random random = new Random();

    public ZonaRiesgoH(ListaHilos humanos) {
        this.humanos = humanos;
    }

    public void añadirHumano(Humano h) {
        cerrojoAdd.lock();
        try {
            humanosDisponibles.add(h);
            humanos.meter(h);
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> añadirHumano");
        } finally {
            cerrojoAdd.unlock();
        }
    }

    public ListaHilos getHumanos() {
        return humanos;
    }

    public Humano elegirHumano() {
        cerrojo.lock();
        Humano eleccion = null;
        try {
            int numHumanos = humanosDisponibles.size();
            if (numHumanos >= 1) {
                int humano = random.nextInt(numHumanos);
                eleccion = humanosDisponibles.get(humano);
                humanosDisponibles.remove(humano);
            }
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> elegirHumano");
        } finally {
            cerrojo.unlock();
        }
        return eleccion;
    }
}
