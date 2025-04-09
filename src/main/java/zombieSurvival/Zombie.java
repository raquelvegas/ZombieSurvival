package zombieSurvival;

import java.util.Random;

public class Zombie extends Thread {
    private int id;
    private Juego juego;
    private Random random = new Random();
    private int contadorMuertes = 0;

    public Zombie(Juego juego, int id) {
        this.juego = juego;
        this.id = id;
        String StringCeros = String.format("%04d", id);
        super.setName("Z" + StringCeros);
    }

    private void atacar(Humano h) {

    }

    public void run(){
        while(true){
            // Movimiento aleatorio entre las zonas de riesgo
            int zona = random.nextInt(4);
            juego.meterZonaRiesgoDch(this, zona);

            // Comprobación de si hay humanos en la zona de riesgo
            int numHumanos = juego.getRiesgoIzq().get(zona).getSize();
            if (numHumanos != 0) {
                // Ataque
                int humano = random.nextInt(numHumanos);
                atacar((Humano) juego.getRiesgoIzq().get(zona).get(humano));
            }

            // Sacamos el zombi de la zona de riesgo en la que esté
            juego.sacarZonaRiesgoDch(this, zona);
        }
    }
}
