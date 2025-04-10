package zombieSurvival;

import java.util.Random;
import java.util.logging.Logger;

public class Humano extends Thread {
    private Juego juego;
    private int id;
    private boolean comida = false;
    private boolean siendoAtacado = false;
    private Random random = new Random();
    private boolean herido = false;
    private static final Logger log = LogConfig.getLogger();
    public Humano(Juego juego, int id) {
        this.id = id;
        this.juego = juego;
        String StringCeros = String.format("%04d", id);
        super.setName("H" + StringCeros);
    }

    public boolean isSiendoAtacado() {
        return siendoAtacado;
    }

    public void setSiendoAtacado(boolean siendoAtacado) {
        this.siendoAtacado = siendoAtacado;
    }

    public boolean isHerido() {
        return herido;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    public void run() {
        while (true) {
            //Zona común (1-2 segundos)
            juego.meterZonaComun(this);
            try {
                sleep((long) (5 + random.nextDouble()) * 1000);
            } catch (InterruptedException e) {
            }
            juego.sacarZonaComun(this);

            // Seleccion de túnel
            int tunel = random.nextInt(4);

            // Atravesar túnel

            // Zona exterior (3-5 segundos)
            juego.meterZonaRiesgoIzq(this,tunel);
            try {
                sleep((long) (3 + random.nextDouble() * 2) * 1000);
                while (siendoAtacado){
                    sleep(500);
                }
            } catch (InterruptedException e){ // Humano muerto
                juego.sacarZonaRiesgoIzq(this, tunel);
                log.warning("Humano " + this.getName() + " -> Muerto");
                Zombie z = new Zombie(juego, id);
                z.start();
                break;

            }
            juego.sacarZonaRiesgoIzq(this, tunel);

            // Depositar comida

            // Zona de descanso
            if (herido) {
                juego.meterZonaDescanso(this);
                try {
                    sleep((long) (2 + (random.nextDouble() * 2) * 1000));
                } catch (Exception e) {
                }
                this.herido = false;
                juego.sacarZonaDescanso(this);
            }
            // Comedor
        }
    }
}
