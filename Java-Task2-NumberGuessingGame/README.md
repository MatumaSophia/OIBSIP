# Number Guessing Game — OIBSIP Java Development Task 2

## Objective
A console-based game where the computer generates a random number and the
user tries to guess it, receiving "Too High!" / "Too Low!" hints until they
guess correctly or run out of attempts.

## Tech Stack
- Java (console application, `java.util.Random`, `java.util.Scanner`)

## Features
- Random number generated within a range depending on difficulty
- User prompted for guesses via `Scanner`
- Feedback after every guess: "Too High!", "Too Low!", or "Correct!"
- Live attempt counter shown after each guess
- Maximum attempts enforced — reveals the number and ends the round if exceeded
- "Play Again?" prompt after every round
- Score tracking across rounds: prints `Round X — guessed in Y attempts`
- Bonus: three difficulty levels
    - Easy: 1–50, 10 attempts
    - Medium: 1–100, 7 attempts
    - Hard: 1–200, 5 attempts

## How to Run
Open `NumberGuessingGame.java` in an IDE (or compile via `javac`/`java` from
the command line) and run the `main` method.

## Author
Matuma Malapile — OIBSIP Java Development Track