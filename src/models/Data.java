package models;

import java.io.Serializable;
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
        if (!((hora != -1 && minuto != -1 && segundo != -1) ||
                (hora == -1 && minuto == -1 && segundo == -1)) ||
                !((ano != -1 && mes != -1 && dia != -1) ||
                        (ano == -1 && mes == -1 && dia == -1)) ||
                ano < -1 || ano == 0 ||
                mes < -1 || mes == 0 || mes > 12 ||
                dia < -1 || dia == 0 ||
                hora < -1 || hora > 23 ||
                minuto < -1 || minuto > 59 ||
                segundo <-1)
            return false;

        if ((mes == 1 ||
                mes == 3 ||
                mes == 5 ||
                mes == 7 ||
                mes == 8 ||
                mes == 10 ||
                mes == 11)
                &&
                dia > 31)
            return false;
        else if (mes == 2 &&
                ((((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 29) ||
                        (!((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 28)))
            return false;
        else if (dia > 30)
            return false;
        return true;
    }

    public Date export() {
        Calendar calendar = Calendar.getInstance();
        if (ano != -1) calendar.set(Calendar.YEAR, ano);
        if (mes != -1) calendar.set(Calendar.MONTH, mes);
        if (dia != -1) calendar.set(Calendar.DAY_OF_MONTH, dia);
        if (hora != -1) calendar.set(Calendar.HOUR, hora);
        if (minuto != -1) calendar.set(Calendar.MINUTE, minuto);
        if (segundo != -1) calendar.set(Calendar.SECOND, segundo);
        return calendar.getTime();
    }
}
