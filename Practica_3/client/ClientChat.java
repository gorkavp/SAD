package Practica_3.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientChat implements ActionListener {

    private static JTextField content; // input content
    private static JTextArea messages; // output content
    private static DefaultListModel<String> users_list; // list of users

    public ClientChat() {

        // Create a DefaultListModel to store the users
        users_list = new DefaultListModel<>();

        // Create a JList to display the messages of the users connected
        JList<String> messages_list = new JList<>(users_list);

        // Create a JScrollPane to display the JList of the users connected
        JScrollPane scroll_area = new JScrollPane(messages_list);
        // Set the preferred size of the JScrollPane to 150 pixels width and 0 pixels
        // height
        scroll_area.setPreferredSize(new Dimension(150, 0));
        // Set the border of the JScrollPane to 5 pixels
        scroll_area.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        // Set the vertical scroll bar policy of the JScrollPane to ALWAYS
        scroll_area.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Set the horizontal scroll bar policy of the JScrollPane to NEVER
        scroll_area.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Set the unit increment of the vertical scroll bar of the JScrollPane to 16
        // pixels
        scroll_area.getVerticalScrollBar().setUnitIncrement(16);
        // Set the unit increment of the horizontal scroll bar of the JScrollPane to 16
        scroll_area.getHorizontalScrollBar().setUnitIncrement(16);

        // Create a JTextArea with 20 rows and 30 columns
        messages = new JTextArea(20, 50);
        // Set the JTextArea to not editable
        messages.setEditable(false);

        // Create a JButton to send the message
        JLabel label = new JLabel("Type your message: ");
        // Set the JButton to not focusable
        label.setFocusable(false);
        // Set the foreground color of the JButton to black
        label.setForeground(Color.BLACK);
        // Set the JButton to opaque
        label.setOpaque(true);
        // Set the font of the JButton to Arial, bold and size 16
        label.setFont(new Font("Arial", Font.BOLD, 13));

        // Create a JButton to send the message
        JButton button = new JButton("Send");
        // Add the ActionListener to the JButton to send the message
        button.addActionListener(this);
        // Set the JButton to not focusable
        button.setFocusable(false);
        // Set the background color of the JButton to green
        button.setBackground(Color.GREEN);
        // Set the foreground color of the JButton to black
        button.setForeground(Color.BLACK);
        // Set the JButton to opaque
        button.setOpaque(true);
        // Set the border of the JButton to not painted
        button.setBorderPainted(false);
        // Set the font of the JButton to Arial, bold and size 16
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Create a JTextField with 25 columns
        content = new JTextField(25);
        // Add the ActionListener to the JTextField to send the message
        content.addActionListener(this);

        // Create an output JPanel to display the messages
        JPanel output_area = new JPanel();
        // Set the border of the output JPanel to 5 pixels
        output_area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
        // Set the layout of the output JPanel to BoxLayout with PAGE_AXIS orientation
        output_area.setLayout(new BoxLayout(output_area, BoxLayout.PAGE_AXIS));
        // Add the JScrollPane to the output JPanel
        output_area.add(new JScrollPane(messages));

        // Create an input JPanel to write the messages
        JPanel input_area = new JPanel();
        // Set the layout of the input JPanel to BoxLayout with LINE_AXIS orientation
        input_area.setLayout(new BoxLayout(input_area, BoxLayout.LINE_AXIS));
        // Add the JLabel to the input JPanel
        input_area.add(label);
        // Add the JTextField to the input JPanel
        input_area.add(content);
        // Add the JButton to the input JPanel
        input_area.add(button);
        // Set the border of the input JPanel to 5 pixels
        input_area.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Create a JFrame that contains a JPanel with a JTextArea and a JTextField
        JFrame window = new JFrame("Client");
        // Add the output JPanel to the window
        window.add(output_area, BorderLayout.CENTER);
        // Add the input JPanel to the window
        window.add(input_area, BorderLayout.PAGE_END);
        // Add the JScrollPane to the window
        window.add(scroll_area, BorderLayout.WEST);
        // Set the size of the window
        window.pack();
        // Set the location of the window to the center of the screen
        window.setLocationRelativeTo(null);
        // Set the window to visible
        window.setVisible(true);
        // Set the window to close when the user clicks the close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the window to not resizable
        window.setResizable(false);
    }

    public void actionPerformed(ActionEvent event) {

        Client.content.put("key", content.getText());
        messages.append(content.getText() + "\n");
        content.setText(null);
    }

    private static void createAndShowGUI() throws Exception {

        // Set the look and feel of the GUI with the system look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Set the JFrame decoration with the system decoration
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Launch a new ClientChat
        new ClientChat();
    }

    public static void main(String[] args) {

        try {

            // This is done to avoid the GUI to freeze when the server is launched
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() { // run() method is called by the event-dispatching thread
                    try {
                        createAndShowGUI();
                    } catch (Exception exception) { // catch any exceptions
                        exception.printStackTrace();
                    }
                }
            });

            Thread.sleep(1000); // sleep for 1 second
            // Launch a new Client with the port number passed as argument
            new Client(messages, users_list).ClientLauncher(args[0], Integer.parseInt(args[1]), args[2]);

        } catch (Exception exception) { // catch any exceptions

            exception.printStackTrace();
        }
    }
}