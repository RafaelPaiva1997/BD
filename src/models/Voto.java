package models;

import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;
import models.listas.*;

import java.io.Serializable;

public class Voto extends Model implements Serializable {

    @Override
    public String sqlInsert() {
        return null;
    }
}
