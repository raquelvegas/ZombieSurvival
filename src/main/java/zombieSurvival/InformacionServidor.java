package zombieSurvival;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InformacionServidor extends UnicastRemoteObject implements InterfazRMI {
    public InformacionServidor() throws RemoteException {}

    @Override
    public Integer humanosEnRefugio() throws RemoteException {
        return 20;
    }

    @Override
    public Integer humanosTunel1() throws RemoteException {
        return 1;
    }

    @Override
    public Integer humanosTunel2() throws RemoteException {
        return 2;
    }

    @Override
    public Integer humanosTunel3() throws RemoteException {
        return 3;
    }

    @Override
    public Integer humanosTunel4() throws RemoteException {
        return 4;
    }

    @Override
    public Integer humanosRiesgo1() throws RemoteException {
        return 11;
    }

    @Override
    public Integer humanosRiesgo2() throws RemoteException {
        return 12;
    }

    @Override
    public Integer humanosRiesgo3() throws RemoteException {
        return 13;
    }

    @Override
    public Integer humanosRiesgo4() throws RemoteException {
        return 14;
    }

    @Override
    public Integer zombisRiesgo1() throws RemoteException {
        return 111;
    }

    @Override
    public Integer zombisRiesgo2() throws RemoteException {
        return 112;
    }

    @Override
    public Integer zombisRiesgo3() throws RemoteException {
        return 113;
    }

    @Override
    public Integer zombisRiesgo4() throws RemoteException {
        return 114;
    }

    @Override
    public ArrayList<Zombie> zombiesLetales() throws RemoteException {
        return null;
    }
}
