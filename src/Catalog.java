
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
class Catalog
{
    private Map<String, Elev> studenti;
    private Map<String, Profesor> profesori;

    public Catalog()
    {
        this.studenti = new HashMap<>();
        this.profesori = new HashMap<>();
    }

    public void adaugaStudent(String id, String nume) {
        studenti.put(id, new Elev(id, nume));
    }

    public void adaugaProfesor(String id, String nume, Set<String> discipline)
    {
        profesori.put(id, new Profesor(id, nume, discipline));
    }

    public void adaugaNota(String userId, String discipline, int valoare)
    {
        if (studenti.containsKey(userId))
        {
            studenti.get(userId).adaugaNota(discipline, valoare);
        } else if (profesori.containsKey(userId) && profesori.get(userId).getDiscipline().contains(discipline))
        {
            System.out.println("Profesorii pot adăuga note doar pentru elevi.");
        } else
        {
            System.out.println("Utilizatorul cu ID-ul " + userId + " nu există sau nu are permisiunea necesară.");
        }
    }

    // Metoda pentru calculul mediei la o materie pentru un student
    public double calculeazaMedia(String studentNume, String discipline)
    {
        for (Elev student : studenti.values())
        {
            if (student.getNume().equals(studentNume))
            {
                List<Nota> note = student.getNote().get(discipline);
                if (note != null && !note.isEmpty())
                {
                    // Calculul mediei
                    double sum = 0;
                    for (Nota grade : note)
                    {
                        sum += grade.getValoare();
                    }
                    return sum / note.size();
                } else
                {
                    System.out.println("Studentul " + studentNume + " nu are note la disciplina " + discipline);
                    return -1; // Valoare de eroare sau semnalizare
                }
            }
        }
        System.out.println("Studentul cu numele " + studentNume + " nu a fost găsit.");
        return -1; // Valoare de eroare sau semnalizare
    }

    public List<String> getDisciplineSortratePerStudent(String studentNume)
    {
        Elev student = studenti.values().stream()
                .filter(s -> s.getNume().equals(studentNume))
                .findFirst()
                .orElse(null);

        if (student != null)
        {
            return student.getNote().keySet().stream()
                    .sorted()
                    .collect(Collectors.toList());
        }
        else
        {
            System.out.println("Studentul cu numele " + studentNume + " nu a fost găsit.");
            return null;
        }
    }


    public List<Elev> getStudentiSortatiDupaNume()
    {
        return studenti.values().stream()
                .sorted(Comparator.comparing(Elev::getNume))
                .collect(Collectors.toList());
    }

    public double calculateOverallAverage(String studentNume)
    {
        Elev student = studenti.values().stream()
                .filter(s -> s.getNume().equals(studentNume))
                .findFirst()
                .orElse(null);

        if (student != null)
        {
            List<String> disciplines = student.getNote().keySet().stream()
                    .sorted()
                    .collect(Collectors.toList());

            if (!disciplines.isEmpty())
            {
                double sumaTotala = 0;
                int totalGradesCount = 0;

                for (String discipline : disciplines)
                {
                    List<Nota> grades = student.getNote().get(discipline);

                    if (grades != null && !grades.isEmpty())
                    {
                        double sum = grades.stream().mapToDouble(Nota::getValoare).sum();
                        sumaTotala += sum;
                        totalGradesCount += grades.size();
                    }
                }

                if (totalGradesCount > 0)
                {
                    return sumaTotala / totalGradesCount;
                }
                else
                {
                    System.out.println("Studentul " + studentNume + " nu are note la nicio disciplina.");
                    return -1; // Valoare de eroare sau semnalizare
                }
            }
            else
            {
                System.out.println("Studentul " + studentNume + " nu are note la nicio disciplina.");
                return -1; // Valoare de eroare sau semnalizare
            }
        }
        else
        {
            System.out.println("Studentul cu numele " + studentNume + " nu a fost găsit.");
            return -1; // Valoare de eroare sau semnalizare
        }
    }

    public Map<String, Elev> getStudents()
    {
        return studenti;
    }

    public Map<String, Profesor> getTeachers()
    {
        return profesori;
    }
}