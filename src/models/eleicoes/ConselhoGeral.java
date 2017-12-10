package models.eleicoes;

import java.sql.ResultSet;

public class ConselhoGeral extends Eleicao {

    public ConselhoGeral() {
    }

    public ConselhoGeral(ResultSet resultSet) {
        super(resultSet);
    }
}
