package Practica_2.server;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        // args[0] = port
        try (MyServerSocket server_socket = new MyServerSocket(Integer.parseInt(args[0]))) {

            System.out.println("Server listening on port " + server_socket.getLocalPort());

            new Thread(new Runnable() { // Thread to send the messages written by the server to the clients
                @Override
                public void run() { // run() is the method that is executed when the thread is started

                    try {

                        server_socket.text_console();

                    } catch (IOException exception) { // If there is an error, print the stack trace

                        exception.printStackTrace();
                    }
                }
            }).start(); // Start the thread

            while (true) { // Infinite loop to accept new clients connections

                // accept() returns a user_socket with the client information
                Socket user_socket = server_socket.accept(); // Wait for a new client connection
                // read_name() returns the username of the client
                String username = server_socket.read_name(user_socket);

                // add_user() returns true if the username is not in use
                if (server_socket.add_user(username, user_socket)) {

                    System.out.println("User " + username + " connected");
                    server_socket.write_all(username + " has joined the chat");

                    new Thread(new Runnable() { // Thread to read the messages from the clients

                        @Override
                        public void run() { // run() is the method that is executed when the thread is started

                            try {

                                server_socket.text_client_to_server(username);
                                server_socket.delete_user(username);

                            } catch (IOException exception) { // If there is an error, print the stack trace

                                System.out.println(username + " has left the chat");

                            }
                        }
                    }).start(); // Start the thread

                } else { // If the username is in use

                    OutputStream output = user_socket.getOutputStream(); // Get the output stream of the user_socket
                    // Notyfy the client that the username is in use
                    new PrintWriter(output, true).println("Username already in use");
                    user_socket.close(); // Close the user_socket
                }
            }

        } catch (IOException exception) { // If there is an error, print the stack trace

            exception.printStackTrace();

        }
    }
}