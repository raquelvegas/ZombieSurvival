package zombieSurvival;

import java.util.Random;

public class Zombie extends Thread {
    private int id;
    private Juego juego;
    private Random random = new Random();

    public Zombie(Juego juego, int id) {
        this.juego = juego;
        this.id = id;
        String StringCeros = String.format("%04d", id);
        super.setName("Z" + StringCeros);
    }

    public void run(){
        while(true){
            // Movimiento aleatorio entre las zonas de riesgo
            int siguienteZona = random.nextInt(4);

            // Comprobación de si hay humanos en la zona de riesgo

            // Ataque

            // Sacamos el zombi de la zona de riesgo en la que esté
        }
    }
}
