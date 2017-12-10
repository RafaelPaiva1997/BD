package models.eleicoes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NucleoEstudantes extends Eleicao {

    private int departamento_id;

    public NucleoEstudantes() {
    }

    public NucleoEstudantes(ResultSet resultSet) {
        super(resultSet);
        try {
            departamento_id = resultSet.getInt("departamento_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("tipo, titulo, descricao, data_inicio, data_fim, departamento_id", "'" + tipo + "','" + titulo + "','" + descricao + "'," + dateToSqlDate(data_inicio) + "," + dateToSqlDate(data_fim) + "," + departamento_id);
    }

    @Override
    public String toString() {
        return super.toString() + " ID_Departamento: " + departamento_id + "\n";
    }

    @Override
    public String print() {
        return super.print() + "ID_Departamento: " + departamento_id + "\n";
    }
}
