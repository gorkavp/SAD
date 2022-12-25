package Practica_3.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public class Server {

    public static ConcurrentHashMap<String, String> content = new ConcurrentHashMap<>();
    private JTextArea messages;
    private DefaultListModel<String> users_list;

    public Server(JTextArea messages, DefaultListModel<String> users_list) {

        this.messages = messages;
        this.users_list = users_list;
    }

    public void ServerLauncher(int port) {

        try (MyServerSocket server_socket = new MyServerSocket(port)) {

            messages.append("Server is listening on port " + port + "\n");

            new Thread(new Runnable() { // Thread to send the messages written by the server to the clients
                @Override
                public void run() { // run() is the method that is executed when the thread is started

                    try {

                        while (true) {

                            if (!content.isEmpty()) {

                                server_socket.write_all("ADMIN: " + content.get("key"));
                                content.remove("key");
                            }
                        }

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

                    this.users_list.addElement(username);
                    server_socket.notify_users_connected(username);
                    server_socket.write_allLessOne(username, username + " has joined the chat");

                    new Thread(new Runnable() { // Thread to read the messages from the clients
                        @Override
                        public void run() { // run() is the method that is executed when the thread is started

                            try {

                                String content; // Variable to store the text read from the client
                                // Check if the client has sent a message
                                while ((content = server_socket.read_client(username)) != null) {

                                    messages.append(username + ": " + content + "\n");
                                    server_socket.write_allLessOne(username, username + ": " + content);
                                }

                                server_socket.delete_user(username);
                                users_list.removeElement(username);

                            } catch (IOException exception) { // If there is an error, print the stack trace

                                exception.printStackTrace();
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