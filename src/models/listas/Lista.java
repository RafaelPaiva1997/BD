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
    private Eleicao eleicao;
    private LinkedList<Pessoa> pessoas;
    private final LinkedList<Voto> votos;

    public Lista()  {
        super();
        pessoas = new LinkedList<>();
        votos = new LinkedList<>();
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

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public void setPessoas(LinkedList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    @Override
    public String sqlInsert() {
        return null;
    }
}
