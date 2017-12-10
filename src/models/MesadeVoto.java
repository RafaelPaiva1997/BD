package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MesadeVoto extends Model implements Serializable {

    private int departamento_id;
    private boolean working;

    public MesadeVoto() {
        super();
        table = "Mesa_Votos";
        working = false;
    }

    public MesadeVoto(int departamento_id) {
        super();
        table = "Mesa_Votos";
        this.departamento_id = departamento_id;
        working = false;
    }

    public MesadeVoto(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Mesa_Votos";
            departamento_id = resultSet.getInt("departamento_id");
            working = resultSet.getBoolean("working");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String sqlInsert() {
        return sqlInsert("departamento_id, working", departamento_id + "," + (working ? 1 : 0));
    }

    @Override
    public String toString() {
        return "MESA DE VOTO ID: " + id + " ID_Departamento: " + departamento_id + " Working: " + working + "\n";
    }
}
