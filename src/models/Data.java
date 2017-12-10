package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Carlos on 20-10-2017.
 */
public class Data implements Serializable {

    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;

    public Data() {
        super();
        ano = -1;
        mes = -1;
        dia = -1;
        hora = -1;
        minuto = -1;
        segundo = -1;
    }

    public Data(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        hora = c.get(Calendar.HOUR);
        minuto = c.get(Calendar.MINUTE);
        segundo = c.get(Calendar.SECOND);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    public boolean test() {
        if ((ano == -1 && mes == -1 && dia == -1 && hora == -1 && minuto == -1 && segundo == -1) ||
                !((hora != -1 && minuto != -1 && segundo != -1) ||
                        (hora == -1 && minuto == -1 && segundo == -1)) ||
                !((ano != -1 && mes != -1 && dia != -1) ||
                        (ano == -1 && mes == -1 && dia == -1)) ||
                (ano < 1000 && ano != -1) || ano > 9999 ||
                mes < -1 || mes == 0 || mes > 12 ||
                dia < -1 || dia == 0 ||
                hora < -1 || hora > 23 ||
                minuto < -1 || minuto > 59 ||
                segundo < -1 || segundo > 59)
            return false;

        if ((mes == 4 ||
                mes == 6 ||
                mes == 9 ||
                mes == 11)
                &&
                dia > 30)
            return false;
        else if (mes == 2 &&
                ((((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 29) ||
                        (!((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 28)))
            return false;
        else if (dia > 31)
            return false;
        return true;
    }

    public Date export() {
        if (hora == -1 && minuto == -1 && segundo == -1)
            return Date.from(LocalDateTime.of(ano, mes, dia, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        else
            return Date.from(LocalDateTime.of(ano, mes, dia, hora, minuto, segundo).atZone(ZoneId.systemDefault()).toInstant());
    }
}
