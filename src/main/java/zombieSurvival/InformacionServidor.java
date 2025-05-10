package zombieSurvival;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InformacionServidor extends UnicastRemoteObject implements InterfazRMI {
    Juego juego;

    public InformacionServidor(Juego juego) throws RemoteException {
        this.juego = juego;
    }

    @Override
    public Integer humanosEnRefugio() throws RemoteException {
        Integer humanos = juego.getZonaComun().getSize() + juego.getZonaDescanso().getSize() + juego.getColaComedorTxt().getSize() + juego.getComiendo().getSize();
        return humanos;
    }

    @Override
    public Integer humanosTunel1() throws RemoteException {
        return juego.humanosEnTunel(0);
    }

    @Override
    public Integer humanosTunel2() throws RemoteException {
        return juego.humanosEnTunel(1);
    }

    @Override
    public Integer humanosTunel3() throws RemoteException {
        return juego.humanosEnTunel(2);
    }

    @Override
    public Integer humanosTunel4() throws RemoteException {
        return juego.humanosEnTunel(3);
    }

    @Override
    public Integer humanosRiesgo1() throws RemoteException {
        return juego.humanosEnRiesgo(0);
    }

    @Override
    public Integer humanosRiesgo2() throws RemoteException {
        return juego.humanosEnRiesgo(1);
    }

    @Override
    public Integer humanosRiesgo3() throws RemoteException {
        return juego.humanosEnRiesgo(2);
    }

    @Override
    public Integer humanosRiesgo4() throws RemoteException {
        return juego.humanosEnRiesgo(3);
    }

    @Override
    public Integer zombisRiesgo1() throws RemoteException {
        return juego.zombiesEnRiesgo(0);
    }

    @Override
    public Integer zombisRiesgo2() throws RemoteException {
        return juego.zombiesEnRiesgo(1);
    }

    @Override
    public Integer zombisRiesgo3() throws RemoteException {
        return juego.zombiesEnRiesgo(2);
    }

    @Override
    public Integer zombisRiesgo4() throws RemoteException {
        return juego.zombiesEnRiesgo(3);
    }

    @Override
    public ArrayList<Zombie> zombiesLetales() throws RemoteException {
        return juego.zombiesMortales();
    }
}
