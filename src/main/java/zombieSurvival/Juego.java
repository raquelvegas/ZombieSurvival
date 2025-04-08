package zombieSurvival;

import java.util.ArrayList;

public class Juego {
    private ListaHilos ZonaComun;
    private ArrayList<ListaHilos> EsperaTuneles = new ArrayList<>(4);

    public Juego(ListaHilos zonaComun) {
        ZonaComun = zonaComun;
    }

    public void meterZonaComun(Individuo i){
        ZonaComun.meter(i);
    }

    public void esperarTunel(Individuo i, int tunel){
        EsperaTuneles.get(tunel).meter(i);
    }

}
