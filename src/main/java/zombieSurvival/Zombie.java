package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;

public class Zombie extends Thread {
    private int id;
    private Juego juego;
    private Random random = new Random();
    private int contadorMuertes = 0;
    private static final LogConfig log = new LogConfig();

    private long TIEMPO_ATAQUE = 500;
    private long TIEMPO_ESPERA = 2000;


    public Zombie(Juego juego, int id) {
        this.juego = juego;
        this.id = id;
        String StringCeros = String.format("%04d", id);
        super.setName("Z" + StringCeros);
        juego.nuevoZombie(this);
    }

    public int getMuertes(){
        return contadorMuertes;
    }

    private void dormir(long tiempo) {
        long dormido = 0;
        long intervalo = 100; // Intervalo de sueño corto (100 ms)

        while (dormido < tiempo) {
            juego.esperarSiPausado();

            try {
                long restante = Math.min(intervalo, tiempo - dormido);
                sleep(restante);
                dormido += restante;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void atacar(Humano h) {
        h.interrupt();
        dormir((long) (TIEMPO_ATAQUE + random.nextDouble() * 500));

        if (h.isAlive()) {
            double ataque = random.nextDouble();
            if (ataque > (double) 2 / 3) { // Ataque exitoso
                contadorMuertes++;
                h.setVivo(false);
                log.logInfo("El zombie " + getName() + " ha matado al humano " + h.getName());
            } else { // Ataque fallido
                log.logInfo("El zombie " + getName() + " ha herido al humano " + h.getName());
                h.setHerido(true);
            }
            h.setSiendoAtacado(false);
        }
        synchronized (h) {
            h.notify();
        }
    }

    public void run() {
        while (true) {
            int zona = random.nextInt(4);
            juego.esperarSiPausado();
            juego.meterZonaRiesgoDch(this, zona);

            Humano objetivo = juego.getRiesgoIzq().get(zona).elegirHumano();

            if (objetivo != null) {
                juego.esperarSiPausado();
                atacar(objetivo); // Ataca solo a uno
            }

            // Espera en la zona aunque no haya podido atacar
            dormir((long) (TIEMPO_ESPERA + random.nextDouble() * 2000));

            juego.esperarSiPausado();
            juego.sacarZonaRiesgoDch(this, zona);
        }
    }
}
