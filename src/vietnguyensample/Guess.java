package vietnguyensample;

import java.util.Scanner;
import java.util.Random;

public class Guess {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    int secretNumber = random.nextInt(100) + 1;  // Number between 1 and 100
    int guess = 0;
    int attempts = 0;

    System.out.println("🎯 Welcome to Guess the Number!");
    System.out.println("I'm thinking of a number between 1 and 100.");

    while (guess != secretNumber) {
      System.out.print("Enter your guess: ");
      guess = scanner.nextInt();
      attempts++;

      if (guess < secretNumber) {
        System.out.println("Too low! 🔽 Try again.");
      } else if (guess > secretNumber) {
        System.out.println("Too high! 🔼 Try again.");
      } else {
        System.out.println("🎉 Correct! You guessed it in " + attempts + " tries.");
      }
    }

    scanner.close();
  }
}
