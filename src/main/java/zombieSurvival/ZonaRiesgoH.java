package zombieSurvival;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaRiesgoH {
    private ListaHilos humanos;
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
            boolean elegido = false;
            int numHumanos = humanos.getSize();
            while (!elegido) {
                int humano = random.nextInt(numHumanos);
                eleccion= (Humano) humanos.get(humano);
                if (!eleccion.isSiendoAtacado()){
                    eleccion.setSiendoAtacado(true);
                    elegido = true;
                }
            }
        } catch (Exception e) {
        } finally {
            cerrojo.unlock();
        }
        return eleccion;
    }
}
