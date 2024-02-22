import java.text.SimpleDateFormat;
import java.util.*;

class Nota {
    private int valoare;
    private String data;

    public Nota(int valoare)
    {
        this.valoare = valoare;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.data = dateFormat.format(new Date());
    }

    public int getValoare()
    {
        return valoare;
    }

    public String getDate()
    {
        return data;
    }
}