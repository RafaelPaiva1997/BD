package models.pessoas;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aluno extends Model implements Serializable {

    private int pessoa_id;
    private long numero_aluno;
    private String curso;

    public Aluno() {
        super();
        table = "Alunos";
    }

    public Aluno(int pessoa_id) {
        super();
        table = "Alunos";
        this.pessoa_id = pessoa_id;
    }

    public Aluno(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Alunos";
            pessoa_id = resultSet.getInt("pessoa_id");
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

    public boolean setNumeroAluno(String numeroAluno) {
        boolean flag = true;
        if (lenghtIgual(numeroAluno, 10) &&
                isNumber(numeroAluno))
            this.numero_aluno = Integer.parseInt(numeroAluno);
        else
            flag = false;
        ;
        return flag;

    }

    public boolean setCurso(String curso) {
        boolean flag = true;
        if (lenghtMaior(curso, 0) &&
                isAlpha(curso))
            this.curso = curso;
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
                this.updateNew = "'" + updateNew + "'";
                break;
            case "numero_aluno":
                flag = setNumeroAluno(updateNew);
                this.updateNew = updateNew;
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("pessoa_id, numero_aluno, curso", pessoa_id + "," + numero_aluno + ",'" + curso + "'");
    }

    public String print() {
        return "Nº Aluno: " + numero_aluno + "\n" +
                "Curso: " + curso + "\n";
    }
}
