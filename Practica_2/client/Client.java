package Practica_2.client;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {

        // args[0] = ip, args[1] = port, args[2] = username
        try (MySocket user_socket = new MySocket(args[0], Integer.parseInt(args[1]), args[2])) {

            new Thread(new Runnable() { // New thread to send the messages written by the client to the server
                @Override
                public void run() { // run() is the method that is executed when the thread is started

                    try {

                        user_socket.text_console_to_server(); // We read what is written in the console to send it

                    } catch (IOException exception) { // If there is an error, print the stack trace

                        exception.printStackTrace();
                    }
                }
            }).start(); // Start the thread

            // Main thread to read the messages from the server
            user_socket.text_server_to_client();

        } catch (UnknownHostException exception) { // If there is an error, print the stack trace

            exception.printStackTrace();

        } catch (IOException exception) { // If there is an error, print the stack trace

            exception.printStackTrace();
        }
    }
}
