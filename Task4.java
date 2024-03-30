import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    String question;
    String[] options;
    int correctAnswerIndex;
    int timeLimit; // in seconds

    public QuizQuestion(String question, String[] options, int correctAnswerIndex, int timeLimit) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.timeLimit = timeLimit;
    }
}

public class Task4 {
    private QuizQuestion[] questions;
    private int score;
    private int currentQuestionIndex;
    private Timer questionTimer;
    private Scanner scanner;

    public Task4(QuizQuestion[] questions) {
        this.questions = questions;
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.scanner = new Scanner(System.in);
    }

    public void startQuiz() {
        displayQuestion();
    }

    private void displayQuestion() {
        try {
            if (currentQuestionIndex < questions.length) {
                QuizQuestion currentQuestion = questions[currentQuestionIndex];
                System.out.println(currentQuestion.question);
                for (int i = 0; i < currentQuestion.options.length; i++) {
                    System.out.println((i + 1) + ". " + currentQuestion.options[i]);
                }

                questionTimer = new Timer();
                questionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Time's up! Moving to the next question.");
                        currentQuestionIndex++;
                        if (currentQuestionIndex < questions.length) {
                            displayQuestion(); // Move to the next question
                        } else {
                            showResult(); // End of the quiz
                        }
                    }
                }, currentQuestion.timeLimit * 1000); // converting seconds to milliseconds

                // Read user input
                while (!scanner.hasNextInt()) { // Check if the next token is an integer
                    if (scanner.hasNext()) {
                        System.out.println("Invalid input! Please enter a number.");
                        scanner.next(); // Consume invalid input
                    }
                }
                int userChoice = scanner.nextInt();
                questionTimer.cancel(); // cancel the timer since the user has answered
                if (userChoice >= 1 && userChoice <= currentQuestion.options.length) {
                    if (userChoice == currentQuestion.correctAnswerIndex) {
                        System.out.println("Correct!");
                        score++;
                    } else {
                        System.out.println("Incorrect!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    displayQuestion(); // Move to the next question
                } else {
                    showResult(); // End of the quiz
                }
            } else {
                showResult(); // End of the quiz
            }
        } catch (Exception e) {
            System.out.println(" ");
        }
    }

    private void showResult() {
        System.out.println("Quiz finished!");
        System.out.println("Your score: " + score + "/" + questions.length);
        System.out.println("Thanks for playing!");
        System.out.println("Do you want to play again? (yes/no)");
        try {
            String playAgainChoice = scanner.next().toLowerCase();
            if (playAgainChoice.equals("yes")) {
                resetQuiz();
                startQuiz();
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void resetQuiz() {
        score = 0;
        currentQuestionIndex = 0;
    }

    public static void main(String[] args) {
        // Define your quiz questions
        QuizQuestion[] questions = {
                new QuizQuestion("What is the capital of France?", new String[]{"London", "Paris", "Rome", "Berlin"}, 2, 10),
                new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"Jupiter", "Mars", "Venus", "Mercury"}, 2, 10),
                new QuizQuestion("What is the largest mammal?", new String[]{"Elephant", "Blue Whale", "Giraffe", "Polar Bear"}, 2, 10),
                new QuizQuestion("What is the chemical symbol for water?", new String[]{"A. H2O", "B. CO2", "C. O2", "D. NaCl"}, 1, 10),
                new QuizQuestion("Who wrote the novel 'To Kill a Mockingbird'?", new String[]{"A. Harper Lee", "B. Ernest Hemingway", "C. F. Scott Fitzgerald", "D. J.D. Salinger"}, 1, 10),
                new QuizQuestion("What is the smallest prime number?", new String[]{"A. 0", "B. 1", "C. 2", "D. 3"}, 3, 10),
                new QuizQuestion("Which country is famous for its tulips?", new String[]{"A. Netherlands", "B. France", "C. Italy", "D. Japan"}, 1, 10),
                new QuizQuestion("Who painted the Mona Lisa?", new String[]{"A. Leonardo da Vinci", "B. Vincent van Gogh", "C. Pablo Picasso", "D. Michelangelo"}, 1, 10),
                new QuizQuestion("What is the tallest mountain in the world?", new String[]{"A. Mount Everest", "B. K2", "C. Kangchenjunga", "D. Lhotse"}, 1, 10),
                new QuizQuestion("Which element has the chemical symbol 'Fe'?", new String[]{"A. Iron", "B. Gold", "C. Silver", "D. Copper"}, 1, 10)
        };

        // Create and run the quiz
        Task4 quiz = new Task4(questions);
        quiz.startQuiz();
    }
}
