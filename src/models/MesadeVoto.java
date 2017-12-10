package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MesadeVoto extends Model implements Serializable {

    private int departamento_id;
    private boolean working;

    public MesadeVoto() {
        super();
        table = "Mesas_Voto";
        working = false;
    }

    public MesadeVoto(int departamento_id) {
        super();
        table = "Mesas_Voto";
        this.departamento_id = departamento_id;
        working = false;
    }

    public MesadeVoto(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Mesas_Voto";
            departamento_id = resultSet.getInt("departamento_id");
            working = resultSet.getBoolean("working");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String sqlInsert() {
        return sqlInsert("departamento_id, working", departamento_id + "," + (working ? 1 : 0));
    }
}
