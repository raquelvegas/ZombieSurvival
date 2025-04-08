package zombieSurvival;

import java.util.ArrayList;

public class Juego {
    private ListaHilos ZonaComun;
    private ArrayList<ListaHilos> EsperaTuneles;
    private Tunel tunel0 = new Tunel();
    private Tunel tunel1 = new Tunel();
    private Tunel tunel2 = new Tunel();
    private Tunel tunel3 = new Tunel();



    public Juego(ListaHilos zonaComun, ArrayList<ListaHilos> esperaTuneles) {
        ZonaComun = zonaComun;
        EsperaTuneles = esperaTuneles;
    }

    public void meterZonaComun(Individuo i){
        ZonaComun.meter(i);
    }

    public void sacarZonaComun(Individuo i){
        ZonaComun.sacar(i);
    }

    public void esperarTunel(Individuo i, int tunel){
        EsperaTuneles.get(tunel).meter(i);
    }

}
