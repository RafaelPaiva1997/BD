package adminconsole.gestores;

import models.pessoas.Aluno;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;
import java.text.SimpleDateFormat;
import java.util.function.BooleanSupplier;

import static adminconsole.AdminConsole.*;
import static adminconsole.AdminConsole.faculdade;

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

        getProperty("Insira o Username: ",
                "Por favor insira um username com entre 8 a 20 caracteres que não esteja em uso.\n",
                () -> !eleicao.setDescricao(sc.nextLine()));

        eleicao.setData_inicio(Data.editData("a data de inicio", new models.Data()).export());
        eleicao.setData_fim(Data.editData("a data de fim", new models.Data()).export());

        rmi.insert(eleicao);
    }

    public static void update() throws RemoteException {
        if (rmi.query("Pessoas", "(ID)", "").equals("empty")) {
            System.out.print("Não existem pessoas, por favor insira uma!");
            return;
        }
}
