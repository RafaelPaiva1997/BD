package models.pessoas;

import java.io.Serializable;
import models.Model;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
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
        this.updateNew = updateNew;
        switch (updateType) {
            case "curso":
                flag = setCargo(updateNew);
        }
        return flag;
    }


    @Override
    public String sqlInsert() {
        try {
            RMIServer.rmi.insert(pessoa);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return sqlInsert("pessoa_id, cargo",pessoa.getId() + "," + cargo + "'");
    }
}
