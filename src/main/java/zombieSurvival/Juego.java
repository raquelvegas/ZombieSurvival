package zombieSurvival;

import zombieSurvival.configuracionesAdicionales.LogConfig;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Juego {
    private ListaHilos zonaComun;
    private ListaHilos zonaDescanso;
    private ArrayList<ListaHilos> esperaTuneles;
    private ArrayList<ZonaRiesgoH> riesgoIzq;
    private ArrayList<ListaHilos> riesgoDch;
    private static final Logger log = LogConfig.getLogger();


    public Juego(ListaHilos zonaComun, ListaHilos zonaDescanso, ArrayList<ListaHilos> esperaTuneles, ArrayList<ZonaRiesgoH> riesgoIzq, ArrayList<ListaHilos> riesgoDch) {
        this.zonaComun = zonaComun;
        this.zonaDescanso = zonaDescanso;
        this.esperaTuneles = esperaTuneles;
        this.riesgoIzq = riesgoIzq;
        this.riesgoDch = riesgoDch;
    }

    public void meterZonaComun(Humano i){
        zonaComun.meter(i);
        log.info("HUMANO " + i.getName() + " -> Zona Común");
    }

    public void sacarZonaComun(Humano i){
        zonaComun.sacar(i);
    }

    public void meterZonaDescanso(Humano i) {
        zonaDescanso.meter(i);
        log.info("HUMANO " + i.getName() + " -> Zona Descanso");
    }

    public void sacarZonaDescanso(Humano i) {
        zonaDescanso.sacar(i);
    }

    public void meterZonaRiesgoIzq(Humano h, int zona){
        riesgoIzq.get(zona).añadirHumano(h);
    }

    public void sacarZonaRiesgoIzq(Humano h, int zona) {
        riesgoIzq.get(zona).eliminarHumano(h);
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
