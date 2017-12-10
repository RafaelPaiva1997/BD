package adminconsole.gestores;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;
import static adminconsole.AdminConsole.remove;

/**
 * Created by Johny on 10/12/2017.
 */
public class MesadeVoto {

    public static void menu() {
        gerir("MENU MESAS DE VOTO\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Remover\n" +
                        "3 - Listar\n" +
                        "4 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3},
                new BooleanSupplier[]{
                        () -> {
                            try {
                                addMesadeVoto();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                removeMesasdeVoto();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },

                        () -> {
                            try {
                                listMesasdeVoto();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }

    public static void addMesadeVoto() throws RemoteException {
        add("MesasdeVoto", "Eleicaos", "a eleicao à qual pretende adicionar uma mesa de voto", "a eleicao a adicionar");
    }

    public static void listMesasdeVoto() throws RemoteException {
        if ((mesadeVoto = (models.MesadeVoto) escolheID("MesasdeVoto", "a eleicao sobre a qual quer ver as mesas de voto")) == null)
            return;

        printConnections("MesasdeVoto", "Eleicaos", mesadeVoto.getId());
    }

    public static void removeMesasdeVoto() throws RemoteException {
        remove("MesasdeVoto", "Eleicaos", "a mesa de voto à qual pretende adicionar uma eleicao", "a mesa de voto a adicionar");
    }
}
