import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GuessingGame {

    private static final int NUMBER_LENGTH = 4;
    private static String secretCode;
    private static int totalGuesses = 0;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/guessing_game_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password_here";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the 4-Digit Guessing Game!");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        generateSecretCode();
        System.out.println("I've generated a secret 4-digit number. Start guessing!");

        long startTime = System.currentTimeMillis();
        boolean guessedCorrectly = false;

        while (!guessedCorrectly) {
            System.out.print("Enter your guess (4 digits): ");
            String userGuess = scanner.nextLine();
            totalGuesses++;

            if (isValidGuess(userGuess)) {
                String feedback = getFeedback(userGuess);
                System.out.println("Feedback: " + feedback);

                if (feedback.equals("++++")) {
                    guessedCorrectly = true;
                }
            } else {
                System.out.println("Invalid guess. Please ensure your guess has 4 unique digits.");
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000;

        System.out.println("Nice job, " + playerName + "! You guessed the number in " + totalGuesses + " tries.");
        System.out.println("It took you " + timeTaken + " seconds.");

        saveGameResults(playerName, totalGuesses, (int) timeTaken);

        showTopPlayers();
    }

    private static void generateSecretCode() {
        ArrayList<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }
        Collections.shuffle(digits);

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            code.append(digits.get(i));
        }
        secretCode = code.toString();
    }

    private static boolean isValidGuess(String guess) {
        if (guess.length() != NUMBER_LENGTH) {
            return false;
        }
        for (char digit : guess.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return guess.chars().distinct().count() == NUMBER_LENGTH;
    }

    private static String getFeedback(String guess) {
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            char guessChar = guess.charAt(i);
            char secretChar = secretCode.charAt(i);

            if (guessChar == secretChar) {
                feedback.append("+");
            } else if (secretCode.contains(String.valueOf(guessChar))) {
                feedback.append("-");
            }
        }
        return feedback.toString();
    }

    private static void saveGameResults(String playerName, int attempts, int timeTaken) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO game_results (player_name, attempts, time_taken) VALUES (?, ?, ?)")) {

            statement.setString(1, playerName);
            statement.setInt(2, attempts);
            statement.setInt(3, timeTaken);
            statement.executeUpdate();
            System.out.println("Your game results have been saved!");
        } catch (SQLException e) {
            System.out.println("Error saving game results: " + e.getMessage());
        }
    }

    private static void showTopPlayers() {
        System.out.println("\nTop Players:");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT player_name, attempts, time_taken FROM game_results ORDER BY attempts, time_taken LIMIT 5")) {

            System.out.printf("%-20s %-10s %-10s%n", "Player Name", "Attempts", "Time (s)");
            System.out.println("---------------------------------------------");
            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                int attempts = resultSet.getInt("attempts");
                int timeTaken = resultSet.getInt("time_taken");

                System.out.printf("%-20s %-10d %-10d%n", playerName, attempts, timeTaken);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching top players: " + e.getMessage());
        }
    }
}
