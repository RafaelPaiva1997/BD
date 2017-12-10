package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Voto extends Model implements Serializable {

    private int pessoa_id;
    private int eleicao_id;
    private int mesa_voto_id;
    private Date data;

    public Voto() {
        super();
        table = "Votos";
    }

    public Voto(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Votos";
            pessoa_id = resultSet.getInt("pessoa_id");
            eleicao_id = resultSet.getInt("eleicao_id");
            mesa_voto_id =  resultSet.getInt("mesa_voto_id");
            data = new Date(resultSet.getTimestamp("data").getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("(pessoa_id, eleicao_id, mesa_voto_id)", pessoa_id + "," + eleicao_id + "," + mesa_voto_id + "," + dateToSqlDateTime(data));
    }
}
