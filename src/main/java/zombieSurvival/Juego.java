package zombieSurvival;

import java.util.ArrayList;

public class Juego {
    private ListaHilos zonaComun;
    private ArrayList<ListaHilos> esperaTuneles;
    private ArrayList<ListaHilos> riesgoIzq;
    private ArrayList<ListaHilos> riesgoDch;

    public Juego(ListaHilos zonaComun, ArrayList<ListaHilos> esperaTuneles, ArrayList<ListaHilos> riesgoIzq, ArrayList<ListaHilos> riesgoDch) {
        this.zonaComun = zonaComun;
        this.esperaTuneles = esperaTuneles;
        this.riesgoIzq = riesgoIzq;
        this.riesgoDch = riesgoDch;
    }

    public void meterZonaComun(Humano i){
        zonaComun.meter(i);
    }

    public void sacarZonaComun(Humano i){
        zonaComun.sacar(i);
    }

    public void meterZonaRiesgoDch(Zombie z, int zona) {
        riesgoDch.get(zona).meter(z);
    }
    public void sacarZonaRiesgoDch(Zombie z, int zona) {
        riesgoDch.get(zona).sacar(z);
    }

    public void esperarTunel(Humano i, int tunel){
        esperaTuneles.get(tunel).meter(i);
    }

    public ArrayList<ListaHilos> getRiesgoIzq() {
        return riesgoIzq;
    }
}
