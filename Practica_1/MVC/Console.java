package Practica_1.MVC;

import java.util.*;

public class Console implements Observer { // View

    public void update(Observable o, Object arg) {

        int position = 0;
        int current_column = ((Line) o).get_current_column();
        List<Character> buffer = ((Line) o).get_buffer();

        switch (String.valueOf(arg)) {

            case Constants.STRING_LEFT:
                System.out.print(Constants.STRING_LEFT);
                break;

            case Constants.STRING_RIGHT:
                System.out.print(Constants.STRING_RIGHT);
                break;

            case Constants.STRING_DELETE:
                while (position + current_column < buffer.size()) { // Update terminal
                    System.out.print(buffer.get(position + current_column));
                    position++;
                }
                System.out.print(" ");
                position++;
                for (int i = 0; i < position; i++) { // Move the cursor to the original column
                    System.out.print(Constants.STRING_LEFT);
                }
                break;

            case Constants.STRING_BACKSPACE:
                System.out.print(Constants.STRING_LEFT);
                while (position + current_column < buffer.size()) { // Update terminal
                    System.out.print(buffer.get(position + current_column));
                    position++;
                }
                System.out.print(" ");
                position++;
                for (int i = 0; i < position; i++) { // Move the cursor to the original column
                    System.out.print(Constants.STRING_LEFT);
                }
                break;

            default: // add_character() method
                System.out.print(arg);
                while (position + current_column < buffer.size()) { // Update terminal
                    System.out.print(buffer.get(position + current_column));
                    position++;
                }
                System.out.print(" ");
                position++;
                for (int i = 0; i < position; i++) { // Move the cursor to the original column
                    System.out.print(Constants.STRING_LEFT);
                }
                break;
        }
    }
}
