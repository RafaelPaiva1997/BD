package adminconsole.gestores;

import models.MesadeVoto;
import models.organizacoes.Faculdade;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;

public class Departamento {

    public static void menu() {
        gerir("MENU DEPARTAMENTOS\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Listar Pessoas\n" +
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
                                System.out.print(rmi.query("Departamentos", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                System.out.print(rmi.query("Departamento_Pessoas", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });
    }

    public static void insert() throws RemoteException {

        getProperty(rmi.query("Faculdades","*", "") + "Insira o ID da faculdade à qual pretende adicionar um departamento: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (faculdade = (Faculdade) rmi.get("Faculdades", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        sc.nextLine();

        departamento = new models.organizacoes.Departamento();
        departamento.setFaculdade_id(faculdade.getId());

        getProperty("Insira o Nome: ",
                "Por favor insira um nome só com letras!\n",
                () -> !departamento.setNome(sc.nextLine()));

        rmi.insert(departamento);
        rmi.insert(new MesadeVoto(rmi.get("Departamentos", "nome = '" + departamento.getNome() + "'").getId()));
    }

    public static void update() throws RemoteException {
        if (rmi.query("Departamentos", "(ID)", "").equals("empty")) {
            System.out.print("Não existem departamentos, por favor insira um!");
            return;
        }

        getProperty(rmi.query("Departamentos", "*", "") + "Insira o ID do departamento a remover: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (departamento = (models.organizacoes.Departamento) rmi.get("Departamentos", "ID = " + sc.nextInt())) == null;
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
                        () -> !departamento.update("nome", editProperty("Nome", departamento.getNome())));
                break;
        }

        rmi.update(departamento);
    }

    public static void delete() throws RemoteException {
        getProperty(rmi.query("Departamentos", "*", "") + "Insira o ID do departamento a remover: ",
                "Por favor insira um ID válido!\n",
                () -> {
                    try {
                        return (departamento = (models.organizacoes.Departamento) rmi.get("Departamentos", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        rmi.delete(departamento);
        rmi.delete(rmi.get("Mesas_Voto", "departamento_id = " + departamento.getId()));
    }
}
