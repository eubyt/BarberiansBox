package unix.caixa.sistema.util;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;


public class Tempo {

    public static SimpleDateFormat data = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss,SSS");

    public static long calcular (String inicio, String fim) throws ParseException {
        Date dtInicial = data.parse (inicio);
        Date dtFinal = data.parse (fim);
        return dtFinal.getTime() - dtInicial.getTime();
    }

    public static boolean calcular_vencimento(String data) {

        try {
            long tempo  = calcular(getData(), data);
            if (tempo <= 0)
                return true;
            if (tempo > 0)
                return false;


        } catch (Exception e)  {

        }

        return false;
    }

    public static String calcular(String data) {
        String retornar = null;
        try {
            long tempo  = calcular(getData(), data);
            retornar = Converter(tempo);


        } catch (Exception e)  {
            retornar = "ERRO";
        }

        return retornar;
    }

    public static String getData() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        Date agora = calendar.getTime();
        return String.valueOf(data.format(agora));
    }

    public static Calendar getCalendario() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        return calendar;
    }


    private static String Converter(long mm) {

        long seg = mm / 1000;
        int segundo = (int) (seg % 60);
        int minuto =  (int) ((seg % 3600) / 60);
        int hora = (int) ((seg / 3600) % 24);
        int dia = (int) seg / 86400 % 29;
        int mes = (int) seg / 2592000 % 12;
        int ano = (int) seg / 31104000;
        String resp = "";

        if (ano == 1)
            resp +=  ano + " ano ";

        if (ano >= 2)
            resp +=  ano + " anos ";

        if (mes == 1)
            resp +=  mes + " mês ";

        if (mes >= 2)
            resp +=  mes + " meses";

        if (dia > 0)
            resp +=  dia + "d ";

        if (hora > 0)
            resp +=  hora + "h ";

        if (minuto > 0)
            resp +=  minuto + "m ";

        if (segundo > 0)
            resp +=  segundo + "s";

        if (segundo == 0)
            resp +=  "0."+ String.valueOf(mm).charAt(0) + "" + String.valueOf(mm).charAt(1)+ "s";

        return resp;

    }



}
