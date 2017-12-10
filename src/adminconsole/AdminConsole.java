package adminconsole;

import adminconsole.gestores.Departamento;
import adminconsole.gestores.Faculdade;
import adminconsole.gestores.Pessoa;
import adminconsole.gestores.Eleicao;
import rmi.RMIInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

public class AdminConsole {

    public static Scanner sc;
    public static RMIInterface rmi;
    public static int r1;
    public static String r2;
    public static models.organizacoes.Faculdade faculdade;
    public static models.organizacoes.Departamento departamento;
    public static models.pessoas.Pessoa pessoa;
    public static models.pessoas.Aluno aluno;
    public static models.pessoas.Docente docente;
    public static models.pessoas.Funcionario funcionario;
    public static models.eleicoes.Eleicao eleicao;
    public static models.listas.Lista lista;

    public static void getProperty(String s1, BooleanSupplier call) {
        while (call.getAsBoolean())
            System.out.print(s1);
    }

    public static void getProperty(String s1, String s2, BooleanSupplier call) {
        System.out.print(s1);
        getProperty(s2, () -> call.getAsBoolean());
    }

    public static String editProperty(String p, String v) {
        System.out.print(p + " Antigo: " + v +
                "\n" + p + " Novo: ");
        return sc.nextLine();
    }

    public static boolean contains(String[] a, String s) {
        return Arrays.toString(a).toLowerCase().contains(s.toLowerCase());
    }

    public static boolean contains(int[] a, int i) {
        return Arrays.toString(a).toLowerCase().contains(String.valueOf(i).toLowerCase());
    }

    public static boolean gerir(String s1, String s2, int[] a, BooleanSupplier[] c) {
        while(true) {
            getProperty(s1, s2, () -> !contains(a, (r1 = sc.nextInt())));

            if (r1 - 1 == c.length) return true;

            for (int i = 0; i < c.length; i++) {
                if (i == r1 - 1) {
                    c[i].getAsBoolean();
                    i = c.length;
                }
            }
        }
    }
    public static void gerirMenu(){
        gerir("MENU PRINCIPAL\n" +
                        "O que pretende fazer?\n" +
                        "1 - Gerir Base de Dados\n" +
                        "2 - Sair\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2},
                new BooleanSupplier[]{
                        () -> {
                            gerirBaseDados();
                            return true;
                        },
                });
    }

    private static void gerirBaseDados() {
        gerir("GERIR BASE DE DADOS\n" +
                        "O que pretende editar?\n" +
                        "1 - Pessoas\n" +
                        "2 - Faculdades\n" +
                        "3 - Departamentos\n" +
                        "4 - Eleicoes\n" +
                        "5 - Voltar\n",
                "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                new int[]{1, 2, 3, 4, 5},
                new BooleanSupplier[]{
                        () -> {
                            Pessoa.menu();
                            return true;
                        },
                        () -> {
                            Faculdade.menu();
                            return true;
                        },
                        () -> {
                            Departamento.menu();
                            return true;
                        },
                        () -> {
                            Eleicao.menu();
                            return true;
                        },

                });
    }



    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                rmi = (RMIInterface) LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])).lookup("rmi-object");
                sc = new Scanner(System.in);
                gerirMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
