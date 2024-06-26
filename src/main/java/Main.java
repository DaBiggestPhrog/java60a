/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class WrongStudentName extends Exception {
}

class WrongStudentAge extends Exception {
}

class WrongStudentDate extends Exception {
}

class InvalidMenuOption extends Exception {
    public InvalidMenuOption(String message) {
        super(message);
    }
}

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                try {
                    int ex = menu();
                    switch (ex) {
                        case 1:
                            exercise1();
                            break;
                        case 2:
                            exercise2();
                            break;
                        case 3:
                            exercise3();
                            break;
                        case 0:
                            return;
                        default:
                            throw new InvalidMenuOption("Nieprawidłowa opcja menu: " + ex);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WrongStudentName e) {
                    System.out.println("Błędne imię studenta!");
                } catch (WrongStudentAge e) {
                    System.out.println("Błędny wiek studenta!");
                } catch (WrongStudentDate e) {
                    System.out.println("Błędna data urodzenia!");
                }
            }
        } catch (InvalidMenuOption e) {
            System.out.println(e.getMessage());
            System.out.println("Program zostanie zakończony.");
        }
    }

    public static int menu() throws InvalidMenuOption {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        String input = scan.nextLine();
        if (!isInteger(input)) {
            throw new InvalidMenuOption("Nieprawidłowa opcja menu: " + input);
        }
        return Integer.parseInt(input);
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static int ReadAge() throws WrongStudentAge {
        System.out.println("Podaj wiek: ");
        var age = scan.nextInt();
        if (age < 0 || age > 100)
            throw new WrongStudentAge();
        return age;
    }

    public static String ReadDate() throws WrongStudentDate {
        scan.nextLine();
        System.out.println("Podaj datę urodzenia DD-MM-YYYY: ");
        String date = scan.nextLine();
        if (!isValidDate(date))
            throw new WrongStudentDate();
        return date;
    }

    public static boolean isValidDate(String date) {
        String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-(19[0-9]{2}|20[0-2][0-4])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongStudentAge, WrongStudentDate {
        var name = ReadName();
        var age = ReadAge();
        var date = ReadDate();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}
