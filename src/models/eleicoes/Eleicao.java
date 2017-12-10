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

    public boolean setTitulo(String titulo) {
        boolean flag = true;
        if (lenghtMaior(titulo, 0) &&
                isAlpha(titulo))
            this.titulo = titulo ;
        else
            flag = false;
        return flag;
    }

    public boolean setDescricao(String descricao) {
        boolean flag = true;
        if (lenghtMaior(descricao, 0) &&
                isAlpha(descricao))
            this.descricao = descricao ;
        else
            flag = false;
        return flag;
    }

    public boolean update(String updateType, String updateNew) {
        boolean flag = false;
        this.updateType = updateType;
        this.updateNew = updateNew;
        switch (updateType) {
            case "titulo":
                flag = setTitulo(updateNew);
                break;
            case "descricao":
                flag = setDescricao(updateNew);
                break;
            case "data_inicio":
            case "data_fim":
                flag = true;
                break;
        }
        return flag;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("tipo, titulo, descricao, data_inicio, data_fim", "'" + tipo + "','" + titulo + "','" + descricao + "'," + dateToSqlDate(data_inicio) + "," + dateToSqlDate(data_fim));
    }

    @Override
    public String toString() {
        return tipo.toUpperCase() + " ID: " + id + " Título: " + titulo;
    }

    public String print() {
        SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
        return tipo.toUpperCase() + "\n" +
                "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Data Início: " + f.format(data_inicio) + "\n" +
                "Data Fim: " + f.format(data_fim) + "\n";
    }
}
