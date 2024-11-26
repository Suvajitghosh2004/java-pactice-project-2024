import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieRecommendationSystem {
    private List<Movie> movieList;
    private Scanner scanner;

    public MovieRecommendationSystem() {
        movieList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Method to add a new movie
    public void addMovie() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        System.out.print("Enter movie genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter movie rating (0-10): ");
        double rating = scanner.nextDouble();
        scanner.nextLine();  // Consume newline left-over

        Movie movie = new Movie(title, genre, rating);
        movieList.add(movie);
        System.out.println("Movie added successfully!");
    }

    // Method to view all movies
    public void viewAllMovies() {
        if (movieList.isEmpty()) {
            System.out.println("No movies in the system.");
        } else {
            System.out.println("List of all movies:");
            for (Movie movie : movieList) {
                System.out.println(movie);
            }
        }
    }

    // Method to search movies by genre
    public void searchMoviesByGenre() {
        System.out.print("Enter genre to search for: ");
        String genre = scanner.nextLine();

        boolean found = false;
        System.out.println("Movies with genre " + genre + ":");
        for (Movie movie : movieList) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(movie);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No movies found for the genre: " + genre);
        }
    }

    // Method to recommend movies based on genre
    public void recommendMovies() {
        System.out.print("Enter your preferred genre: ");
        String genre = scanner.nextLine();

        boolean found = false;
        System.out.println("Recommended movies based on your genre (" + genre + "):");
        for (Movie movie : movieList) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(movie);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No recommendations available for the genre: " + genre);
        }
    }

    // Method to rate a movie
    public void rateMovie() {
        System.out.print("Enter the title of the movie to rate: ");
        String title = scanner.nextLine();

        Movie movie = findMovieByTitle(title);
        if (movie != null) {
            System.out.print("Enter new rating for " + title + " (0-10): ");
            double newRating = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            movie.setRating(newRating);
            System.out.println("Rating updated successfully!");
        } else {
            System.out.println("Movie not found.");
        }
    }

    // Utility method to find a movie by title
    private Movie findMovieByTitle(String title) {
        for (Movie movie : movieList) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    // Method to display the menu
    public void showMenu() {
        while (true) {
            System.out.println("\nMovie Recommendation System");
            System.out.println("1. Add a new movie");
            System.out.println("2. View all movies");
            System.out.println("3. Search movies by genre");
            System.out.println("4. Recommend movies by genre");
            System.out.println("5. Rate a movie");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    viewAllMovies();
                    break;
                case 3:
                    searchMoviesByGenre();
                    break;
                case 4:
                    recommendMovies();
                    break;
                case 5:
                    rateMovie();
                    break;
                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        MovieRecommendationSystem system = new MovieRecommendationSystem();
        system.showMenu();
    }
}
