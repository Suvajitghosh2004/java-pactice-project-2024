import java.util.*;

class Movie {
    private String title;
    private String genre;
    private double price;
    private int availableSeats;
    private List<String> reviews;
    private double rating;

    public Movie(String title, String genre, double price, int availableSeats) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.availableSeats = availableSeats;
        this.reviews = new ArrayList<>();
        this.rating = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookTicket(int numTickets) {
        if (numTickets <= availableSeats) {
            availableSeats -= numTickets;
            System.out.println("Booking successful! Total price: $" + (numTickets * price));
        } else {
            System.out.println("Sorry, not enough seats available.");
        }
    }

    public void cancelTicket(int numTickets) {
        availableSeats += numTickets;
        System.out.println("Booking cancelled! Number of tickets refunded: " + numTickets);
    }

    public void addReview(String review) {
        reviews.add(review);
        System.out.println("Review added successfully.");
    }

    public void viewReviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews for this movie yet.");
        } else {
            System.out.println("Reviews for " + title + ":");
            for (String review : reviews) {
                System.out.println("- " + review);
            }
        }
    }

    public void showDetails() {
        System.out.println("Title: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Ticket Price: $" + price);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Rating: " + rating + " / 5.0");
        viewReviews();
    }
}

class Booking {
    private String movieTitle;
    private int ticketsBooked;

    public Booking(String movieTitle, int ticketsBooked) {
        this.movieTitle = movieTitle;
        this.ticketsBooked = ticketsBooked;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getTicketsBooked() {
        return ticketsBooked;
    }

    @Override
    public String toString() {
        return "Movie: " + movieTitle + " | Tickets: " + ticketsBooked;
    }
}

class User {
    private String username;
    private String password;
    private List<Booking> bookings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

class MovieTicketBookingSystem {
    private List<Movie> movies;
    private List<User> users;
    private List<Booking> bookingHistory;
    private User loggedInUser;

    public MovieTicketBookingSystem() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
        bookingHistory = new ArrayList<>();
    }

    public void addMovie(String title, String genre, double price, int availableSeats) {
        movies.add(new Movie(title, genre, price, availableSeats));
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful!");
                return loggedInUser;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void displayMovies() {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getTitle());
        }
    }

    public Movie getMovie(int index) {
        if (index >= 1 && index <= movies.size()) {
            return movies.get(index - 1);
        }
        return null;
    }

    public void bookTicket(int movieIndex, int numTickets) {
        Movie movie = getMovie(movieIndex);
        if (movie != null) {
            movie.bookTicket(numTickets);
            loggedInUser.addBooking(new Booking(movie.getTitle(), numTickets));
            bookingHistory.add(new Booking(movie.getTitle(), numTickets));
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    public void cancelBooking(int movieIndex, int numTickets) {
        Movie movie = getMovie(movieIndex);
        if (movie != null) {
            movie.cancelTicket(numTickets);
            loggedInUser.getBookings().removeIf(booking -> booking.getMovieTitle().equals(movie.getTitle()));
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    public void viewBookingHistory() {
        if (loggedInUser == null || loggedInUser.getBookings().isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            System.out.println("\nBooking History:");
            for (Booking booking : loggedInUser.getBookings()) {
                System.out.println(booking);
            }
        }
    }

    public void addReview(int movieIndex, String review) {
        Movie movie = getMovie(movieIndex);
        if (movie != null) {
            movie.addReview(review);
        }
    }

    public void viewReviews(int movieIndex) {
        Movie movie = getMovie(movieIndex);
        if (movie != null) {
            movie.viewReviews();
        }
    }

    public void viewMovieDetails(int movieIndex) {
        Movie movie = getMovie(movieIndex);
        if (movie != null) {
            movie.showDetails();
        } else {
            System.out.println("Invalid movie selection.");
        }
    }

    public void searchMovieByGenre(String genre) {
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                movie.showDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No movies found with that genre.");
        }
    }
}

public class MovieTicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieTicketBookingSystem system = new MovieTicketBookingSystem();

        // Sample movies
        system.addMovie("Avengers: Endgame", "Action, Sci-Fi", 12.99, 50);
        system.addMovie("The Lion King", "Animation, Family", 9.99, 40);
        system.addMovie("Inception", "Sci-Fi, Thriller", 10.99, 30);

        while (true) {
            System.out.println("\nMovie Ticket Booking System");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. View Available Movies");
            System.out.println("4. Book a Ticket");
            System.out.println("5. Cancel Booking");
            System.out.println("6. View Booking History");
            System.out.println("7. Add Review");
            System.out.println("8. View Reviews");
            System.out.println("9. View Movie Details");
            System.out.println("10. Search Movies by Genre");
            System.out.println("11. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    system.registerUser(username, password);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    system.loginUser(loginUsername, loginPassword);
                    break;

                case 3:
                    system.displayMovies();
                    break;

                case 4:
                    system.displayMovies();
                    System.out.print("Enter the movie number to book a ticket: ");
                    int movieIndex = scanner.nextInt();
                    System.out.print("Enter the number of tickets: ");
                    int numTickets = scanner.nextInt();
                    system.bookTicket(movieIndex, numTickets);
                    break;

                case 5:
                    system.displayMovies();
                    System.out.print("Enter the movie number to cancel booking: ");
                    movieIndex = scanner.nextInt();
                    System.out.print("Enter the number of tickets to cancel: ");
                    numTickets = scanner.nextInt();
                    system.cancelBooking(movieIndex, numTickets);
                    break;

                case 6:
                    system.viewBookingHistory();
                    break;

                case 7:
                    system.displayMovies();
                    System.out.print("Enter the movie number to add a review: ");
                    movieIndex = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter your review: ");
                    String review = scanner.nextLine();
                    system.addReview(movieIndex, review);
                    break;

                case 8:
                    system.displayMovies();
                    System.out.print("Enter the movie number to view reviews: ");
                    movieIndex = scanner.nextInt();
                    system.viewReviews(movieIndex);
                    break;

                case 9:
                    system.displayMovies();
                    System.out.print("Enter the movie number to view details: ");
                    movieIndex = scanner.nextInt();
                    system.viewMovieDetails(movieIndex);
                    break;

                case 10:
                    System.out.print("Enter genre to search: ");
                    String genre = scanner.nextLine();
                    system.searchMovieByGenre(genre);
                    break;

                case 11:
                    System.out.println("Exiting... Thank you for using the Movie Ticket Booking System!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
