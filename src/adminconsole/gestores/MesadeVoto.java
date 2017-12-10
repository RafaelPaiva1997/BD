package adminconsole.gestores;

import models.eleicoes.Eleicao;

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
                        "1 - Adicionar Eleições\n" +
                        "2 - Listar Eleições\n" +
                        "3 - Remover Eleições\n" +
                        "4 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4},
                new BooleanSupplier[]{
                        () -> {
                            try {
                                addEleicao();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                listEleicao();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                removeEleicao();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }

    public static void addEleicao() throws RemoteException {
        if ((mesadeVoto = (models.MesadeVoto) escolheID("Mesa_Votos", "a mesa de voto à qual pretende adicionar uma eleição")) == null)
            return;

        if ((eleicao = (Eleicao) escolheID("Eleicaos", "a eleição a adicionar")) == null)
            return;

        rmi.connect(mesadeVoto, eleicao);
    }

    public static void listEleicao() throws RemoteException {
        if ((mesadeVoto = (models.MesadeVoto) escolheID("Mesa_Votos", "a mesa de voto sobre a qual quer ver as eleições")) == null)
            return;

        printConnections("Mesa_Voto", "Eleicao", mesadeVoto.getId());
    }

    public static void removeEleicao() throws RemoteException {
        escolheModels("Mesa_Votos", "Eleicaos", "a mesa de voto da qual pretende remover uma eleicao", "a eleicao a remover");
    }
}
