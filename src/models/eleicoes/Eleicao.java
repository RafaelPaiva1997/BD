package models.eleicoes;

import models.Data;
import models.listas.*;
import models.Model;
import models.Voto;
import models.MesadeVoto;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Johny on 09/12/2017.
 */
public class Eleicao extends Model implements Serializable {
    protected String titulo;
    protected String descricao;
    protected Data dataInicio;
    protected Data dataFim;
    protected LinkedList<Voto> votos;
    protected LinkedList<MesadeVoto> mesasDeVoto;
    protected LinkedList<Lista> listas;


    @Override
    public String sqlInsert() {
        return null;
    }
}
