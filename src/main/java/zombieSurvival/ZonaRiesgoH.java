package zombieSurvival;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaRiesgoH {
    private ListaHilos humanos;
    private ArrayList<Humano> humanosDisponibles;
    private Lock cerrojo = new ReentrantLock();
    private Random random = new Random();

    public ZonaRiesgoH(ListaHilos humanos) {
        this.humanos = humanos;
    }

    public ListaHilos getHumanos() {
        return humanos;
    }

    public Humano elegirHumano() {
        cerrojo.lock();
        Humano eleccion = null;
        try {
            int numHumanos = humanos.getSize();
            int humano = random.nextInt(numHumanos);
            eleccion = (Humano) humanos.get(humano);
            if (!eleccion.isSiendoAtacado() && !eleccion.isHerido()) {
                eleccion.setSiendoAtacado(true);
            }
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> elegirHumano");
        } finally {
            cerrojo.unlock();
        }
        return eleccion;
    }
}
