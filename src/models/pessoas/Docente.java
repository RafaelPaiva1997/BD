package models.pessoas;

import java.io.Serializable;

import models.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Docente extends Model implements Serializable {

    private int pessoa_id;
    private String cargo;

    public Docente() {
        super();
        table = "Docentes";
    }

    public Docente(int pessoa_id) {
        super();
        table = "Alunos";
        this.pessoa_id = pessoa_id;
    }

    public Docente(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Docentes";
            pessoa_id = resultSet.getInt("pessoa_id");
            cargo = resultSet.getString("cargo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getCargo() {
        return cargo;
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

    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = "'" + updateNew + "'";
        switch (updateType) {
            case "curso":
                flag = setCargo(updateNew);
                this.updateNew = "'" + updateNew + "'";
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("pessoa_id, cargo", pessoa_id + "," + cargo + "'");
    }

    public String print() {
        return "Cargo: " + cargo + "\n";
    }
}
