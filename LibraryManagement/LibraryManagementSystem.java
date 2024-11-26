import java.util.ArrayList;
import java.util.Scanner;

// Class representing a Book
class Book {
    private int id;
    private String title;
    private boolean isBorrowed;
    private boolean isReserved;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
        this.isBorrowed = false;
        this.isReserved = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void borrowBook() {
        this.isBorrowed = true;
    }

    public void returnBook() {
        this.isBorrowed = false;
    }

    public void reserveBook() {
        this.isReserved = true;
    }

    public void cancelReservation() {
        this.isReserved = false;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: \"" + title + "\", Status: "
                + (isBorrowed ? "Borrowed" : (isReserved ? "Reserved" : "Available"));
    }
}

// Main Library Management System Class
public class LibraryManagementSystem {
    private ArrayList<Book> books;
    private int nextBookId;
    private Scanner scanner;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        nextBookId = 1; // Start book IDs from 1
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to the Library Management System ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new book");
            System.out.println("2. View all books");
            System.out.println("3. Borrow a book");
            System.out.println("4. Return a book");
            System.out.println("5. Search books by title");
            System.out.println("6. Remove a book");
            System.out.println("7. View borrowed books");
            System.out.println("8. Reserve a book");
            System.out.println("9. Cancel reservation");
            System.out.println("10. View total number of books");
            System.out.println("11. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> searchBooksByTitle();
                case 6 -> removeBook();
                case 7 -> viewBorrowedBooks();
                case 8 -> reserveBook();
                case 9 -> cancelReservation();
                case 10 -> viewTotalBooks();
                case 11 -> {
                    System.out.println("Exiting... Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBook() {
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        Book newBook = new Book(nextBookId++, title);  // Use nextBookId and increment
        books.add(newBook);
        System.out.println("Book added successfully with ID: " + newBook.getId());
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("Books available in the library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void borrowBook() {
        System.out.print("Enter the ID of the book to borrow: ");
        int id = scanner.nextInt();
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (book.isBorrowed()) {
            System.out.println("Sorry, the book is already borrowed.");
        } else if (book.isReserved()) {
            System.out.println("Sorry, the book is reserved and cannot be borrowed until it is returned.");
        } else {
            book.borrowBook();
            System.out.println("You successfully borrowed: " + book.getTitle());
        }
    }

    private void returnBook() {
        System.out.print("Enter the ID of the book to return: ");
        int id = scanner.nextInt();
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (!book.isBorrowed()) {
            System.out.println("The book was not borrowed.");
        } else {
            book.returnBook();
            System.out.println("You successfully returned: " + book.getTitle());

            // If the book was reserved, allow the reserved person to borrow it
            if (book.isReserved()) {
                System.out.println("The book is reserved. It can now be borrowed.");
            }
        }
    }

    private void searchBooksByTitle() {
        System.out.print("Enter the title of the book to search: ");
        String title = scanner.nextLine();
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + title);
        }
    }

    private void removeBook() {
        System.out.print("Enter the ID of the book to remove: ");
        int id = scanner.nextInt();
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else {
            books.remove(book);
            System.out.println("Book removed successfully: " + book.getTitle());
        }
    }

    private void viewBorrowedBooks() {
        boolean found = false;
        for (Book book : books) {
            if (book.isBorrowed()) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No borrowed books.");
        }
    }

    private void reserveBook() {
        System.out.print("Enter the ID of the book to reserve: ");
        int id = scanner.nextInt();
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (book.isBorrowed()) {
            System.out.println("Sorry, the book is currently borrowed and cannot be reserved.");
        } else if (book.isReserved()) {
            System.out.println("Sorry, the book is already reserved.");
        } else {
            book.reserveBook();
            System.out.println("You have successfully reserved: " + book.getTitle());
        }
    }

    private void cancelReservation() {
        System.out.print("Enter the ID of the book to cancel the reservation: ");
        int id = scanner.nextInt();
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (!book.isReserved()) {
            System.out.println("This book is not reserved.");
        } else {
            book.cancelReservation();
            System.out.println("Reservation for \"" + book.getTitle() + "\" has been cancelled.");
        }
    }

    private void viewTotalBooks() {
        System.out.println("Total number of books in the library: " + books.size());
    }

    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.start();
    }
}
