package Practica_3.client;

import java.net.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public class Client {

    public static ConcurrentHashMap<String, String> content = new ConcurrentHashMap<>();
    private JTextArea messages;
    private DefaultListModel<String> users_list;

    public Client(JTextArea messages, DefaultListModel<String> users_list) {

        this.messages = messages;
        this.users_list = users_list;
    }

    public void ClientLauncher(String hostname, int port, String username) {

        try (MySocket user_socket = new MySocket(hostname, port, username)) {

            new Thread(new Runnable() { // New thread to send the messages written by the client to the server
                @Override
                public void run() { // run() is the method that is executed when the thread is started

                    try {

                        while (true) {

                            if (!content.isEmpty()) {
                                user_socket.write_socket(content.get("key"));
                                content.remove("key");
                            }
                        }
                    } catch (Exception exception) { // If there is an error, print the stack trace

                        exception.printStackTrace();
                    }
                }
            }).start();

            // Read the users connected
            String users = user_socket.read_socket();
            // Split the users connected
            String[] total_users = users.split(" ");
            int i = 0;

            while (total_users.length != i) {
                // Append the users connected to the users list
                users_list.addElement(total_users[i]);
                i++;
            }

            String clientText; // Variable to store the text received from the server
            while ((clientText = user_socket.read_socket()) != null) {
                // Append the text to the messages area
                messages.append(clientText + "\n");
            }

        } catch (UnknownHostException exception) { // If there is an error, print the stack trace

            exception.printStackTrace();

        } catch (IOException exception) { // If there is an error, print the stack trace

            exception.printStackTrace();
        }
    }
}