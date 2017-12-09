package adminconsole.gestores;


import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;

public class Faculdade {

    public static void menu() {
        gerir("MENU FACULDADES\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Listar Departamentos\n" +
                        "6 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4},
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
                                System.out.print(rmi.query("Faculdades", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                System.out.print(rmi.query("Faculdade_Departamentos", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }

    public static void insert() throws RemoteException {
        faculdade = new models.organizacoes.Faculdade();

        sc.nextLine();

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!\n",
                () -> !faculdade.setNome(sc.nextLine()));

        rmi.insert(faculdade);
    }

    public static void update() throws RemoteException {
        getProperty(rmi.query("Faculdades", "*", "") + "Insira o ID da faculdade a editar: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (faculdade = (models.organizacoes.Faculdade) rmi.get("Faculdades", "ID = " + sc.nextInt())) == null;
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
                        () -> !faculdade.update("nome", editProperty("Nome", faculdade.getNome())));
                break;
        }

        rmi.update(faculdade);
    }

    public static void delete() throws RemoteException {
        getProperty(rmi.query("Faculdades", "*", "") + "Insira o ID da faculdade a remover: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (faculdade = (models.organizacoes.Faculdade) rmi.get("Faculdades", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        rmi.delete(faculdade);
    }
}
