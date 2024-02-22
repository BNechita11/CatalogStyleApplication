import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
class Elev {
    private String id;
    private String nume;
    private Map<String, List<Nota>> note; // Mapă pentru a stoca notele la diverse discipline

    public Elev(String id, String nume)
    {
        this.id = id;
        this.nume = nume;
        this.note = new HashMap<>();
    }
    public String getId()
    {
        return id;
    }
    public String getNume()
    {
        return nume;
    }

    public Map<String, List<Nota>> getNote()
    {
        return note;
    }

    public void adaugaNota(String discipline, int valoare)
    {
        note.computeIfAbsent(discipline, k -> new ArrayList<>()).add(new Nota(valoare));
    }
}