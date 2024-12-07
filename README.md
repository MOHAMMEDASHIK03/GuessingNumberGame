
# Number Guessing Game

## Overview
The **Number Guessing Game** is a fun and interactive game where the player has to guess a 4-digit number generated randomly by the system. The game will provide feedback on the guesses, including how many digits are correct and whether they are in the correct position.

## Features
- The game generates a 4-digit random number with unique digits.
- The player makes guesses, and feedback is provided for each guess.
- The player is notified when they guess the number correctly.
- The number of attempts and time taken to guess the number are tracked.
- Results are stored in a MySQL database.
- Top players are displayed based on the number of attempts and time taken.

## Requirements
- **Java 8+** installed on your system.
- **MySQL Database** running locally or on a server.
- **MySQL JDBC Driver** to connect Java with MySQL.

## Setup Instructions

### 1. Set up MySQL Database

1. Create a database named `guessing_game_db` in MySQL.
   ```sql
   CREATE DATABASE guessing_game_db;
   ```

2. Create the `game_results` table to store the game results:
   ```sql
   CREATE TABLE game_results (
       id INT AUTO_INCREMENT PRIMARY KEY,
       player_name VARCHAR(100),
       attempts INT,
       time_taken INT
   );
   ```

### 2. Configure Database Connection

In the `GuessingGame.java` file, update the following database credentials with your own:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/guessing_game_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

Replace `"your_password_here"` with the actual password for your MySQL database.

### 3. Running the Game

1. Clone or download the repository to your local machine.
2. Ensure you have MySQL running and the required database and table set up as mentioned above.
3. Compile and run the `GuessingGame.java` file.

### 4. Gameplay

- The game will prompt you to enter your name.
- The system will generate a 4-digit number.
- You will enter guesses, and the game will provide feedback on the correctness of your guess.
- The game continues until the player guesses the correct number.
- Once you guess correctly, the game will show the number of attempts and the time taken.
- The results will be saved to the MySQL database.

### 5. Top Players

After the game finishes, the program will display the top 5 players based on the fewest attempts and time taken.

## Example Output

```plaintext
Welcome to the Number Guessing Game!
Please enter your name: Player1
The game has generated a 4-digit number. Start guessing!
Enter your 4-digit guess: 1234
Feedback: +--
Enter your 4-digit guess: 5678
Feedback: --
Enter your 4-digit guess: 1987
Feedback: +++
Well done, Player1! You guessed the number in 3 attempts.
Total time taken: 15 seconds.
Game results saved to the database!

Top Players:
Player Name         Attempts   Time (s)
---------------------------------------------
Player1             3          15
Player2             4          20
```

## Troubleshooting

- Ensure the MySQL database is running and accessible.
- Make sure the database credentials in `GuessingGame.java` match your MySQL configuration.
- If you're encountering issues with the JDBC driver, make sure you have the correct driver installed and added to your project's classpath.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
