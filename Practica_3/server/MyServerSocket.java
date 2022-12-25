package Practica_3.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyServerSocket extends ServerSocket {

    private Map<String, Socket> connections; // Map that stores the username and the socket of each user

    public MyServerSocket(int port) throws IOException {

        super(port); // Create a server socket, bound to the specified port
        connections = new HashMap<String, Socket>();
    }

    // Method to read the username of a client
    public String read_name(Socket socket) throws IOException {

        InputStream input = socket.getInputStream(); // Get the input stream from the socket
        // Return a BufferedReader to read the input from the socket
        return new BufferedReader(new InputStreamReader(input)).readLine(); // Return the username
    }

    // Method that returns a BufferedReader to read the messages from the clients
    public String read_client(String username) throws IOException {

        InputStream input = connections.get(username).getInputStream(); // Get the input stream from the socket
        // Return a BufferedReader to read the input from the socket
        return new BufferedReader(new InputStreamReader(input)).readLine(); // Return the message
    }

    // Method to read all the clients
    public String read_all() throws IOException {

        for (String key : connections.keySet()) { // For each key in the map
            String text = read_client(key); // Read the client
            if (text != null) { // If the text is not null
                return text; // Return the text
            }
        }
        return null; // Return null if there is no text
    }

    // Method to write to a specific client
    public void write_client(String username, String text) throws IOException {

        OutputStream output = connections.get(username).getOutputStream(); // Get the output stream from the socket
        // Create a PrintWriter to write to the socket
        new PrintWriter(output, true).println(text);
    }

    // Method to write to all the clients
    public void write_all(String text) throws IOException {

        for (String key : connections.keySet()) { // For each key in the map
            write_client(key, text); // Write to the client
        }
    }

    // Method to write to all the clients except the one with the given username
    public void write_allLessOne(String clientName, String text) throws IOException {

        for (String key : connections.keySet()) { // For each key in the map
            if (clientName != key) { // If the key is not the clientName
                write_client(key, text); // Write to the client
            }
        }
    }

    // Method to add a client to the map
    public boolean add_user(String username, Socket socket) {

        for (Map.Entry<String, Socket> entry : connections.entrySet()) { // For each entry in the map
            if (username.equals(entry.getKey())) { // If the username is already in the map
                return false;
            }
        }
        connections.put(username, socket); // Add the username and the socket to the map
        return true;
    }

    // Method to remove a client from the map
    public void delete_user(String username) throws IOException {

        try {

            write_all(username + " has left the chat."); // Write to all the clients that the user has left the chat
            connections.remove(username); // Remove the username from the map

        } catch (IOException exception) { // Catch the exception

            exception.printStackTrace(); // Print the stack trace
        }
    }

    // Method to read what is written in the console to send it
    public void text_console() throws IOException {

        // Create a reader to read from the console
        BufferedReader console_reader = new BufferedReader(new InputStreamReader(System.in));
        String text = console_reader.readLine(); // readLine() reads a line of text

        while (text != null) { // While there is something to read
            this.write_all("ADMIN: " + text); // send the message to all the clients
            text = console_reader.readLine(); // Read the next message
        }
    }

    // Method to reead the messages from the given client and send them to the other
    // clients
    public void text_client_to_server(String username) throws IOException {

        // read_client() reads the output from the client
        String text = this.read_client(username);

        while (text != null) { // While there is something to read

            // Print the message received from the client
            System.out.println(username + ": " + text);
            // send the message to all the clients less the one that sent it
            this.write_allLessOne(username, username + ": " + text);
            text = this.read_client(username); // Read the next message
        }
    }

    // Method to notify to a user the usernames of the other users connected
    public void notify_users_connected(String username) throws IOException {

        StringBuilder string = new StringBuilder(); // Create a StringBuilder to store the username
        for (Map.Entry<String, Socket> entry : this.connections.entrySet()) {
            string.append(entry.getKey() + " "); // Append the username to the string
        }
        write_client(username, string.toString());
    }
}