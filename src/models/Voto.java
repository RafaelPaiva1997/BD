package models;

import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;
import models.listas.*;
import models.MesadeVoto.*;

import java.io.Serializable;

/**
 * Created by Johny on 09/12/2017.
 */
public class Voto extends Model implements Serializable{
    private Pessoa pessoa;
    private Eleicao eleicao;
    private Lista lista;
    private MesadeVoto mesaDeVoto;
    private Data data;

    @Override
    public String sqlInsert() {
        return null;
    }
}
