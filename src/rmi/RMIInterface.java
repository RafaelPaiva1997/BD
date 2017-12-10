package rmi;

import models.Lista;
import models.MesadeVoto;
import models.Model;
import models.Voto;
import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

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

    int queryInt(String table, String query, String query2) throws RemoteException;

    String[] votar(Eleicao eleicao, Pessoa pessoa) throws RemoteException;

    String votar(String[] listas, String input) throws RemoteException;

    boolean votar(String id, Eleicao eleicao, Pessoa pessoa, MesadeVoto mesadeVoto, Date date) throws RemoteException;

    Eleicao[] getEleicoes(String query) throws RemoteException;

    MesadeVoto[] getMesasVoto(String query) throws RemoteException;

    Lista[] getListas(String query) throws RemoteException;

    Voto[] getVotos(String query) throws RemoteException;
}
