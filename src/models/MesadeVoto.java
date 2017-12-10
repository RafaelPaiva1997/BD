package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;

public class MesadeVoto extends Model implements Serializable {

    private int departamento_id;
    private boolean working;

    public MesadeVoto() {
        super();
        table = "Mesa_Votos";
        updateType = "working";
        working = false;
    }

    public MesadeVoto(int departamento_id) {
        super();
        table = "Mesa_Votos";
        updateType = "working";
        this.departamento_id = departamento_id;
        working = false;
    }

    public MesadeVoto(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Mesa_Votos";
            updateType = "working";
            departamento_id = resultSet.getInt("departamento_id");
            working = resultSet.getBoolean("working");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
        updateNew = String.valueOf(working ? 1 : 0);
    }

    public String sqlInsert() {
        return sqlInsert("departamento_id, working", departamento_id + "," + (working ? 1 : 0));
    }

    @Override
    public String toString() {
        return "MESA DE VOTO ID: " + id + " ID_Departamento: " + departamento_id + " Working: " + working + "\n";
    }
}
