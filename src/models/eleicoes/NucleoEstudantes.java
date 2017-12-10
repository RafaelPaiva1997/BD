package models.eleicoes;

import models.organizacoes.Departamento;

/**
 * Created by Johny on 09/12/2017.
 */
public class NucleoEstudantes extends Eleicao {

    private int departamento_id;

    @Override
    public String sqlInsert() {
        return sqlInsert("tipo, titulo, descricao, data_inicio, data_fim, departamento_id", "'" + tipo + "','" + titulo + "','" + descricao + "'," + dateToSqlDate(data_inicio) + "," + dateToSqlDate(data_fim) + "," + departamento_id);
    }
}
