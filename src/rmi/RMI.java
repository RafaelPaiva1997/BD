package rmi;

import database.DatabaseHandler;
import models.Model;
import models.eleicoes.ConselhoGeral;
import models.eleicoes.Eleicao;
import models.eleicoes.NucleoEstudantes;
import models.listas.Lista;
import models.organizacoes.Departamento;
import models.organizacoes.Faculdade;
import models.pessoas.Aluno;
import models.pessoas.Docente;
import models.pessoas.Funcionario;
import models.pessoas.Pessoa;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RMI extends UnicastRemoteObject implements RMIInterface {

    private final DatabaseHandler databaseHandler;

    public RMI(DatabaseHandler databaseHandler) throws RemoteException {
        super();
        this.databaseHandler = databaseHandler;
    }

    @Override
    public boolean insert(Model model) throws RemoteException {
        return databaseHandler.execute(model.sqlInsert());
    }

    @Override
    public boolean update(Model model) throws RemoteException {
        return databaseHandler.execute(model.sqlUpdate());
    }

    @Override
    public boolean delete(Model model) throws RemoteException {
        return databaseHandler.execute(model.sqlDelete());
    }

    @Override
    public boolean delete(String table, String query) throws RemoteException {
        return databaseHandler.execute("DELETE FROM " + table + " WHERE " + query);
    }

    @Override
    public boolean connect(Model model1, Model model2) throws RemoteException {
        return databaseHandler.execute(model1.connect(model2));
    }

    @Override
    public boolean disconnect(Model model1, Model model2) throws RemoteException {
        return databaseHandler.execute(model1.disconnect(model2));
    }

    @Override
    public boolean reset() throws RemoteException {
        return databaseHandler.reset();
    }

    @Override
    public Model get(String table, String query) throws RemoteException {
        try {
            ResultSet resultSet = databaseHandler.executeQuery("SELECT * FROM " + table + " WHERE " + query);

            if (resultSet.next()) {
                switch (table.toLowerCase()) {
                    case "faculdades":
                        return new Faculdade(resultSet);
                    case "departamentos":
                        return new Departamento(resultSet);
                    case "pessoas":
                        return new Pessoa(resultSet);
                    case "alunos":
                        return new Aluno(resultSet);
                    case "docentes":
                        return new Docente(resultSet);
                    case "funcionarios":
                        return new Funcionario(resultSet);
                    case "listas":
                        return new Lista(resultSet);
                    case "eleicoes":
                        if (resultSet.getString("tipo").equals("conselho geral"))
                            return new ConselhoGeral(resultSet);
                        else
                            return new NucleoEstudantes(resultSet);
                    default:
                        return null;
                }
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String query(String table, String query, String query2) throws RemoteException {
        StringBuilder s = new StringBuilder();
        try {
            ResultSet resultSet = databaseHandler.executeQuery("SELECT " + query + " FROM " + table + " " + query2);

            if (query.equals("(ID)")) {
                if (!resultSet.next())
                    return "empty";
                else
                    return "not empty";
            }

            switch (table.toLowerCase()) {
                case "faculdades":
                    Faculdade faculdade;
                    s.append("Faculdades: \n");
                    while (resultSet.next()) {
                        faculdade = new Faculdade(resultSet);
                        s.append(faculdade.toString());
                    }
                    break;

                case "departamentos":
                    Departamento departamento;
                    s.append("Departamentos: \n");
                    while (resultSet.next()) {
                        departamento = new Departamento(resultSet);
                        s.append(departamento.toString());
                    }
                    break;

                case "lista_pessoas":
                case "pessoas":
                    Pessoa pessoa;
                    s.append("Pessoas: \n");
                    while (resultSet.next()) {
                        pessoa = new Pessoa(resultSet);
                        s.append(pessoa.toString());
                    }
                    break;

                case "eleicoes":
                    Eleicao eleicao;
                    s.append("Eleições: \n");
                    while (resultSet.next()) {
                        if (resultSet.getString("tipo").equals("conselho geral"))
                            eleicao = new ConselhoGeral(resultSet);
                        else
                            eleicao = new NucleoEstudantes(resultSet);
                        s.append(eleicao.toString());
                    }
                    break;

                case "listas":
                    Lista lista;
                    s.append("Listas: \n");
                    while (resultSet.next()) {
                        lista = new Lista(resultSet);
                        s.append(lista.toString());
                    }
                    break;

                case "faculdade_departamentos":
                    s.append("Faculdade_Departamentos: \n");
                    while (resultSet.next()) {
                        s.append("ID_Faculdade: " + resultSet.getInt("faculdade_id") + " ID_Departamento: " + resultSet.getInt("departamento_id") + "\n");
                    }
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s.append("\n").toString();
    }

    public boolean put(Registry registry) {
        boolean flag = true;
        try {
            registry.rebind("rmi-object", this);
        } catch (RemoteException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
