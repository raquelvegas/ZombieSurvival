package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.Random;
import java.util.logging.Logger;

public class Zombie extends Thread {
    private int id;
    private Juego juego;
    private Random random = new Random();
    private int contadorMuertes = 0;
    private static final Logger log = LogConfig.getLogger();

    private long TIEMPO_ATAQUE = 500;
    private long TIEMPO_ESPERA = 2000;


    public Zombie(Juego juego, int id) {
        this.juego = juego;
        this.id = id;
        String StringCeros = String.format("%04d", id);
        super.setName("Z" + StringCeros);
    }

    private void dormir(long tiempo) {
        try {
            sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Volver a interrumpir el hilo si ocurre una interrupción
        }
    }

    private void atacar(Humano h, int zona) {
        dormir((long) (TIEMPO_ATAQUE + random.nextDouble() * 500));

        if (h != null && h.isAlive()) {
            double ataque = random.nextDouble();
            if (ataque > (double) 2 / 3) { // Ataque exitoso
                contadorMuertes++;
                h.interrupt();
                log.info("El zombie " + getName() + " ha matado al humano " + h.getName());
            } else { // Ataque fallido
                log.info("El zombie " + getName() + " ha herido al humano " + h.getName());
                h.setHerido(true);
                h.setSiendoAtacado(false);
            }
        }
    }

    public void run() {
        while (true) {
            int zona = random.nextInt(4);
            juego.meterZonaRiesgoDch(this, zona);

            Humano objetivo = juego.getRiesgoIzq().get(zona).elegirHumano();

            if (objetivo != null) {
                atacar(objetivo, zona); // Ataca solo a uno
            }

            // Espera en la zona aunque no haya podido atacar
            dormir((long) (TIEMPO_ESPERA + random.nextDouble() * 2000));

            juego.sacarZonaRiesgoDch(this, zona);
        }
    }
}
