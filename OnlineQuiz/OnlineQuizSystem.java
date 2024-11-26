import java.util.*;

class User {
    String username;
    String password;
    int score;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
    }
}

class Question {
    String question;
    List<String> options;
    int correctAnswerIndex;

    Question(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

class QuizSystem {
    private Map<String, User> users = new HashMap<>();
    private List<Question> questions = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Add questions for the quiz
    public void addQuestions() {
        questions.add(new Question("What is the capital of France?", 
                Arrays.asList("Berlin", "Madrid", "Paris", "Rome"), 2));
        questions.add(new Question("Which planet is known as the Red Planet?", 
                Arrays.asList("Earth", "Mars", "Jupiter", "Saturn"), 1));
        questions.add(new Question("Who wrote 'Harry Potter'?", 
                Arrays.asList("J.R.R. Tolkien", "J.K. Rowling", "George R.R. Martin", "C.S. Lewis"), 1));
    }

    // User Registration
    public void register() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
    }

    // User Login
    public User login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return null;
        }
    }

    // Start the quiz for the logged-in user
    public void startQuiz(User user) {
        System.out.println("Starting quiz...");
        int score = 0;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 60000; // 1 minute timer

        for (int i = 0; i < questions.size(); i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Time's up! Your final score is: " + score);
                break;
            }

            Question question = questions.get(i);
            System.out.println("\n" + question.question);
            for (int j = 0; j < question.options.size(); j++) {
                System.out.println((j + 1) + ". " + question.options.get(j));
            }

            System.out.print("Enter your choice (1-4): ");
            int userAnswer = scanner.nextInt();

            if (userAnswer - 1 == question.correctAnswerIndex) {
                score++;
            }
        }
        user.score = score;
        System.out.println("Quiz finished! Your score is: " + score);
    }

    // Display leaderboard
    public void displayLeaderboard() {
        System.out.println("\nLeaderboard:");
        users.values().stream()
                .sorted((u1, u2) -> Integer.compare(u2.score, u1.score)) // Sort by score in descending order
                .forEach(user -> System.out.println(user.username + ": " + user.score));
    }
}

public class OnlineQuizSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizSystem quizSystem = new QuizSystem();
        quizSystem.addQuestions();

        while (true) {
            System.out.println("\n--- Welcome to the Online Quiz System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Display Leaderboard");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    quizSystem.register();
                    break;
                case 2:
                    User user = quizSystem.login();
                    if (user != null) {
                        quizSystem.startQuiz(user);
                    }
                    break;
                case 3:
                    quizSystem.displayLeaderboard();
                    break;
                case 4:
                    System.out.println("Thank you for using the Online Quiz System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
