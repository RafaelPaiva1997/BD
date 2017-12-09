package models.pessoas;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by Johny on 08/12/2017.
 */
public class Funcionario extends Model implements Serializable {

    private Pessoa pessoa;
    private String funcao;

    public Funcionario() {
        super();
        pessoa = new Pessoa();
    }

    public Funcionario(Pessoa pessoa) {
        super();
        this.pessoa = pessoa;
    }

    public Funcionario(Pessoa pessoa, ResultSet resultSet) {
        super(resultSet);
        try {
            this.pessoa = pessoa;
            funcao = resultSet.getString("funcao");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setFuncao(String funcao) {
        boolean flag = true;
        if (lenghtMaior(funcao, 0) &&
                isAlpha(funcao))
            this.funcao = funcao;
        else
            flag = false;
        return flag;
    }

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, funcao",pessoa.getId() + "," + funcao + "'");
    }

}
