import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Server address
    private static final int SERVER_PORT = 12345; // Port number

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); // Connect to server
            System.out.println("Connected to the server.");

            // Input and output streams for communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Get username and password
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            output.println(username);  // Send username to server

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            output.println(password);  // Send password to server

            // Wait for server response (login result)
            String loginResponse = input.readLine();
            System.out.println(loginResponse);

            // If login is successful, start chatting
            if (loginResponse.equals("Login successful!")) {
                System.out.println("You can now start chatting...");

                // Continuously prompt for chat messages
                while (true) {
                    System.out.print("You: ");
                    String message = scanner.nextLine();  // Read message from the user
                    output.println(message);  // Send message to server
                    
                    if (message.equalsIgnoreCase("exit")) {
                        break; // Exit the chat loop if the user types 'exit'
                    }

                    // Receive and display the server's response
                    String serverResponse = input.readLine();
                    System.out.println("Server: " + serverResponse);
                }
            } else {
                System.out.println("Login failed. Please try again.");
            }

            // Close the socket and streams
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
