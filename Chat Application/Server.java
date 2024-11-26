import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345; // Port number for the server

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);  // Create server socket
            System.out.println("Server started, waiting for clients...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Input and output streams for communication with the client
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Receive username and password from client
                String username = input.readLine();
                String password = input.readLine();
                System.out.println("Received Username: " + username + " | Password: " + password);

                // Check if username and password are correct
                if ("suva".equals(username) && "123".equals(password)) {
                    output.println("Login successful!");
                    System.out.println("Client authenticated.");

                    // Handle chat messages
                    String message;
                    while ((message = input.readLine()) != null) {
                        System.out.println("Client: " + message);
                        output.println("Server received: " + message);  // Send a response back to the client
                    }
                } else {
                    output.println("Login failed. Please try again.");
                    System.out.println("Invalid credentials.");
                }

                // Close client connection
                input.close();
                output.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
