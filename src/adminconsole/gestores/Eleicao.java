package adminconsole.gestores;


import models.eleicoes.ConselhoGeral;
import models.eleicoes.NucleoEstudantes;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;
import java.text.SimpleDateFormat;


import static adminconsole.AdminConsole.*;


public class Eleicao {
    public static void menu() {
        gerir("MENU ELEICOES\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Ver Detalhes\n" +
                        "6 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4, 5, 6},
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
                                System.out.print(rmi.query("Eleicoes", "*", ""));
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        },
                        () -> {
                            try {
                                print();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });

    }

    public static void insert() throws RemoteException {
        sc.nextLine();

        getProperty(
                "Escolha o tipo de eleicao a inserir:\n" +
                        "1 - Conselho Geral\n" +
                        "2 - Nucleo de Estudantes\n",
                "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                () -> !contains(new int[]{1, 2}, r1 = sc.nextInt()));

        if (r1 == 1)
            eleicao = new ConselhoGeral();
        else {
            eleicao = new NucleoEstudantes();

            if ((departamento = (models.organizacoes.Departamento) escolheID("Departamentos", "o departamento do nucleo de estudantes")) == null)
                return;

            eleicao.setDepartamento_id(departamento.getId());


            sc.nextLine();
        }

        getProperty("Insira o Titulo: ",
                "Por favor insira um título só com letras!\n",
                () -> !eleicao.setTitulo(sc.nextLine()));

        getProperty("Insira a Descricao: ",
                "Por favor insira uma descrição só com letras!\n",
                () -> !eleicao.setDescricao(sc.nextLine()));
        do {
            eleicao.setData_inicio(Data.edit("a data de inicio", new models.Data()).export());
            eleicao.setData_fim(Data.edit("a data de fim", new models.Data()).export());
            if (!eleicao.checkDates())
                System.out.print("A data de ínicio não está antes da data de fim!\n");
        } while (!eleicao.checkDates());

        rmi.insert(eleicao);
    }

    public static void update() throws RemoteException {
        if ((eleicao = (models.eleicoes.Eleicao) escolheID("Eleicoes", "a eleicao a editar")) == null)
            return;

        sc.nextLine();

        getProperty("Escolha a propriedade a editar:\n" +
                        "Titulo - Descricao\n" +
                        "Data de inicio - Data de fim\n",
                "Por favor insira um número correspondente a uma das propriedades disponíveis.\n",
                () -> !contains(new String[]{
                                "título",
                                "titulo",
                                "descrição",
                                "descricão",
                                "descriçao",
                                "descricao",
                                "data de inicío",
                                "data de inicio",
                                "data de fim",
                        },
                        (r2 = sc.nextLine())));

        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        switch (r2.toLowerCase()) {
            case "titulo":
                getProperty("Por favor insira um titulo só com letras!\n",
                        () -> !eleicao.update("titulo", editProperty("Titulo", eleicao.getTitulo())));
                rmi.update(eleicao);
                break;

            case "descricao":
                getProperty("Por favor insira um descricao só com letras!\n",
                        () -> !eleicao.update("descricao", editProperty("Descricao", eleicao.getDescricao())));
                rmi.update(eleicao);
                break;

            case "data de inicio":
                do {
                    System.out.println("Data de Inicio: " + f.format(eleicao.getData_inicio()));
                    eleicao.setData_inicio(Data.edit("a data de inicio", new models.Data(eleicao.getData_inicio())).export());
                    if (!eleicao.checkDates())
                        System.out.print("A data de ínicio não está antes da data de fim!\n");
                } while (!eleicao.checkDates());
                rmi.update(eleicao);
                break;

            case "data de fim":
                do {
                    System.out.println("Data de Fim: " + f.format(eleicao.getData_fim()));
                    eleicao.setData_fim(Data.edit("a data de fim", new models.Data(eleicao.getData_fim())).export());
                    if (!eleicao.checkDates())
                        System.out.print("A data de ínicio não está antes da data de fim!\n");
                } while (!eleicao.checkDates());
                eleicao.update("data_fim", "'" + f1.format(eleicao.getData_fim()) + "'");
                rmi.update(eleicao);
                break;
        }
    }

    public static void delete() throws RemoteException {
        if ((eleicao = (models.eleicoes.Eleicao) escolheID("Eleicoes", "a eleicao a remover")) == null)
            return;

        rmi.delete(eleicao);
    }

    public static void print() throws RemoteException {
        if ((eleicao = (models.eleicoes.Eleicao) escolheID("Eleicoes", "a eleicao a inspecionar")) == null)
            return;

        System.out.print(eleicao.print());

        sc.nextLine();
        sc.nextLine();
    }
}


