package adminconsole.gestores;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;

public class Lista {
    public static void menu() {
        gerir("MENU DEPARTAMENTOS\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Adicionar Pessoa\n" +
                        "6 - Listar Pessoas\n" +
                        "7 - Remover Pessoa\n" +
                        "8 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4, 5, 6, 7, 8},
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
                                System.out.print(rmi.query("Listas", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                addPessoas();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                listPessoas();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                removePessoas();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }

    public static void insert() throws RemoteException {
        if ((eleicao = (models.eleicoes.Eleicao) escolheID("Eleicoes", "a eleicao a qual pretende adicionar uma lista")) == null)
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

        if (rmi.query("Lista_Pessoas", "(ID)", "WHERE lista_id = "  + lista.getId()).equals("not empty")) {
            System.out.print("A lista pretendida contêm referências a várias pessoas, pretende eliminá-la na mesma?");
            if (sc.nextLine().toLowerCase().equals("sim")) {
                rmi.delete("Lista_Pessoas", "lista_id = " + lista.getId());
                rmi.delete(lista);
            }
        }
        else rmi.delete(lista);
    }


    public static void addPessoas() throws RemoteException {
        add("Listas", "Pessoas", "a lista à qual pretende adicionar uma pessoa", "a pessoa a adicionar");
    }

    public static void listPessoas() throws RemoteException {
        if ((lista = (models.listas.Lista) escolheID("Listas", "a lista sobre a qual quer ver as pessoas")) == null)
            return;

        printConnections("lista", "pessoa", lista.getId());
    }

    public static void removePessoas() throws RemoteException {
        remove("Listas", "Pessoas", "a lista à qual pretende adicionar uma pessoa", "a pessoa a adicionar");
    }
}
