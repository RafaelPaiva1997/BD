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

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, numero_aluno, curso",pessoa.getId() + "," + numero_aluno + ",'" + curso + "'");
    }
}
