package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Tunel {
    private int id;
    private Lock cerrojo = new ReentrantLock();
    private Condition esperandoEntrar = cerrojo.newCondition();
    private Condition esperandoSalir = cerrojo.newCondition();
    private boolean ocupado = false;
    private int enEsperaEntrar = 0;
    private static LogConfig log;


    public Tunel(int id, LogConfig log) {
        this.id = id;
        this.log = log;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void volverDentro(Humano h) {
        cerrojo.lock();
        try {
            enEsperaEntrar++;
            while (ocupado) {
                esperandoEntrar.await();
            }
            log.logInfo("HUMANO " + h.getName() + " -> Volviendo por el tunel " + id);
            enEsperaEntrar--;
            ocupado = true;
        } catch (Exception e) {
            System.out.println("Error | Clase -> Túnel | Método -> volverDentro");
        } finally {
            cerrojo.unlock();
        }
    }

    public void salirAlExterior(Humano h) {
        cerrojo.lock();
        try {
            while (ocupado || enEsperaEntrar > 0) {
                esperandoSalir.await();
            }
            log.logInfo("HUMANO " + h.getName() + " -> Saliendo por el tunel " + id);
            ocupado = true;
        } catch (Exception e) {
            System.out.println("Error | Clase -> Tunel | Método -> salirAlExterior");
        } finally {
            cerrojo.unlock();
        }
    }

    public void salirDelTunel() {
        cerrojo.lock();
        try {
            ocupado = false;
            if (enEsperaEntrar > 0) {
                esperandoEntrar.signal();
                log.logInfo("Tunel " + id + " -> Despierta a un hilo que quiere entrar.");
            } else {
                esperandoSalir.signal();
                log.logInfo("Tunel " + id + " -> Despierta a un hilo que quiere salir.");
            }
        } catch (Exception e) {
            System.out.println("Error | Clase -> Tunel | Método -> salirDelTunel");
        } finally {
            cerrojo.unlock();
        }
    }
}