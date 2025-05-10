package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.Random;

public class Humano extends Thread {
    private Juego juego;
    private int id;
    private boolean comida = false;
    private int tunel;
    private boolean siendoAtacado = false;
    private Random random = new Random();
    private boolean herido = false;
    private static final LogConfig log = new LogConfig();

    private boolean vivo = true;

    //Constantes para los tiempos
    private long TIEMPO_ZONA_COMUN = 5000;
    private long TIEMPO_ZONA_RIESGO = 3000;
    private long TIEMPO_ZONA_DESCANSO = 2000;

    public Humano(Juego juego, int id) {
        this.id = id;
        this.juego = juego;
        int num = 4;
        String formato = "%0" + num + "d";
        String StringCeros = String.format(formato, id);
        super.setName("H" + StringCeros);
    }

    public boolean isSiendoAtacado() {
        return siendoAtacado;
    }

    public void setSiendoAtacado(boolean siendoAtacado) {
        this.siendoAtacado = siendoAtacado;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    private void convertirseEnZombie(int tunel) {
        vivo = false;  // Marca al humano como muerto
        juego.esperarSiPausado();
        juego.sacarZonaRiesgoIzq(this, tunel); // asegurarse de limpiar
        log.logWarning("Humano " + this.getName() + " -> Muerto");
        juego.esperarSiPausado();
        Zombie z = new Zombie(juego, id);
        juego.nuevoZombie(z);
        z.start();
    }

    public void dormir(long tiempo) throws InterruptedException {
        long dormido = 0;
        long intervalo = 100; // Intervalo de sueño corto (100 ms)

        while (dormido < tiempo) {
            juego.esperarSiPausado();
            long restante = Math.min(intervalo, tiempo - dormido);
            sleep(restante);
            dormido += restante;
        }
    }

    public synchronized void esperarAtaque() {
        System.out.println("Humano " + getName() + " esperando a ser atacado.");
        while (siendoAtacado) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run() {
        while (vivo && !Thread.currentThread().isInterrupted()) {  // Verificación de que el humano esté vivo
            try {
                if (!siendoAtacado) {
                    // Zona común (1-2 segundos)
                    juego.esperarSiPausado();
                    juego.meterZonaComun(this);
                    dormir((long) (TIEMPO_ZONA_COMUN + random.nextDouble() * 2000));
                    juego.esperarSiPausado();

                    // Selección de túnel
                    tunel = random.nextInt(4);

                    // Atravesar túnel
                    juego.cruzarIda(this, tunel);

                    // Zona exterior (3-5 segundos)
                    juego.esperarSiPausado();
                    juego.meterZonaRiesgoIzq(this, tunel);
                    dormir((long) (TIEMPO_ZONA_RIESGO + random.nextDouble() * 2000));
                }
                // Si el humano esta siendo atacado se ejecuta este código
                esperarAtaque();
                if (!vivo) {
                    juego.esperarSiPausado();
                    convertirseEnZombie(tunel);
                    return;
                }

                juego.esperarSiPausado();
                juego.sacarZonaRiesgoIzq(this, tunel);

                // Cruzar tunel

                juego.cruzarVuelta(this, tunel);
                // Depositar comida

                if (comida) {
                    juego.esperarSiPausado();
                    juego.dejarComida();
                    comida = false;
                }

                // Zona de descanso
                juego.esperarSiPausado();
                juego.meterZonaDescanso(this);
                dormir((long) (TIEMPO_ZONA_DESCANSO + random.nextDouble() * 2000));
                juego.esperarSiPausado();
                juego.sacarZonaDescanso(this);

                // Comedor
                juego.esperarSiPausado();
                juego.comer(this);


                // Zona de descanso (solo si heridos)
                if (herido) {
                    juego.esperarSiPausado();
                    juego.meterZonaDescanso(this);
                    dormir((long) (TIEMPO_ZONA_DESCANSO + random.nextDouble() * 2000));
                    this.herido = false;
                    juego.esperarSiPausado();
                    juego.sacarZonaDescanso(this);
                }

            } catch (InterruptedException e) {
                log.logInfo("Humano " + getName() + " interrumpido");
                System.out.println("Humano " + getName() + " interrumpido");
            }
        }
    }
}