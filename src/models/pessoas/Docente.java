package models.pessoas;

import java.io.Serializable;
import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by Johny on 08/12/2017.
 */
public class Docente extends Model implements Serializable {
    private String cargo;
    private Pessoa pessoa;

    public Docente() {
        super();
        pessoa = new Pessoa();
    }
    public Docente(Pessoa pessoa, ResultSet resultSet) {
        super(resultSet);
        try {
            this.pessoa = pessoa;
            cargo = resultSet.getString("cargo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDocente(){
        return true;
    }

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, cargo",pessoa.getId() + "," + cargo + "'");
    }
}
