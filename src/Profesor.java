import java.util.Set;

class Profesor
{
    private String id;
    private String nume;
    private Set<String> discipline; // Set pentru a stoca disciplinele predate de profesor

    public Profesor(String id, String nume, Set<String> discipline)
    {
        this.id = id;
        this.nume = nume;
        this.discipline = discipline;
    }

    public String getId()
    {
        return id;
    }

    public String getNume()
    {
        return nume;
    }

    public Set<String> getDiscipline()
    {
        return discipline;
    }
}