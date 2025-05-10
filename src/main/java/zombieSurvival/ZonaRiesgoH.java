package zombieSurvival;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaRiesgoH {
    private ListaHilos humanos;
    private ArrayList<Humano> humanosDisponibles = new ArrayList<>();
    private Lock cerrojo = new ReentrantLock();
    private Random random = new Random();

    public ZonaRiesgoH(ListaHilos humanos) {
        this.humanos = humanos;
    }

    public void añadirHumano(Humano h) {
        cerrojo.lock();
        try {
            humanosDisponibles.add(h);
            humanos.meter(h);
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> añadirHumano");
        } finally {
            cerrojo.unlock();
        }
    }

    public Humano elegirHumano() {
        cerrojo.lock();
        try {
            if (!humanosDisponibles.isEmpty()) {
                int humanoIndex = random.nextInt(humanosDisponibles.size());
                Humano h = humanosDisponibles.get(humanoIndex);
                if (!h.isSiendoAtacado() && h.isAlive()) {
                    h.setSiendoAtacado(true);
                    humanosDisponibles.remove(h);
                    return h;
                }
            }
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> elegirHumano");
        } finally {
            cerrojo.unlock();
        }
        return null;
    }

    public void eliminarHumano(Humano h) {
        cerrojo.lock();
        try {
            if (humanosDisponibles.contains(h)) {
                humanosDisponibles.remove(h);
                h.setComida(true); // Si el humano sigue en esta lista significa que no ha sido atacado y, por tanto, ha recolectado la comida
            }
            humanos.sacar(h);
        } catch (Exception e) {
            System.out.println("Error | Clase -> ZonaRiesgoH | Método -> eliminarHumano");
        } finally {
            cerrojo.unlock();
        }
    }

    public ListaHilos getHumanos() {
        return humanos;
    }
}
