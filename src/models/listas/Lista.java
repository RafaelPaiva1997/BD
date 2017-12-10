package models.listas;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lista extends Model implements Serializable {

    private String tipo;
    private String nome;
    private int eleicao_id;

    public Lista()  {
        super();
        table = "Listas";
    }

    public Lista(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Listas";
            tipo = resultSet.getString("tipo");
            nome = resultSet.getString("nome");
            eleicao_id = resultSet.getInt("eleicao_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setNome(String nome) {
        boolean flag = true;
        if (lenghtMaior(nome, 0) &&
                isAlpha(nome))
            this.nome = nome;
        else
            flag = false;
        return flag;
    }

    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = updateNew;
        switch (updateType) {
            case "nome":
                flag =  setNome(updateNew);
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("tipo, nome, eleicao_id", "'" + tipo + "','" + nome + "'," + eleicao_id);
    }

    @Override
    public String toString() {
        return "LISTA " + tipo.toUpperCase() + " Nome: " + nome + " ID_Eleição: " + eleicao_id;
    }
}
