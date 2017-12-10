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
        getProperty(rmi.query("Eleicoes", "*", "") + "Insira o ID da faculdade à qual pretende adicionar uma lista: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (eleicao = (models.eleicoes.Eleicao) rmi.get("Eleicoes", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        sc.nextLine();

        lista = new models.listas.Lista();
        lista.setEleicao_id(eleicao.getId());

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!\n",
                () -> !lista.setNome(sc.nextLine()));

        rmi.insert(lista);
    }
    public static void update() throws RemoteException {
        if (rmi.query("Listas", "(ID)", "").equals("empty")) {
            System.out.print("Não existem listas, por favor insira um!");
            return;
        }

        getProperty(rmi.query("Listas", "*", "") + "Insira o ID da lista\n ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (lista = (models.listas.Lista) rmi.get("Listas", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

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
        getProperty(rmi.query("Listas", "*", "") + "Insira o ID da lista remover: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (lista = (models.listas.Lista) rmi.get("Listas", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        rmi.delete(lista);
    }

}
