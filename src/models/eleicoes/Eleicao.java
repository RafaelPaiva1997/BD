package models.eleicoes;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Eleicao extends Model implements Serializable {

    protected String tipo;
    protected String titulo;
    protected String descricao;
    protected Date data_inicio;
    protected Date data_fim;

    public Eleicao() {
        super();
        table = "Eleicoes";
    }

    public Eleicao(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Eleicoes";
            tipo = resultSet.getString("tipo");
            titulo = resultSet.getString("titulo");
            data_inicio = resultSet.getDate("data_inicio");
            data_fim = resultSet.getDate("data_fim");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("tipo, titulo, descricao, data_inicio, data_fim", "'" + tipo + "','" + titulo + "','" + descricao + "'," + dateToSqlDate(data_inicio) + "," + dateToSqlDate(data_fim));
    }
}
