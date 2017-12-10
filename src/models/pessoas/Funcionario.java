package models.pessoas;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funcionario extends Model implements Serializable {

    private int pessoa_id;
    private String funcao;

    public Funcionario() {
        super();
        table = "Funcionarios";
    }

    public Funcionario(int pessoa_id) {
        super();
        table = "Funcionarios";
        this.pessoa_id = pessoa_id;
    }

    public String getFuncao() {
        return funcao;
    }

    public Funcionario(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Alunos";
            pessoa_id = resultSet.getInt("pessoa_id");
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
                this.updateNew = "'" + updateNew + "'";
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("pessoa_id, funcao", pessoa_id + "," + funcao + "'");
    }

    public String print() {
        return "Função: " + funcao + "\n";
    }
}
