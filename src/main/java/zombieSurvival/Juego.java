package zombieSurvival;

import java.util.ArrayList;

public class Juego {
    private ListaHilos zonaComun;
    private ArrayList<ListaHilos> esperaTuneles;
    private ArrayList<ZonaRiesgoH> riesgoIzq;
    private ArrayList<ListaHilos> riesgoDch;

    public Juego(ListaHilos zonaComun, ArrayList<ListaHilos> esperaTuneles, ArrayList<ZonaRiesgoH> riesgoIzq, ArrayList<ListaHilos> riesgoDch) {
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

    public void meterZonaRiesgoIzq(Humano h, int zona){
        riesgoIzq.get(zona).getHumanos().meter(h);
    }
    public void sacarZonaRiesgoIzq(Humano h, int zona) {
        riesgoIzq.get(zona).getHumanos().sacar(h);
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

    public ArrayList<ZonaRiesgoH> getRiesgoIzq() {
        return riesgoIzq;
    }
}
