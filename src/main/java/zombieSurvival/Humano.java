package zombieSurvival;

import java.util.Random;

public class Humano extends Thread {
    private Juego juego;
    private int id;
    private boolean comida = false;
    private boolean siendoAtacado = false;
    private Random random = new Random();
    private boolean herido = false;

    public Humano(Juego juego, int id) {
        this.id = id;
        this.juego = juego;
        String StringCeros = String.format("%04d", id);
        super.setName("H" + StringCeros);
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
