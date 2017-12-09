package models;

import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Johny on 09/12/2017.
 */
public class MesadeVoto extends Model implements Serializable {
    private Departamento departamento;
    private boolean[] logins;
    private LinkedList<Pessoa> pessoas;
    private LinkedList<Eleicao> eleicoes;
    private LinkedList<Voto> votos;


    @Override
    public String sqlInsert() {
        return null;
    }
}
