package models.pessoas;

import models.Model;

import java.io.Serializable;
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

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, numero_aluno, curso",pessoa.getId() + "," + numero_aluno + ",'" + curso + "'");
    }
}
