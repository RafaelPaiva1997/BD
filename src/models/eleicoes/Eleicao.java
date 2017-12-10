package models.eleicoes;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Eleicao extends Model implements Serializable {

    protected String tipo;
    protected String titulo;
    protected String descricao;
    protected Date data_inicio;
    protected Date data_fim;
    protected int departamento_id;
    protected boolean finished;

    public Eleicao() {
        super();
        table = "Eleicaos";
        finished = false;
    }

    public Eleicao(ResultSet resultSet) {
        super(resultSet);
        try {
            table = "Eleicaos";
            finished = resultSet.getBoolean("finished");
            tipo = resultSet.getString("tipo");
            titulo = resultSet.getString("titulo");
            descricao = resultSet.getString("descricao");
            data_inicio = new Date(resultSet.getTimestamp("data_inicio").getTime());
            data_fim = new Date(resultSet.getTimestamp("data_fim").getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public int getDepartamento_id() {
        return departamento_id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }

    public boolean setTitulo(String titulo) {
        boolean flag = true;
        if (lenghtMaior(titulo, 0) &&
                isAlpha(titulo))
            this.titulo = titulo;
        else
            flag = false;
        return flag;
    }

    public boolean setDescricao(String descricao) {
        boolean flag = true;
        if (lenghtMaior(descricao, 0) &&
                isAlpha(descricao))
            this.descricao = descricao;
        else
            flag = false;
        return flag;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = updateNew;
        switch (updateType) {
            case "titulo":
                flag = setTitulo(updateNew);
                this.updateNew = "'" + updateNew + "'";
                break;
            case "descricao":
                flag = setDescricao(updateNew);
                this.updateNew = "'" + updateNew + "'";
                break;
            case "data_inicio":
                this.updateNew = "'" + updateNew + "'";
            case "data_fim":
                this.updateNew = "'" + updateNew + "'";
                flag = true;
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("finished, tipo, titulo, descricao, data_inicio, data_fim", (finished ? 1 : 0) + ",'" + tipo + "','" + titulo + "','" + descricao + "'," + dateToSqlDateTime(data_inicio) + "," + dateToSqlDateTime(data_fim));
    }

    @Override
    public String toString() {
        return tipo.toUpperCase() + " ID: " + id + " Título: " + titulo;
    }

    public String print() {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return tipo.toUpperCase() + "\n" +
                "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Data Início: " + f.format(data_inicio) + "\n" +
                "Data Fim: " + f.format(data_fim) + "\n";
    }

    public boolean checkDates() {
        return data_inicio.before(data_fim);
    }

    public String getTipo() {
        return tipo;
    }
}
