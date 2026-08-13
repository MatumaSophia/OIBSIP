import java.util.Random;
import java.util.Scanner;

/**
 * NumberGuessingGame
 * OIBSIP - Java Development Track - Task 2
 *
 * The computer picks a random number within a range based on difficulty.
 * The user guesses repeatedly, receiving "Too High" / "Too Low" hints,
 * until they guess correctly or run out of attempts. Score is tracked
 * across multiple rounds.
 */
public class NumberGuessingGame {

    private final Scanner scanner;
    private final Random random;
    private int roundNumber;

    public NumberGuessingGame() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.roundNumber = 0;
    }

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.run();
    }

    public void run() {
        System.out.println("=================================");
        System.out.println("   WELCOME TO NUMBER GUESSING!    ");
        System.out.println("=================================");

        boolean playAgain = true;
        while (playAgain) {
            roundNumber++;
            playRound();
            playAgain = askPlayAgain();
        }

        System.out.println("\nThanks for playing! Goodbye.");
        scanner.close();
    }

    private void playRound() {
        Difficulty difficulty = chooseDifficulty();
        int secretNumber = 1 + random.nextInt(difficulty.upperBound);
        int attemptsUsed = 0;
        boolean guessedCorrectly = false;

        System.out.printf(
                "%nRound %d — Guess a number between 1 and %d. You have %d attempts.%n",
                roundNumber, difficulty.upperBound, difficulty.maxAttempts
        );

        while (attemptsUsed < difficulty.maxAttempts && !guessedCorrectly) {
            int guess = readGuess(difficulty.upperBound);
            attemptsUsed++;

            if (guess == secretNumber) {
                guessedCorrectly = true;
                System.out.println("Correct! 🎉");
            } else if (guess < secretNumber) {
                System.out.println("Too Low!");
            } else {
                System.out.println("Too High!");
            }

            int remaining = difficulty.maxAttempts - attemptsUsed;
            if (!guessedCorrectly && remaining > 0) {
                System.out.println("Attempts remaining: " + remaining);
            }
        }

        if (guessedCorrectly) {
            System.out.printf(
                    "Round %d — guessed in %d attempt%s!%n",
                    roundNumber, attemptsUsed, attemptsUsed == 1 ? "" : "s"
            );
        } else {
            System.out.println("You Lost! The number was: " + secretNumber);
        }
    }

    private int readGuess(int upperBound) {
        while (true) {
            System.out.print("Your guess: ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < 1 || value > upperBound) {
                    System.out.println("Enter a number between 1 and " + upperBound + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Try again.");
            }
        }
    }

    private Difficulty chooseDifficulty() {
        System.out.println("\nChoose a difficulty:");
        System.out.println("1. Easy   (1-50,  10 attempts)");
        System.out.println("2. Medium (1-100, 7 attempts)");
        System.out.println("3. Hard   (1-200, 5 attempts)");

        while (true) {
            System.out.print("Enter 1, 2, or 3: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return Difficulty.EASY;
                case "2": return Difficulty.MEDIUM;
                case "3": return Difficulty.HARD;
                default: System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private boolean askPlayAgain() {
        while (true) {
            System.out.print("\nPlay again? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) return true;
            if (answer.equals("n") || answer.equals("no")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    private enum Difficulty {
        EASY(50, 10),
        MEDIUM(100, 7),
        HARD(200, 5);

        final int upperBound;
        final int maxAttempts;

        Difficulty(int upperBound, int maxAttempts) {
            this.upperBound = upperBound;
            this.maxAttempts = maxAttempts;
        }
    }
}