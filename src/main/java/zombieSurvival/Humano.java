package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.Random;
import java.util.logging.Logger;

public class Humano extends Thread {
    private Juego juego;
    private int id;
    private boolean comida = false;
    private int tunel;
    private boolean siendoAtacado = false;
    private Random random = new Random();
    private boolean herido = false;
    private static final Logger log = LogConfig.getLogger();

    private boolean vivo = true;

    //Constantes para los tiempos
    private long TIEMPO_ZONA_COMUN = 5000;
    private long TIEMPO_ZONA_RIESGO = 3000;
    private long TIEMPO_ZONA_DESCANSO = 2000;

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

    public boolean isComida() {
        return comida;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public boolean isHerido() {
        return herido;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    private void convertirseEnZombie(int tunel) {
        vivo = false;  // Marca al humano como muerto
        juego.sacarZonaRiesgoIzq(this, tunel); // asegurarse de limpiar
        log.warning("Humano " + this.getName() + " -> Muerto");
        Zombie z = new Zombie(juego, id);
        z.start();
    }

    private void dormir(long tiempo){
        try{
            sleep(tiempo);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        while (vivo && !Thread.currentThread().isInterrupted()) {  // Verificación de que el humano esté vivo
            try {
                // Zona común (1-2 segundos)
                juego.meterZonaComun(this);
                dormir((long) (TIEMPO_ZONA_COMUN + random.nextDouble()*2000));
                juego.sacarZonaComun(this);

                // Selección de túnel
                tunel = random.nextInt(4);

                // Atravesar túnel

                // Zona exterior (3-5 segundos)
                juego.meterZonaRiesgoIzq(this, tunel);
                dormir((long) (TIEMPO_ZONA_RIESGO+ random.nextDouble()*2000));

                while (siendoAtacado && vivo){
                    dormir(500);
                    if(Thread.currentThread().isInterrupted()){
                        convertirseEnZombie(tunel);
                        return;
                    }
                }
                juego.sacarZonaRiesgoIzq(this, tunel);

                // Depositar comida

                if (comida){
                    ////////////////////////////////////////////////////////////////
                    //            AÑADIR COMIDA A LA PILA DE COMIDA               //
                    ////////////////////////////////////////////////////////////////
                    // Hay que meter los controllers como atributo de los humanos para poder modificar directamente ese número ??
                    comida = false;
                }
                // Zona de descanso
                if (herido) {
                    juego.meterZonaDescanso(this);
                    dormir((long) (TIEMPO_ZONA_DESCANSO + random.nextDouble()*2000));
                    this.herido = false;
                    juego.sacarZonaDescanso(this);
                }

                // Comedor


            } catch (Exception e) {
                // Manejo de excepciones generales
                if (!vivo || Thread.currentThread().isInterrupted()) {
                    convertirseEnZombie(tunel);
                    return;
                }
            }
        }
    }
}

