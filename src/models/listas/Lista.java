package models.listas;

import models.Model;
import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;
import models.Voto;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Johny on 09/12/2017.
 */
public class Lista extends Model implements Serializable {
    private String nome;
    private int eleicao_id;

    public Lista()  {
        super();
    }

    public boolean setNome(String nome) {
        boolean flag = true;
        if (lenghtMaior(nome, 0) &&
                isAlpha(nome))
            this.nome = nome;
        else
            flag = false;
        return flag;
    }

    @Override
    public String sqlInsert() {
        return null;
    }
}
