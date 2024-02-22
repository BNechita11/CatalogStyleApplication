import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {

    private static void adaugaNotaLaInput(Catalog catalog, Scanner scanner)
    {
        System.out.print("Introduceti ID-ul studentului: ");
        String studentId = scanner.nextLine();
        System.out.print("Introduceti disciplina: ");
        String discipline = scanner.nextLine();

        if (catalog.getStudents().containsKey(studentId))
        {
            System.out.print("Introduceti valoarea notei: ");
            try
            {
                int gradeValue = scanner.nextInt();
                catalog.adaugaNota(studentId, discipline, gradeValue);
                System.out.println("Nota a fost adaugata cu succes.");

                // Adăugare nota în fișier
                try
                {
                    FileWriter writer = new FileWriter("C:\\Users\\Nechita Bianca\\Desktop\\AN 2\\sem1\\MIP\\numeInvalid\\src\\fisNote", true);
                    writer.write(studentId + "," + discipline + "," + gradeValue + "\n");
                    writer.close();
                }
                catch (IOException e)
                {
                    System.out.println("Eroare la scrierea notelor in fisier.");
                }

            }
            catch (InputMismatchException e)
            {
                System.out.println("Input invalid!");
                scanner.nextLine(); // Consumă inputul invalid
            }
        }
        else
        {
            System.out.println("Studentul cu ID-ul " + studentId + " nu a fost gasit.");
        }
    }

    public static void main(String[] args)
    {
        Catalog catalog = new Catalog();
        Scanner scanner = new Scanner(System.in);

        try
        {
            // Citeste studentii din fisier
            Scanner studentScanner = new Scanner(new File("C:\\Users\\Nechita Bianca\\Desktop\\AN 2\\sem1\\MIP\\numeInvalid\\src\\fisElevi"));
            while (studentScanner.hasNextLine())
            {
                String[] studentInfo = studentScanner.nextLine().split(",");
                catalog.adaugaStudent(studentInfo[0], studentInfo[1]);
            }
            studentScanner.close();

            // citeste Profesorii
            Scanner teacherScanner = new Scanner(new File("C:\\Users\\Nechita Bianca\\Desktop\\AN 2\\sem1\\MIP\\numeInvalid\\src\\fisProfesori"));
            while (teacherScanner.hasNextLine()) {
                String[] teacherInfo = teacherScanner.nextLine().split(",");
                String id = teacherInfo[0];
                String name = teacherInfo[1];
                Set<String> disciplines = new HashSet<>(Arrays.asList(Arrays.copyOfRange(teacherInfo, 2, teacherInfo.length)));
                catalog.adaugaProfesor(id, name, disciplines);
            }
            teacherScanner.close();

            // Citeste notele din fisier
            Scanner gradeScanner = new Scanner(new File("C:\\Users\\Nechita Bianca\\Desktop\\AN 2\\sem1\\MIP\\numeInvalid\\src\\fisNote"));
            while (gradeScanner.hasNextLine()) {
                String[] gradeInfo = gradeScanner.nextLine().split(",");
                String userId = gradeInfo[0];
                String discipline = gradeInfo[1];
                int value = Integer.parseInt(gradeInfo[2]);
                catalog.adaugaNota(userId, discipline, value);
            }
            gradeScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Eroare!");
            System.exit(1);
        }



        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Calculeaza Media La o Disciplina:");
            System.out.println("2. Sorteaza Studentii Alfabetic:");
            System.out.println("3. Sorteaza Disciplinele Studentilor");
            System.out.println("4. Adauga o noua nota");
            System.out.println("5. Calculeaza media generala a unui student");
            System.out.println("0. Exit");
            System.out.print("Introduceti-va alegerea manual: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Calculeaza Media
                        System.out.print("Introduce numele studentului: ");
                        String studentName = scanner.nextLine();
                        System.out.print("Introduce disciplina: ");
                        String discipline = scanner.nextLine();
                        double averageGrade = catalog.calculeazaMedia(studentName, discipline);
                        if (averageGrade != -1) {
                            System.out.println("Media notelor la disciplina " + discipline + " pentru studentul " + studentName + ": " + averageGrade);
                        }
                        break;

                    case 2:
                        // Afiseaza Studenti Sortati dupa Nume
                        List<Elev> sortedStudentsByName = catalog.getStudentiSortatiDupaNume();
                        if (sortedStudentsByName.isEmpty()) {
                            System.out.println("Nu au fost gasiti studentii.");
                        } else {
                            System.out.println("Studenti sortati dupa nume:");
                            for (Elev student : sortedStudentsByName) {
                                System.out.println("Student: " + student.getNume());
                                for (Map.Entry<String, List<Nota>> entry : student.getNote().entrySet()) {
                                    System.out.println("  Disciplina: " + entry.getKey());
                                    for (Nota grade : entry.getValue()) {
                                        System.out.println("    Nota: " + grade.getValoare() + " - Date: " + grade.getDate());
                                    }
                                }
                                System.out.println();
                            }
                        }
                        break;

                    case 3:
                        // Afisam disciplinele sortate pentru fiecare student
                        for (Elev student : catalog.getStudents().values()) {
                            System.out.println("Student: " + student.getNume());
                            List<String> sortedDisciplinesForStudent = catalog.getDisciplineSortratePerStudent(student.getNume());
                            if (sortedDisciplinesForStudent != null) {
                                for (String sortedDiscipline : sortedDisciplinesForStudent) {
                                    System.out.println("  Disciplina: " + sortedDiscipline);
                                    List<Nota> grades = student.getNote().get(sortedDiscipline);
                                    if (grades != null) {
                                        for (Nota grade : grades) {
                                            System.out.println("    Nota: " + grade.getValoare() + " - Data: " + grade.getDate());
                                        }
                                    } else {
                                        System.out.println("Nu sunt note disponibile la aceasta materie.");
                                    }
                                }
                            }
                            System.out.println();
                        }
                        break;
                    case 4:
                        adaugaNotaLaInput(catalog, scanner);
                        break;
                    case 5:
                        System.out.print("Introduceti numele studentului: ");
                        String studentNameForOverallAverage = scanner.nextLine();
                        double overallAverage = catalog.calculateOverallAverage(studentNameForOverallAverage);
                        if (overallAverage != -1) {
                            System.out.println("Media totala a studentului este " + studentNameForOverallAverage + ": " + overallAverage);
                        }
                        break;
                    case 6:
                        // Calculeaza si afiseaza media totala pentru fiecare student si ii sorteaza dupa ea
                        List<Elev> sortedStudentsByOverallAverage = catalog.getStudents().values().stream()
                                .sorted(Comparator.comparingDouble(s -> catalog.calculateOverallAverage(s.getNume())))
                                .toList();

                        if (sortedStudentsByOverallAverage.isEmpty()) {
                            System.out.println("Niciun student gasit.");
                        } else {
                            System.out.println("Studenti sortati dupa media finala:");
                            for (Elev student : sortedStudentsByOverallAverage) {
                                double studentOverallAverage = catalog.calculateOverallAverage(student.getNume());
                                System.out.println("Student: " + student.getNume() + " - Media Finala: " + studentOverallAverage);
                            }
                        }
                        break;
                    case 0:
                        System.out.println("Iesire din program. Pa-pa!");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Alegere invalida!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input invalid.");
                scanner.nextLine();
            }
        }
    }
}