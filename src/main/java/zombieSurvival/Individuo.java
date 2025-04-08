package zombieSurvival;

import java.util.Random;

public class Individuo extends Thread {
    Juego juego;
    boolean vivo;
    boolean comida = false;
    Random random = new Random();


    public Individuo(Juego juego, boolean vivo, int id) {
        this.juego = juego;
        this.vivo = vivo;
        String StringCeros = String.format("%04d", id);
        if (vivo) {
            super.setName("H" + StringCeros);
        } else {
            super.setName("Z" + StringCeros);
        }
    }

    public void run() {
        //for (int i = 0; i < 2; i++)
        //while (vivo)
            //{
            // Zona común (1-2 segundos)
            juego.meterZonaComun(this);
            try {
                sleep((long) (5 + random.nextDouble()) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            juego.sacarZonaComun(this);

            // Seleccion de túnel
            int tunel = random.nextInt(4);
            juego.esperarTunel(this, tunel);

            // Atravesar túnel

            // Zona exterior (3-5 segundos)


        //}
    }
}
