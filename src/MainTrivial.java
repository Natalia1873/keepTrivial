//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Random;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class MainTrivial {
    public static void main(String[] args) {
        // Initialize questions
        List<Question> questions = getQuestions("src/questions");

        if (questions.isEmpty()) {
            title("Error: No se han cargado preguntas.");
            return;
        }

        // Initialize teams
        List<String> teams = new ArrayList<>();
        teams.add("Equipo 1");
        teams.add("Equipo 2");

        int[] scores = new int[teams.size()];

        // Game loop
        boolean exit = false;
        int currentTeam = 0; // Whose turn is it to answer the question (0 - first team, 1 - second)
        Scanner scanner = new Scanner(System.in);

        do {
            // Show current team's turn
            System.out.println("Turno de " + teams.get(currentTeam));

            // Show random question
            Question question = getRandomQuestion(questions);
            System.out.println(question);

            // Get user's answer
            System.out.print("Elige la respuesta correcta (1-4): ");
            String input = scanner.nextLine();
            int answer = -1; // Initialize answer variable

            if (esTransformableAEntero(input)) {
                answer = Integer.parseInt(input);
                // Check if the answer is within the valid range
                if (answer >= 1 && answer <= 4) {
                    System.out.println("Has elegido la opción: " + answer);
                } else {
                    System.out.println("Opción fuera de rango. Elige entre 1 y 4.");
                    answer = -1; // Invalid answer
                }
            } else {
                System.out.println("Entrada no válida. Introduce un número.");
            }

            // Check if the answer is correct
            if (answer != -1 && answer == question.getCorrectAnswer()) {
                System.out.println("¡Correcto!");
                scores[currentTeam]++;
            } else if (answer != -1) {
                System.out.println("Incorrecto. La respuesta correcta era: " + question.getCorrectAnswer());
            }

            // Check if the team won
            if (scores[currentTeam] >= 5) {
                exit = true;
                title("Ha ganado: " + teams.get(currentTeam));
            }

            // Switch team
            currentTeam = (currentTeam + 1) % teams.size();

            // Show current standings
            showScores(teams, scores);

        } while (!exit);

        System.out.println("¡Juego terminado!");
    }

    // Method to load questions from the files in the folder
    private static List<Question> getQuestions(String folderName) {
        List<Question> questions = new ArrayList<>();

        File folder = new File(folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            title("Error al cargar el fichero");
            return questions;
        }

        File[] filesList = folder.listFiles();

        if (filesList == null || filesList.length == 0) {
            title("No hay archivos de preguntas en la carpeta.");
            return questions;
        }

        for (File file : filesList) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                var topicName = file.getName().substring(0, file.getName().length() - 4);
                // TODO create topic
                // Read the question
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    List<String> block = new ArrayList<>();

                    while ((line = br.readLine()) != null) {
                        block.add(line);

                        if (block.size() == 6) { // 6 lines per question
                            var question = block.get(0);
                            var answer1 = block.get(1);
                            var answer2 = block.get(2);
                            var answer3 = block.get(3);
                            var answer4 = block.get(4);
                            var rightOption = Integer.parseInt(block.get(5));

                            // Create a new Question object and add it to the list
                            questions.add(new Question(question, answer1, answer2, answer3, answer4, rightOption));
                            block.clear();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return questions;
    }

    public static void title(String text) {
        int length = text.length();
        printHashtagLine(length + 4); // Borders

        System.out.println("# " + text + " #");

        printHashtagLine(length + 4);
    }

    public static void printHashtagLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("#");
        }
        System.out.println();
    }

    public static boolean esTransformableAEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Question getRandomQuestion(List<Question> questions) {
        int randomIndex = new Random().nextInt(questions.size());
        return questions.get(randomIndex);
    }

    public static void showScores(List<String> teams, int[] scores) {
        System.out.println("Clasificación actual:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println(teams.get(i) + ": " + scores[i] + " puntos");
        }
        System.out.println();
    }
}

// Question class
