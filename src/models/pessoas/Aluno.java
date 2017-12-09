package models.pessoas;

import models.Model;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aluno extends Model implements Serializable {

    private Pessoa pessoa;
    private long numero_aluno;
    private String curso;

    public Aluno() {
        super();
        pessoa = new Pessoa();
    }

    public Aluno(Pessoa pessoa) {
        super();
        this.pessoa = pessoa;
    }

    public Aluno(Pessoa pessoa, ResultSet resultSet) {
        super(resultSet);
        try {
            this.pessoa = pessoa;
            numero_aluno = resultSet.getLong("numero_aluno");
            curso = resultSet.getString("curso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getNumero_aluno() {
        return numero_aluno;
    }

    public String getCurso() {
        return curso;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean setNumeroAluno(String numeroAluno)  {
        boolean flag = true;
        if (lenghtIgual(numeroAluno, 10) &&
                isNumber(numeroAluno))
            this.numero_aluno = Integer.parseInt(numeroAluno);
        else
            flag = false; ;
        return flag;

    }

    public boolean setCurso(String curso) {
        boolean flag = true;
        if (lenghtMaior(curso, 0) &&
                isAlpha(curso))
            this.curso = curso ;
        else
            flag = false;
        return flag;
    }

    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = updateNew;
        switch (updateType) {
            case "curso":
                flag = setCurso(updateNew);
                break;
            case "nº aluno":
            case "no aluno":
                flag = setNumeroAluno(updateNew);
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        try {
            RMIServer.rmi.insert(pessoa);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return sqlInsert("pessoa_id, numero_aluno, curso",pessoa.getId() + "," + numero_aluno + ",'" + curso + "'");
    }
}
