package rmi;

import models.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    boolean insert(Model model) throws RemoteException;

    boolean update(Model model) throws RemoteException;

    boolean delete(Model model) throws RemoteException;

    boolean delete(String table, String query) throws RemoteException;

    boolean connect(Model model1, Model model2) throws RemoteException;

    boolean disconnect(Model model1, Model model2) throws RemoteException;

    boolean reset() throws RemoteException;

    Model get(String table, String query) throws RemoteException;

    String query(String table, String query, String query2) throws RemoteException;
}
