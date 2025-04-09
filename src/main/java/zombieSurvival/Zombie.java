package zombieSurvival;

public class Zombie extends Thread {
    private int id;
    private Juego juego;

    public Zombie(Juego juego, int id) {
        this.juego = juego;
        String StringCeros = String.format("%04d", id);
        super.setName("Z" + StringCeros);
    }

    public void run(){

    }
}
