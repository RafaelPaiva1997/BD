package models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Model implements Serializable {

    protected int id;
    protected String table;
    protected String updateNew;
    protected String updateType;

    public Model() {
    }

    public Model(ResultSet resultSet) {
        try {
            id = resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTable() {
        return table;
    }

    public int getId() {
        return id;
    }

    public abstract String sqlInsert();

    public String sqlInsert(String dataTypes, String data) {
        return "INSERT INTO " + table + "(" + dataTypes + ") VALUES (" + data + ")";
    }

    public String sqlUpdate() {
        return "UPDATE " + table + " SET " + updateType + " = " + updateNew + " WHERE ID = " + id;
    }

    public String sqlDelete() {
        return "DELETE FROM " + table + " WHERE ID = " + id;
    }

    public String connect(Model model) {
        return "INSERT INTO " + table.substring(0, table.length() - 1) + "_" + model.table + " VALUES (" + id + "," + model.id + ")";
    }

    public String disconnect(Model model) {
        return "DELETE FROM " + table.substring(0, table.length() - 1) + "_" + model.table + " WHERE " + table.substring(0, table.length() - 1).toLowerCase() + "_id = " + id + " && " + model.table.substring(0, model.table.length() - 1).toLowerCase() + "_id = " + model.id;
    }

    public boolean lenghtIgual(String s, int i) {
        return s.length() == i;
    }

    public boolean lenghtMaior(String s, int i) {
        return s.length() > i;
    }

    public boolean lenghtEntre(String s, int a, int b) {
        return s.length() >= a && s.length() <= b;
    }

    public boolean isAlpha(String s) {
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(s);
        return !s.contains(";") && !s.contains("|") && m.matches();
    }

    public boolean isNumber(String s) {
        return s.matches("^[0-9]*$");
    }

    public String dateToSqlDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return "'" + f.format(date) + "'";
    }

    public String dateToSqlDateTime(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "'" + f.format(date) + "'";
    }

    public boolean checkAdd(String s, Model model) {
        return true;
    }
}
