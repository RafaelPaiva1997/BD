package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Voto extends Model implements Serializable {

    private String tipo;
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
            tipo = resultSet.getString("tipo");
            pessoa_id = resultSet.getInt("pessoa_id");
            eleicao_id = resultSet.getInt("eleicao_id");
            mesa_voto_id =  resultSet.getInt("mesa_voto_id");
            data = new Date(resultSet.getTimestamp("data").getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPessoa_id(int pessoa_id) {
        this.pessoa_id = pessoa_id;
    }

    public void setEleicao_id(int eleicao_id) {
        this.eleicao_id = eleicao_id;
    }

    public void setMesa_voto_id(int mesa_voto_id) {
        this.mesa_voto_id = mesa_voto_id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("(tipo, pessoa_id, eleicao_id, mesa_voto_id)", "'" + tipo + "'," + pessoa_id + "," + eleicao_id + "," + mesa_voto_id + "," + dateToSqlDateTime(data));
    }
}
