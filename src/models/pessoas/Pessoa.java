package models.pessoas;

import models.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Pessoa extends Model implements Serializable {

    protected String nome;
    protected String username;
    protected String password;
    protected int departamento_id;
    protected long telemovel;
    protected String morada;
    protected String codigo_postal;
    protected String localidade;
    protected long numero_cc;
    protected Date validade_cc;
    protected String genero;
    protected Date data_nascimento;

    public Pessoa() {
        super();
        table = "Pessoas";
    }

    public Pessoa(ResultSet resultSet) {
        super(resultSet);
        try {
            nome = resultSet.getString("nome");
            username = resultSet.getString("username");
            password = resultSet.getString("password");
            departamento_id = resultSet.getInt("departamento_id");
            telemovel = resultSet.getLong("telemovel");
            morada = resultSet.getString("morada");
            codigo_postal = resultSet.getString("codigo_postal");
            localidade = resultSet.getString("localidade");
            numero_cc = resultSet.getLong("numero_cc");
            validade_cc = resultSet.getDate("validade_cc");
            genero = resultSet.getString("genero");
            data_nascimento = resultSet.getDate("data_nascimento");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    public boolean setUsername(String username) {
        boolean flag = true;
        this.username = "";
        if (lenghtEntre(username, 8, 20))
            this.username = username;
        else
            flag = false;
        return flag;
    }
    
    public boolean setPassword(String password) {
        boolean flag = true;
        if (lenghtEntre(password, 8, 20))
            this.password = password;
        else
            flag = false;
        return flag;
    }

    public void setDepartamento_id(int departamento_id) {
        this.departamento_id = departamento_id;
    }

    public boolean setTelemovel(String telemovel) {
        boolean flag = true;
        if (lenghtIgual(telemovel, 9) &&
                isNumber(telemovel))
            this.telemovel = Integer.parseInt(telemovel);
        else
            flag = false;
        return flag;
    }
    
    public boolean setMorada(String morada) {
        boolean flag = true;
        if (lenghtMaior(morada, 0))
            this.morada = morada;
        else
            flag = false;
        return flag;
    }

    private boolean testeCodigo_postal(String codigo_postal) {
        if (!lenghtIgual(codigo_postal, 8) || codigo_postal.charAt(4) != '-')
            return false;
        for (int i : new int[]{0, 1, 2, 3, 5, 6, 7})
            if (Character.getNumericValue(codigo_postal.charAt(i)) == -1 ||
                    Character.getNumericValue(codigo_postal.charAt(i)) == -2 ||
                    Character.getNumericValue(codigo_postal.charAt(i)) > 9)
                return false;
        return true;
    }

    
    public boolean setCodigo_postal(String codigo_postal) {
        boolean flag = true;
        if (testeCodigo_postal(codigo_postal))
            this.codigo_postal = codigo_postal;
        else
            flag = false;
        return flag;
    }

    
    public boolean setLocalidade(String localidade) {
        boolean flag = true;
        if (lenghtMaior(localidade, 0) &&
                isAlpha(localidade))
            this.localidade = localidade;
        else {
            flag = false;
        }
        return flag;
    }

    
    public boolean setNumero_cc(String numeroCC) {
        boolean flag = true;
        if (lenghtIgual(numeroCC, 8) &&
                isNumber(numeroCC))
            this.numero_cc = Integer.parseInt(numeroCC);
        else
            flag = false;
        return flag;
    }

    
    public boolean setGenero(String genero) {
        boolean flag = true;
        if (genero.matches("Femenino") ||
                genero.matches("Masculino") ||
                genero.matches("Outro"))
            this.genero = genero;
        else
            flag = false;
        return flag;

    }

    public boolean isAluno(){
        return false;
    }


    public boolean isDocente(){
        return false;
    }


    public boolean isFuncionario() {
        return false;
    }

    @Override
    public String sqlInsert() {
        return sqlInsert("nome," +
                "username," +
                "password," +
                "departamento_id," +
                "telemovel," +
                "morada," +
                "codigo_postal," +
                "localidade," +
                "numero_cc" +
                "validade_cc" +
                "genero" +
                "data_nascimento",
                "'" + nome + "'," +
                        "'" + username + "'," +
                        "'" + password + "'," +
                        departamento_id + "," +
                        telemovel + "," +
                        "'" + morada + "'," +
                        "'" + codigo_postal + "'," +
                        "'" + localidade + "'," +
                        numero_cc + "," +
                        dateToSqlDate(validade_cc) + "," +
                        "'" + genero + "'," +
                        dateToSqlDate(data_nascimento));
    }
}
