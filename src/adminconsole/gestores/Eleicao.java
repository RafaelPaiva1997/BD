package adminconsole.gestores;



import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;
import java.text.SimpleDateFormat;


import static adminconsole.AdminConsole.*;


public class Eleicao {
    public static void menu(){
        gerir("MENU ELEICOES\n" +
                        "O que pretende fazer?\n" +
                        "1 - Adicionar\n" +
                        "2 - Editar\n" +
                        "3 - Remover\n" +
                        "4 - Listar\n" +
                        "5 - Voltar\n",
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
                                delete();
                                return true;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                });

    }

    public static void insert() throws RemoteException {
        eleicao = new models.eleicoes.Eleicao();

        sc.nextLine();

        getProperty(
                "Escolha o tipo de eleicao a inserir:\n" +
                        "1 - Conselho Geral\n" +
                        "2 - Nucleo de Estudantes\n",
                "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                () -> !contains(new int[]{1, 2}, r1 = sc.nextInt()));

        sc.nextLine();

        if (r1 == 1)
            eleicao.setTipo("concelho geral");
        else if (r1 == 2)
            eleicao.setTipo("nucleo de estudantes");

        getProperty("Insira o Titulo: ",
                "Por favor insira um nome só com letras!\n",
                () -> !eleicao.setTitulo(sc.nextLine()));

        getProperty("Insira a Descricao: ",
                "Por favor insira um username com entre 8 a 20 caracteres que não esteja em uso.\n",
                () -> !eleicao.setDescricao(sc.nextLine()));

        eleicao.setData_inicio(Data.editData("a data de inicio", new models.Data()).export());
        eleicao.setData_fim(Data.editData("a data de fim", new models.Data()).export());

        rmi.insert(eleicao);
    }

    public static void update() throws RemoteException {
        if (rmi.query("Eleicoes", "(ID)", "").equals("empty")) {
            System.out.print("Não existem eleicoes, por favor insira uma!");
            return;
        }

        getProperty(rmi.query("Eleicoes", "*", "") + "Insira o ID da eleicao a editar: ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (eleicao = (models.eleicoes.Eleicao) rmi.get("Eleicoes", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        getProperty("Escolha a propriedade a editar:\n" +
                        "Titulo\n" +
                        "Descricao\n" +
                        "Data de inicio" +
                        "Data de fim",
                "Por favor insira um número correspondente a uma das propriedades disponíveis.\n",
                () -> !contains(new String[]{"titulo", "descricao", "data de inicio", "data de fim"},
                        (r2 = sc.nextLine())));

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
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
                System.out.println("Data de Inicio: " + f.format(eleicao.getData_inicio()));
                eleicao.setData_inicio(Data.editData("a data de inicio", new models.Data(eleicao.getData_inicio())).export());
                eleicao.update("data_inicio", "'" + f1.format(eleicao.getData_inicio()) + "'");
                rmi.update(eleicao);
                break;
            case "data de fim":
                System.out.println("Data de Fim: " + f.format(eleicao.getData_fim()));
                eleicao.setData_fim(Data.editData("a data de fim", new models.Data(eleicao.getData_fim())).export());
                eleicao.update("data_fim", "'" + f1.format(eleicao.getData_fim()) + "'");
                rmi.update(eleicao);
                break;
        }
    }

    public static void delete() throws RemoteException {
        if (rmi.query("Eleicoes", "(ID)", "").equals("empty")) {
            System.out.print("Não existem eleicoes, por favor insira uma!");
            return;
        }
        getProperty(rmi.query("Eleicoes", "*", "") + "Insira o ID da eleicao a remover: ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (eleicao = (models.eleicoes.Eleicao) rmi.get("Eleicoes", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        rmi.delete(eleicao);
    }

    public static void print() throws RemoteException {
        if (rmi.query("Eleicoes", "(ID)", "").equals("empty")) {
            System.out.print("Não existem eleicoes, por favor insira uma!");
            return;
        }

        getProperty(rmi.query("Eleicoes", "*", "") ,
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (eleicao = (models.eleicoes.Eleicao) rmi.get("Eleicoes", "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        System.out.print(eleicao.print());

        sc.nextLine();
        sc.nextLine();
    }
}


