import java.util.*;

class Room {
    int roomNumber;
    String roomType;
    boolean isAvailable;
    double pricePerNight;

    Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = true;
        this.pricePerNight = pricePerNight;
    }
}

class Customer {
    String name;
    String contactNumber;
    String address;
    int bookedRoomNumber;
    int nightsStayed;
    double totalBill;

    Customer(String name, String contactNumber, String address) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.bookedRoomNumber = -1;
        this.nightsStayed = 0;
        this.totalBill = 0.0;
    }

    void bookRoom(Room room, int nights) {
        this.bookedRoomNumber = room.roomNumber;
        this.nightsStayed = nights;
        this.totalBill = nights * room.pricePerNight;
    }

    void checkOut() {
        this.bookedRoomNumber = -1;
        this.nightsStayed = 0;
        this.totalBill = 0.0;
    }
}

class HotelManagementSystem {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<Integer, Room> rooms = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // Register rooms
    public void addRooms() {
        rooms.put(101, new Room(101, "Single", 100.0));
        rooms.put(102, new Room(102, "Double", 150.0));
        rooms.put(103, new Room(103, "Suite", 250.0));
        rooms.put(104, new Room(104, "Single", 100.0));
        rooms.put(105, new Room(105, "Double", 150.0));
    }

    // Display room availability
    public void displayRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms.values()) {
            System.out.println("Room Number: " + room.roomNumber + ", Type: " + room.roomType + 
                               ", Price: $" + room.pricePerNight + " per night, Available: " + room.isAvailable);
        }
    }

    // Register customer
    public void registerCustomer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        customers.put(contactNumber, new Customer(name, contactNumber, address));
        System.out.println("Registration successful!");
    }

    // User login
    public Customer loginCustomer() {
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        Customer customer = customers.get(contactNumber);
        if (customer != null) {
            System.out.println("Login successful!");
            return customer;
        } else {
            System.out.println("Customer not found. Please register first.");
            return null;
        }
    }

    // Room booking functionality
    public void bookRoom(Customer customer) {
        displayRooms();
        System.out.print("\nEnter the room number you want to book: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine();  // consume newline character

        Room room = rooms.get(roomNumber);
        if (room != null && room.isAvailable) {
            room.isAvailable = false;
            customer.bookRoom(room, nights);
            System.out.println("Room booked successfully. Your total bill: $" + customer.totalBill);
        } else {
            System.out.println("Room is not available or invalid room number.");
        }
    }

    // Check out customer
    public void checkOut(Customer customer) {
        if (customer.bookedRoomNumber != -1) {
            Room room = rooms.get(customer.bookedRoomNumber);
            room.isAvailable = true;
            customer.checkOut();
            System.out.println("Checked out successfully. Thank you for staying with us.");
        } else {
            System.out.println("No room booked.");
        }
    }

    // View customer booking details
    public void viewBookingDetails(Customer customer) {
        if (customer.bookedRoomNumber != -1) {
            System.out.println("\nBooking Details:");
            System.out.println("Room Number: " + customer.bookedRoomNumber + ", Nights Stayed: " + customer.nightsStayed +
                               ", Total Bill: $" + customer.totalBill);
        } else {
            System.out.println("No room booked.");
        }
    }

    // Display billing information
    public void viewBill(Customer customer) {
        if (customer.bookedRoomNumber != -1) {
            System.out.println("\nBilling Information:");
            System.out.println("Room Number: " + customer.bookedRoomNumber + ", Nights Stayed: " + customer.nightsStayed +
                               ", Total Bill: $" + customer.totalBill);
        } else {
            System.out.println("No booking found.");
        }
    }
}

public class HotelManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelManagementSystem hotelSystem = new HotelManagementSystem();
        hotelSystem.addRooms();

        while (true) {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Rooms");
            System.out.println("4. Book a Room");
            System.out.println("5. View Booking Details");
            System.out.println("6. Check-Out");
            System.out.println("7. View Bill");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline character

            switch (choice) {
                case 1:
                    hotelSystem.registerCustomer();
                    break;
                case 2:
                    Customer customer = hotelSystem.loginCustomer();
                    if (customer != null) {
                        while (true) {
                            System.out.println("\n--- Customer Menu ---");
                            System.out.println("1. Book a Room");
                            System.out.println("2. View Booking Details");
                            System.out.println("3. Check-Out");
                            System.out.println("4. View Bill");
                            System.out.println("5. Logout");
                            System.out.print("Choose an option: ");
                            int customerChoice = scanner.nextInt();
                            scanner.nextLine();  // consume newline character

                            switch (customerChoice) {
                                case 1:
                                    hotelSystem.bookRoom(customer);
                                    break;
                                case 2:
                                    hotelSystem.viewBookingDetails(customer);
                                    break;
                                case 3:
                                    hotelSystem.checkOut(customer);
                                    break;
                                case 4:
                                    hotelSystem.viewBill(customer);
                                    break;
                                case 5:
                                    System.out.println("Logging out...");
                                    return;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    }
                    break;
                case 3:
                    hotelSystem.displayRooms();
                    break;
                case 8:
                    System.out.println("Thank you for using the Hotel Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
