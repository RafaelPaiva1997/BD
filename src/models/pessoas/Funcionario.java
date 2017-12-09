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

    public String getFuncao() {
        return funcao;
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


    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = updateNew;
        switch (updateType) {
            case "funcao":
                flag = setFuncao(updateNew);
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return pessoa.sqlInsert() + ";" + sqlInsert("pessoa_id, funcao",pessoa.getId() + "," + funcao + "'");
    }

}
