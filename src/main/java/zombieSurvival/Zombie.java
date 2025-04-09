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
        try {
            sleep((long) (0.5 + random.nextDouble()) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double ataque = random.nextDouble();
        if (ataque > (double) 2/3){ // Ataque exitoso
            h.interrupt();
            System.out.println("El zombie " + this.getName() + " ha matado al humano " + h.getName() + ". P = " + ataque);
        } else { // Ataque fallido
            System.out.println("El zombie " + this.getName() + " ha herido al humano " + h.getName() + ". P = " + ataque);
            h.setHerido(true);
            h.setSiendoAtacado(false);
        }
    }

    public void run(){
        while (true){
            // Movimiento aleatorio entre las zonas de riesgo
            int zona = random.nextInt(4);
            juego.meterZonaRiesgoDch(this, zona);

            // Comprobación de si hay humanos en la zona de riesgo
            int numHumanos = juego.getRiesgoIzq().get(zona).getHumanos().getSize();
            if (numHumanos != 0) {
                // Ataque
                atacar(juego.getRiesgoIzq().get(zona).elegirHumano());
            }
            // Tiempo dormido
            try {
                sleep((long) (2 + random.nextDouble()) * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Sacamos el zombi de la zona de riesgo en la que esté
            juego.sacarZonaRiesgoDch(this, zona);
        }
    }
}
