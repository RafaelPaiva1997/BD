package votingterminal;

import models.MesadeVoto;
import models.Model;
import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;
import rmi.RMIInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

public class VotingTerminal {

    private static RMIInterface rmi;
    private static Scanner sc;
    private static Departamento departamento;
    private static MesadeVoto mesadeVoto;
    private static Pessoa pessoa;
    private static Eleicao eleicao;
    private static Model model;
    private static String id;
    private static String[] listas;

    public static void getProperty(String s1, BooleanSupplier call) {
        while (call.getAsBoolean())
            System.out.print(s1);
    }

    public static void getProperty(String s1, String s2, BooleanSupplier call) {
        System.out.print(s1);
        getProperty(s2, () -> call.getAsBoolean());
    }

    public static Model escolheID(String table, String show) throws RemoteException {
        if (rmi.query(table, "(ID)", "").equals("empty")) {
            System.out.print("Não existem " + table.toLowerCase() + ", por favor insira uma!");
            return null;
        }

        getProperty(rmi.query(table, "*", "") + "Insira o ID d" + show + ": ",
                "Por favor insira um ID válido!",
                () -> {
                    try {
                        return (model = rmi.get(table, "ID = " + sc.nextInt())) == null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        return true;
                    }
                });

        return model;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                rmi = (RMIInterface) LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1])).lookup("rmi-object");
                sc = new Scanner(System.in);
                pessoa = new Pessoa();

                if ((departamento = (Departamento) escolheID("Departamentos", "o departamento onde está a votar")) == null)
                    return;

                mesadeVoto = (MesadeVoto) rmi.get("Mesas_Voto", "departamento_id = " + departamento.getId());

                sc.nextLine();

                do {
                    getProperty("Insira o Username: ",
                            "Por favor insira um username com entre 8 a 20 caracteres.\n",
                            () -> !pessoa.setUsername(sc.nextLine()));

                    getProperty("Insira a Password: ",
                            "Por favor insira uma password entre 8 a 20 caracteres.\n",
                            () -> !pessoa.setPassword(sc.nextLine()));
                } while ((pessoa = (Pessoa) rmi.get("Pessoas", "username = '" + pessoa.getUsername() + "' && password = '" + pessoa.getPassword() + "'")) == null);

                do {
                    if ((eleicao = (Eleicao) escolheID("Eleicoes", "a eleição na qual pretende votar")) == null)
                        return;
                } while(rmi.query("Votos", "(ID)", "WHERE eleicao_id = " + eleicao.getId() + " && pessoa_id = " + pessoa.getId()).equals("not empty") || !pessoa.check(eleicao));

                sc.nextLine();

                do {
                    if ((listas = rmi.votar(eleicao, pessoa)) == new String[0]) {
                        System.out.print("Erro ao votar\n");
                        return;
                    }
                    System.out.print(listas[listas.length-1]);
                } while ((id = rmi.votar(Arrays.copyOf(listas, listas.length-1), sc.nextLine())).equals("fail"));

                System.out.print(rmi.votar(id, eleicao, pessoa, mesadeVoto, new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
