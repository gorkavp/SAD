package Practica_3.client;

import java.io.*;
import java.net.*;

public class MySocket extends Socket {

    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public MySocket(String ip, int port, String username) throws UnknownHostException, IOException {

        // Create a socket, connected to the specified remote host at the specified
        // remote port
        super(ip, port);
        this.username = username;
        // Inputstreamreader reads bytes and decodes them into characters
        // BufferedReader reads text from a character-input stream, buffering characters
        // so as to provide for the efficient reading of characters, arrays, and lines
        // We create a reader to read the input from the socket
        this.reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
        this.writer = new PrintWriter(this.getOutputStream(), true); // Create a writer to write to the socket
        this.send_username(); // Send the username to the server
    }

    // Method to get the username
    public String get_username() {

        return this.username; // Return the username
    }

    // Method to send the username to the server
    public void send_username() {

        this.writer.println(username); // Write the username to the socket
    }

    // Method to read the input from the server
    public String read_socket() throws IOException {

        return this.reader.readLine(); // return the text read from the socket
    }

    // Method to write to the server
    public void write_socket(String content) {

        this.writer.println(content); // Write the output to the socket
    }

    // Method to read what is written in the console to send it
    public void text_console_to_server() throws IOException {

        // Create a BufferedReader to read the input from the console
        BufferedReader text_reader = new BufferedReader(new InputStreamReader(System.in));
        String text = text_reader.readLine(); // readLine() reads a line of text
        while (text != null) { // While there is something to read
            this.writer.println(text); // send the message to the server
            text = text_reader.readLine(); // Read the next message
        }
    }

    // Method to read the messages from the server and print them
    public void text_server_to_client() throws IOException {

        String text = this.reader.readLine(); // readLine() reads a line of text
        while (text != null) { // While there is something to read
            System.out.println(text); // Print the message received from the server
            text = this.reader.readLine();// Read the next message
        }
    }
}