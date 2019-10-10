package mp.tareas.tarea4;

import android.text.method.DateTimeKeyListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    Date fechaInicio = null;
    Date fechaFin = null;
    int id;
    float total;

    public Ticket (int id, Date horaInicio)
    {
        this.id = id;
        this.fechaInicio = horaInicio;

    }

    public Ticket()
    {
    }

    public int getId() {
        return id;
    }

    public Date getHoraFin() {
        return fechaFin;
    }

    public Date getHoraInicio() {
        return fechaInicio;
    }

    public float getTotal() {
        return total;
    }

    public void setFechaFin(Date fechaFin, long tiempo) {
        int totaltemp = 0;
        this.fechaFin = fechaFin;
        if (tiempo < 3600000) {
            totaltemp = 10;
        }
        else if (tiempo <7200000){
            totaltemp = 10;
            tiempo= tiempo - 3600000;
            totaltemp = totaltemp +((int)(tiempo/900000)) * 5 ;
        }
        else if (tiempo <= 10800000 )
        {
            totaltemp = 30;
            tiempo= tiempo - 7200000;
            totaltemp = totaltemp + ((int)(tiempo/900000)) * 10 ;
        }
        else
        {
            totaltemp = 100;
        }

        setTotal(totaltemp);

    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String[] getHeaders()
    {
        return new String[]{"Ticket","Hora Inicio","Hora Fin","Total"};
    }

    public String[] ToArray()
    {
        String horaInicio = "", horaFin = "", totalPrecio = "";
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            horaInicio = localDateFormat.format(fechaInicio);
        }catch (Exception ex){}


        try {
            horaFin = localDateFormat.format(fechaFin);

        }catch (Exception ex){}

        try {
            totalPrecio = String.valueOf(total);
        }catch (Exception ex){}


        return new String[]{ String.valueOf(id),horaInicio,horaFin,totalPrecio};
    }

}
