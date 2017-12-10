package adminconsole.gestores;

import models.MesadeVoto;
import models.organizacoes.*;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;
import static adminconsole.AdminConsole.faculdade;

public class Lista {
    public static void menu(){
        gerir("MENU DEPARTAMENTOS\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4, 5},
                new BooleanSupplier[]{
                        () -> {
                            try {
                                insert();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                update();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                delete();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                System.out.print(rmi.query("Departamentos", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }
    public static void insert() throws RemoteException {
        if ((eleicao = (models.eleicoes.Eleicao) escolheID("Eleicoes", "a eleicao a qual quer adicionar uma lista")) == null)
            return;

        sc.nextLine();

        lista = new models.listas.Lista();
        lista.setEleicao_id(eleicao.getId());

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!\n",
                () -> !lista.setNome(sc.nextLine()));

        rmi.insert(lista);
    }
    public static void update() throws RemoteException {
        if ((lista = (models.listas.Lista) escolheID("Listas", "a lista a editar")) == null)
            return;
        sc.nextLine();

        getProperty("Escolha a propriedade a editar:\n" +
                        "Nome\n",
                "Por favor insira um número correspondente a uma das propriedades disponíveis.\n",
                () -> !contains(new String[]{"nome"}, (r2 = sc.nextLine())));


        switch (r2.toLowerCase()) {
            case "nome":
                getProperty("Por favor insira um nome só com letras!\n",
                        () -> !lista.update("nome", editProperty("Nome", lista.getNome())));
                break;
        }

        rmi.update(departamento);
    }

    public static void delete() throws RemoteException {
        if ((lista = (models.listas.Lista) escolheID("Listas", "a lista a remover")) == null)
            return;
        rmi.delete(lista);
    }

}
