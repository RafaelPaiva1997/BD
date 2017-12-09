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

    private Pessoa pessoa;
    private String cargo;

    public Docente() {
        super();
        pessoa = new Pessoa();
    }

    public Docente(Pessoa pessoa) {
        super();
        this.pessoa = pessoa;
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

    public boolean setCargo(String cargo) {
        boolean flag = true;
        if (lenghtMaior(cargo, 0) &&
                isAlpha(cargo))
            this.cargo = cargo;
        else
            flag = false;
        return flag;
    }

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, cargo",pessoa.getId() + "," + cargo + "'");
    }
}
