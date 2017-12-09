package adminconsole;

import adminconsole.gestores.Departamento;
import adminconsole.gestores.Faculdade;
import adminconsole.gestores.Pessoa;
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

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                rmi = (RMIInterface) LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])).lookup("rmi-object");
                sc = new Scanner(System.in);
                while(true) {
                    gerir("O que pretende fazer?:\n" +
                                    "1 - Adicionar Faculdade\n" +
                                    "2 - Editar Faculdade\n" +
                                    "3 - Remover Faculdade\n" +
                                    "4 - Listar Faculdades\n" +
                                    "5 - Listar Faculdade_Departamentos\n" +
                                    "6 - Adicionar Departamento\n" +
                                    "7 - Editar Departamento\n" +
                                    "8 - Remover Departamento\n" +
                                    "9 - Listar Departamentos\n" +
                                    "10 - Pessoas\n",
                            "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                            new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                            new BooleanSupplier[]{
                                    () -> {
                                        try {
                                            Faculdade.insert();
                                            return true;
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    },
                                    () -> {
                                        try {
                                            Faculdade.update();
                                            return true;
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    },
                                    () -> {
                                        try {
                                            Faculdade.delete();
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
                                    },
                                    () -> {
                                        try {
                                            Departamento.insert();
                                            return true;
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    },
                                    () -> {
                                        try {
                                            Departamento.update();
                                            return true;
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    },
                                    () -> {
                                        try {
                                            Departamento.delete();
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
                                    () -> {Pessoa.menu(); return true;}
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
