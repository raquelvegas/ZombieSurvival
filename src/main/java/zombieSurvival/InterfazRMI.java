package zombieSurvival;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfazRMI extends Remote {
    Integer humanosEnRefugio() throws RemoteException;
    Integer humanosTunel1() throws RemoteException;
    Integer humanosTunel2() throws RemoteException;
    Integer humanosTunel3() throws RemoteException;
    Integer humanosTunel4() throws RemoteException;
    Integer humanosRiesgo1() throws RemoteException;
    Integer humanosRiesgo2() throws RemoteException;
    Integer humanosRiesgo3() throws RemoteException;
    Integer humanosRiesgo4() throws RemoteException;
    Integer zombisRiesgo1() throws RemoteException;
    Integer zombisRiesgo2() throws RemoteException;
    Integer zombisRiesgo3() throws RemoteException;
    Integer zombisRiesgo4() throws RemoteException;
    ArrayList<String> nombresZombiesLetales() throws RemoteException;
    ArrayList<Integer> muertesZombiesLetales() throws RemoteException;
    void play_pause() throws RemoteException;
}
